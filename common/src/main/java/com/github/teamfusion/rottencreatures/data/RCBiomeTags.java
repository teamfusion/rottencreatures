package com.github.teamfusion.rottencreatures.data;

import com.blackgear.platform.common.data.TagRegistry;
import com.github.teamfusion.rottencreatures.RottenCreatures;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class RCBiomeTags {
    public static final TagRegistry<Biome> TAGS = TagRegistry.create(Registry.BIOME_REGISTRY, RottenCreatures.MOD_ID);

    public static final TagKey<Biome> CAN_BURNED_SPAWN_ON = TAGS.register("can_burned_spawn_on");
    public static final TagKey<Biome> CAN_FROSTBITTEN_SPAWN_ON = TAGS.register("can_frostbitten_spawn_on");
    public static final TagKey<Biome> CAN_GLACIAL_HUNTER_SPAWN_ON = TAGS.register("can_glacial_hunter_spawn_on");
    public static final TagKey<Biome> CAN_SWAMPY_SPAWN_ON = TAGS.register("can_swampy_spawn_on");
    public static final TagKey<Biome> CAN_UNDEAD_MINER_SPAWN_ON = TAGS.register("can_undead_miner_spawn_on");
    public static final TagKey<Biome> CAN_MUMMY_SPAWN_ON = TAGS.register("can_mummy_spawn_on");
    public static final TagKey<Biome> CAN_DEAD_BEARD_SPAWN_ON = TAGS.register("can_dead_beard_spawn_on");

    public static final Spawner BURNED = Spawner.of("burned");
    public static final Spawner FROSTBITTEN = Spawner.of("frostbitten");
    public static final Spawner GLACIAL_HUNTER = Spawner.of("glacial_hunter");
    public static final Spawner SWAMPY = Spawner.of("swampy");
    public static final Spawner UNDEAD_MINER = Spawner.of("undead_miner");
    public static final Spawner MUMMY = Spawner.of("mummy");
    public static final Spawner DEAD_BEARD = Spawner.of("dead_beard");

    public record Spawner(TagKey<Biome> whitelist, TagKey<Biome> blacklist) {
        public static Spawner of(String name) {
            return new Spawner(TAGS.register(name + "_whitelist"), TAGS.register(name + "_blacklist"));
        }
    }
}