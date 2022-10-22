package com.github.teamfusion.platform.common.worldgen.forge;

import com.github.teamfusion.platform.common.worldgen.BiomeContext;
import com.github.teamfusion.platform.common.worldgen.BiomeManager;
import com.github.teamfusion.platform.common.worldgen.BiomeWriter;
import com.github.teamfusion.rottencreatures.RottenCreatures;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = RottenCreatures.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BiomeManagerImpl {
    public static void setup() {}

    @SubscribeEvent
    public static void event(BiomeLoadingEvent event) {
        BiomeManager.INSTANCE.register(new ForgeBiomeWriter(event));
    }

    static class ForgeBiomeWriter extends BiomeWriter {
        private final BiomeLoadingEvent event;

        ForgeBiomeWriter(BiomeLoadingEvent event) {
            this.event = event;
        }

        @Override
        public ResourceLocation name() {
            return this.event.getName();
        }

        @Override
        public BiomeContext context() {
            return new BiomeContext() {
                private final ResourceKey<Biome> resourceKey = ResourceKey.create(Registry.BIOME_REGISTRY, ForgeBiomeWriter.this.name());

                @Override
                public boolean is(ResourceKey<Biome> biome) {
                    return this.resourceKey == biome;
                }

                @Override
                public boolean is(TagKey<Biome> tag) {
                    MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
                    if (server != null) {
                        Optional<? extends Registry<Biome>> registry = server.registryAccess().registry(Registry.BIOME_REGISTRY);
                        if (registry.isPresent()) {
                            Optional<Holder<Biome>> holder = registry.get().getHolder(this.resourceKey);
                            if (holder.isPresent()) {
                                return holder.get().is(tag);
                            }
                        }
                    }
                    return false;
                }

                @Override
                public boolean is(Biome.BiomeCategory category) {
                    return ForgeBiomeWriter.this.event.getCategory() == category;
                }
            };
        }

        @Override
        public void addFeature(GenerationStep.Decoration step, Holder<PlacedFeature> feature) {
            this.event.getGeneration().addFeature(step, feature);
        }

        @Override
        public void addSpawn(MobCategory category, EntityType<?> type, int weight, int minGroup, int maxGroup) {
            this.event.getSpawns().addSpawn(category, new MobSpawnSettings.SpawnerData(type, weight, minGroup, maxGroup));
        }
    }
}