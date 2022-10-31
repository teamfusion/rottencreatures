package com.github.teamfusion.rottencreatures.data;

import com.github.teamfusion.rottencreatures.RottenCreatures;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class RCBiomeTags {
    public static void init() {}

    public static final Spawner BURNED = Spawner.of("burned");
    public static final Spawner FROSTBITTEN = Spawner.of("frostbitten");
    public static final Spawner GLACIAL_HUNTER = Spawner.of("glacial_hunter");
    public static final Spawner SWAMPY = Spawner.of("swampy");
    public static final Spawner UNDEAD_MINER = Spawner.of("undead_miner");
    public static final Spawner MUMMY = Spawner.of("mummy");
    public static final Spawner DEAD_BEARD = Spawner.of("dead_beard");

    private static TagKey<Biome> create(String key) {
        return TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(RottenCreatures.MOD_ID, key));
    }

    public record Spawner(TagKey<Biome> whitelist, TagKey<Biome> blacklist) {
        public static Spawner of(String name) {
            return new Spawner(create(name + "_whitelist"), create(name + "_blacklist"));
        }
    }
}