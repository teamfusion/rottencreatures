package com.github.teamfusion.rottencreatures.common.worldgen;

import com.github.teamfusion.platform.common.worldgen.BiomeModifier;
import com.github.teamfusion.rottencreatures.common.entities.Burned;
import com.github.teamfusion.rottencreatures.common.entities.Frostbitten;
import com.github.teamfusion.rottencreatures.common.entities.Swampy;
import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;
import com.github.teamfusion.rottencreatures.mixin.access.SpawnPlacementsAccessor;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap;

public class WorldGeneration {
    public static void setup() {
        SpawnPlacementsAccessor.callRegister(RCEntityTypes.BURNED.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Burned::checkBurnedSpawnRules);
        SpawnPlacementsAccessor.callRegister(RCEntityTypes.FROSTBITTEN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Frostbitten::checkFrostbittenSpawnRules);
        SpawnPlacementsAccessor.callRegister(RCEntityTypes.SWAMPY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Swampy::checkSwampySpawnRules);
        BiomeModifier.add(writer -> writer.addSpawn(MobCategory.MONSTER, RCEntityTypes.BURNED.get(), 19, 4, 4), Biomes.NETHER_WASTES);
        BiomeModifier.add(writer -> writer.addSpawn(MobCategory.MONSTER, RCEntityTypes.FROSTBITTEN.get(), 80, 4, 4), Biome.BiomeCategory.ICY);
        BiomeModifier.add(writer -> writer.addSpawn(MobCategory.MONSTER, RCEntityTypes.SWAMPY.get(), 80, 4, 4), Biome.BiomeCategory.SWAMP);
    }
}