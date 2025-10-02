package com.github.teamfusion.rottencreatures.common;

import com.blackgear.platform.common.integration.BlockIntegration;
import com.blackgear.platform.common.integration.MobIntegration;
import com.blackgear.platform.common.worldgen.modifier.BiomeManager;
import com.blackgear.platform.core.ParallelDispatch;
import com.github.teamfusion.rottencreatures.common.level.entities.burned.Burned;
import com.github.teamfusion.rottencreatures.common.level.entities.deadbeard.DeadBeard;
import com.github.teamfusion.rottencreatures.common.level.entities.frostbitten.Frostbitten;
import com.github.teamfusion.rottencreatures.common.level.entities.glacialhunter.GlacialHunter;
import com.github.teamfusion.rottencreatures.common.level.entities.immortal.Immortal;
import com.github.teamfusion.rottencreatures.common.level.entities.lackey.SkeletonLackey;
import com.github.teamfusion.rottencreatures.common.level.entities.lackey.ZombieLackey;
import com.github.teamfusion.rottencreatures.common.level.entities.mummy.Mummy;
import com.github.teamfusion.rottencreatures.common.level.entities.scarab.Scarab;
import com.github.teamfusion.rottencreatures.common.level.entities.swampy.Swampy;
import com.github.teamfusion.rottencreatures.common.level.entities.undeadminer.UndeadMiner;
import com.github.teamfusion.rottencreatures.common.level.entities.zap.Zap;
import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;
import com.github.teamfusion.rottencreatures.common.registries.RCItems;
import com.github.teamfusion.rottencreatures.common.worldgen.WorldGeneration;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.level.levelgen.Heightmap;

public class CommonSetup {
    public static void setup() {
        MobIntegration.registerIntegrations(CommonSetup::mobIntegrations);
    }

    public static void asyncSetup(ParallelDispatch dispatch) {
        dispatch.enqueueWork(() -> {
            BiomeManager.add(WorldGeneration::setupMobSpawns);
            BlockIntegration.registerIntegrations(event -> event.registerFuelItem(RCItems.MAGMA_ROTTEN_FLESH.get(), 67));
            CommonGameEvents.bootstrap();
        });
    }

    private static void mobIntegrations(MobIntegration.Event event) {
        event.registerAttributes(RCEntityTypes.BURNED, Burned::createAttributes);
        event.registerAttributes(RCEntityTypes.FROSTBITTEN, Frostbitten::createAttributes);
        event.registerAttributes(RCEntityTypes.SWAMPY, Swampy::createAttributes);
        event.registerAttributes(RCEntityTypes.UNDEAD_MINER, UndeadMiner::createAttributes);
        event.registerAttributes(RCEntityTypes.MUMMY, Mummy::createAttributes);
        event.registerAttributes(RCEntityTypes.GLACIAL_HUNTER, GlacialHunter::createAttributes);
        event.registerAttributes(RCEntityTypes.HUNTER_WOLF, Wolf::createAttributes);
        event.registerAttributes(RCEntityTypes.SCARAB, Scarab::createAttributes);
        event.registerAttributes(RCEntityTypes.DEAD_BEARD, DeadBeard::createAttributes);
        event.registerAttributes(RCEntityTypes.ZOMBIE_LACKEY, ZombieLackey::createAttributes);
        event.registerAttributes(RCEntityTypes.SKELETON_LACKEY, SkeletonLackey::createAttributes);
        event.registerAttributes(RCEntityTypes.IMMORTAL, Immortal::createAttributes);
        event.registerAttributes(RCEntityTypes.ZAP, Zap::createAttributes);

        event.registerPlacement(RCEntityTypes.BURNED, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Burned::checkBurnedSpawnRules);
        event.registerPlacement(RCEntityTypes.FROSTBITTEN, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Frostbitten::checkFrostbittenSpawnRules);
        event.registerPlacement(RCEntityTypes.SWAMPY, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Swampy::checkSwampySpawnRules);
        event.registerPlacement(RCEntityTypes.UNDEAD_MINER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, UndeadMiner::checkUndeadMinerSpawnRules);
        event.registerPlacement(RCEntityTypes.GLACIAL_HUNTER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GlacialHunter::checkGlacialHunterSpawnRules);
        event.registerPlacement(RCEntityTypes.MUMMY, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mummy::checkMummySpawnRules);
        event.registerPlacement(RCEntityTypes.SCARAB, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        event.registerPlacement(RCEntityTypes.DEAD_BEARD, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DeadBeard::checkDeadBeardSpawnRules);
        event.registerPlacement(RCEntityTypes.ZOMBIE_LACKEY, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        event.registerPlacement(RCEntityTypes.SKELETON_LACKEY, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        event.registerPlacement(RCEntityTypes.IMMORTAL, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        event.registerPlacement(RCEntityTypes.ZAP, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
    }
}