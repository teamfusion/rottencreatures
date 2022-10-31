package com.github.teamfusion.rottencreatures.common.worldgen;

import com.github.teamfusion.platform.Environment;
import com.github.teamfusion.platform.common.worldgen.BiomeContext;
import com.github.teamfusion.platform.common.worldgen.BiomeManager;
import com.github.teamfusion.rottencreatures.ConfigEntries;
import com.github.teamfusion.rottencreatures.common.entities.Burned;
import com.github.teamfusion.rottencreatures.common.entities.DeadBeard;
import com.github.teamfusion.rottencreatures.common.entities.Frostbitten;
import com.github.teamfusion.rottencreatures.common.entities.GlacialHunter;
import com.github.teamfusion.rottencreatures.common.entities.Mummy;
import com.github.teamfusion.rottencreatures.common.entities.Swampy;
import com.github.teamfusion.rottencreatures.common.entities.UndeadMiner;
import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;
import com.github.teamfusion.rottencreatures.data.RCBiomeTags;
import com.github.teamfusion.rottencreatures.mixin.access.SpawnPlacementsAccessor;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.Arrays;

public class WorldGeneration {
    public static void setup() {
        SpawnPlacementsAccessor.callRegister(RCEntityTypes.BURNED.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Burned::checkBurnedSpawnRules);
        SpawnPlacementsAccessor.callRegister(RCEntityTypes.FROSTBITTEN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Frostbitten::checkFrostbittenSpawnRules);
        SpawnPlacementsAccessor.callRegister(RCEntityTypes.GLACIAL_HUNTER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GlacialHunter::checkGlacialHunterSpawnRules);
        SpawnPlacementsAccessor.callRegister(RCEntityTypes.SWAMPY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Swampy::checkSwampySpawnRules);
        SpawnPlacementsAccessor.callRegister(RCEntityTypes.UNDEAD_MINER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, UndeadMiner::checkUndeadMinerSpawnRules);
        SpawnPlacementsAccessor.callRegister(RCEntityTypes.MUMMY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mummy::checkMummySpawnRules);
        SpawnPlacementsAccessor.callRegister(RCEntityTypes.SCARAB.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        SpawnPlacementsAccessor.callRegister(RCEntityTypes.FLYING_SCARAB.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        SpawnPlacementsAccessor.callRegister(RCEntityTypes.DEAD_BEARD.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DeadBeard::checkDeadBeardSpawnRules);
        SpawnPlacementsAccessor.callRegister(RCEntityTypes.ZOMBIE_LACKEY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        SpawnPlacementsAccessor.callRegister(RCEntityTypes.SKELETON_LACKEY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        SpawnPlacementsAccessor.callRegister(RCEntityTypes.IMMORTAL.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        SpawnPlacementsAccessor.callRegister(RCEntityTypes.ZAP.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        BiomeManager.add((writer, biome) -> {
            if (in(biome, RCBiomeTags.BURNED) || in(biome, Biomes.NETHER_WASTES)) {
                writer.addSpawn(MobCategory.MONSTER, RCEntityTypes.BURNED.get(), ConfigEntries.BURNED_WEIGHT.value(), 4, 4);
            }

            if (in(biome, RCBiomeTags.FROSTBITTEN) || in(biome, BiomeCategory.ICY) || in(biome, Biomes.ICE_SPIKES)) {
                writer.addSpawn(MobCategory.MONSTER, RCEntityTypes.FROSTBITTEN.get(), ConfigEntries.FROSTBITTEN_WEIGHT.value(), 4, 4);
            }

            if (in(biome, RCBiomeTags.GLACIAL_HUNTER) || in(biome, BiomeCategory.ICY) || in(biome, Biomes.ICE_SPIKES)) {
                writer.addSpawn(MobCategory.MONSTER, RCEntityTypes.GLACIAL_HUNTER.get(), ConfigEntries.GLACIAL_HUNTER_WEIGHT.value(), 1, 3);
            }

            if (in(biome, RCBiomeTags.SWAMPY) || in(biome, BiomeCategory.SWAMP)) {
                writer.addSpawn(MobCategory.MONSTER, RCEntityTypes.SWAMPY.get(), ConfigEntries.SWAMPY_WEIGHT.value(), 4, 4);
            }

            if (in(biome, RCBiomeTags.UNDEAD_MINER) || not(biome, BiomeCategory.NONE, BiomeCategory.THEEND, BiomeCategory.NETHER, BiomeCategory.MUSHROOM)) {
                writer.addSpawn(MobCategory.MONSTER, RCEntityTypes.UNDEAD_MINER.get(), ConfigEntries.UNDEAD_MINER_WEIGHT.value(), 1, 4);
            }

            if (in(biome, RCBiomeTags.MUMMY) || in(biome, BiomeCategory.DESERT)) {
                writer.addSpawn(MobCategory.MONSTER, RCEntityTypes.MUMMY.get(), ConfigEntries.MUMMY_WEIGHT.value(), 1, 3);
            }

            if (in(biome, RCBiomeTags.DEAD_BEARD) || in(biome, BiomeCategory.DESERT)) {
                writer.addSpawn(MobCategory.MONSTER, RCEntityTypes.MUMMY.get(), ConfigEntries.MUMMY_WEIGHT.value(), 1, 3);
            }

            if (in(biome, RCBiomeTags.DEAD_BEARD) || in(biome, BiomeCategory.BEACH)) {
                writer.addSpawn(MobCategory.MONSTER, RCEntityTypes.DEAD_BEARD.get(), ConfigEntries.DEAD_BEARD_WEIGHT.value(), 1, 1);
            }
        });
    }

    private static boolean in(BiomeContext biome, RCBiomeTags.Spawner spawner) {
        return biome.is(spawner.whitelist()) && !biome.is(spawner.blacklist());
    }

    private static boolean in(BiomeContext biome, BiomeCategory... categories) {
        return Environment.forge() && Arrays.stream(categories).anyMatch(biome::is);
    }

    private static boolean not(BiomeContext biome, BiomeCategory... categories) {
        return Environment.forge() && Arrays.stream(categories).noneMatch(biome::is);
    }

    private static boolean in(BiomeContext context, ResourceKey<Biome> biome) {
        return Environment.forge() && context.is(biome);
    }
}