package com.github.teamfusion.rottencreatures.core.data;

import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import com.github.teamfusion.rottencreatures.core.platform.common.TagRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.biome.Biome;

public class RCBiomeTags {
    public static final TagRegistry<Biome> TAGS = TagRegistry.of(Registry.BIOME_REGISTRY, RottenCreatures.MOD_ID);

    public static final Spawner BURNED = Spawner.of("burned");
    public static final Spawner FROSTBITTEN = Spawner.of("frostbitten");
    public static final Spawner GLACIAL_HUNTER = Spawner.of("glacial_hunter");
    public static final Spawner SWAMPY = Spawner.of("swampy");
    public static final Spawner UNDEAD_MINER = Spawner.of("undead_miner");
    public static final Spawner MUMMY = Spawner.of("mummy");
    public static final Spawner DEAD_BEARD = Spawner.of("dead_beard");

    public record Spawner(TagKey<Biome> whitelist, TagKey<Biome> blacklist) {
        public static Spawner of(String name) {
            return new Spawner(TAGS.create(name + "_whitelist"), TAGS.create(name + "_blacklist"));
        }

        public static SpawnGroupData shouldSpawn(Holder<Biome> biome, Spawner spawner, MobSpawnType spawnType, SpawnGroupData data) {
            return (spawnType == MobSpawnType.NATURAL && biome.is(spawner.whitelist()) && !biome.is(spawner.blacklist())) ? data : null;
        }
    }
}