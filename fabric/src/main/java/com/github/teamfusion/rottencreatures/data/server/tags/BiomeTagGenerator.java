package com.github.teamfusion.rottencreatures.data.server.tags;

import com.github.teamfusion.rottencreatures.core.data.tags.RCBiomeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

import java.util.concurrent.CompletableFuture;

public class BiomeTagGenerator extends FabricTagProvider<Biome> {
    public BiomeTagGenerator(FabricDataOutput generator, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(generator, Registries.BIOME, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.getOrCreateTagBuilder(RCBiomeTags.CAN_BURNED_SPAWN_ON).add(Biomes.NETHER_WASTES);
        this.getOrCreateTagBuilder(RCBiomeTags.CAN_FROSTBITTEN_SPAWN_ON).forceAddTag(BiomeTags.HAS_IGLOO).add(Biomes.ICE_SPIKES);
        this.getOrCreateTagBuilder(RCBiomeTags.CAN_GLACIAL_HUNTER_SPAWN_ON).forceAddTag(BiomeTags.HAS_IGLOO).add(Biomes.ICE_SPIKES);
        this.getOrCreateTagBuilder(RCBiomeTags.CAN_SWAMPY_SPAWN_ON).forceAddTag(BiomeTags.HAS_RUINED_PORTAL_SWAMP);
        this.getOrCreateTagBuilder(RCBiomeTags.CAN_UNDEAD_MINER_SPAWN_ON).forceAddTag(BiomeTags.IS_OVERWORLD);
        this.getOrCreateTagBuilder(RCBiomeTags.CAN_MUMMY_SPAWN_ON).forceAddTag(BiomeTags.HAS_DESERT_PYRAMID);
        this.getOrCreateTagBuilder(RCBiomeTags.CAN_DEAD_BEARD_SPAWN_ON).forceAddTag(BiomeTags.HAS_SHIPWRECK_BEACHED);
    }
}