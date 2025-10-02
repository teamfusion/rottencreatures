package com.github.teamfusion.rottencreatures.core.data.tags;

import com.blackgear.platform.common.data.TagRegistry;
import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class RCBiomeTags {
    public static final TagRegistry<Biome> TAGS = TagRegistry.create(Registries.BIOME, RottenCreatures.MOD_ID);

    public static final TagKey<Biome> CAN_BURNED_SPAWN_ON = TAGS.register("can_burned_spawn_on");
    public static final TagKey<Biome> CAN_FROSTBITTEN_SPAWN_ON = TAGS.register("can_frostbitten_spawn_on");
    public static final TagKey<Biome> CAN_GLACIAL_HUNTER_SPAWN_ON = TAGS.register("can_glacial_hunter_spawn_on");
    public static final TagKey<Biome> CAN_SWAMPY_SPAWN_ON = TAGS.register("can_swampy_spawn_on");
    public static final TagKey<Biome> CAN_UNDEAD_MINER_SPAWN_ON = TAGS.register("can_undead_miner_spawn_on");
    public static final TagKey<Biome> CAN_MUMMY_SPAWN_ON = TAGS.register("can_mummy_spawn_on");
    public static final TagKey<Biome> CAN_DEAD_BEARD_SPAWN_ON = TAGS.register("can_dead_beard_spawn_on");
}