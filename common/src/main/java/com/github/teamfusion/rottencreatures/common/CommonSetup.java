package com.github.teamfusion.rottencreatures.common;

import com.blackgear.platform.common.IntegrationHandler;
import com.blackgear.platform.common.entity.EntityHandler;
import com.blackgear.platform.core.ParallelDispatch;
import com.github.teamfusion.rottencreatures.common.entities.DeadBeard;
import com.github.teamfusion.rottencreatures.common.entities.FlyingScarab;
import com.github.teamfusion.rottencreatures.common.entities.GlacialHunter;
import com.github.teamfusion.rottencreatures.common.entities.Immortal;
import com.github.teamfusion.rottencreatures.common.entities.Mummy;
import com.github.teamfusion.rottencreatures.common.entities.Scarab;
import com.github.teamfusion.rottencreatures.common.entities.Burned;
import com.github.teamfusion.rottencreatures.common.entities.Frostbitten;
import com.github.teamfusion.rottencreatures.common.entities.SkeletonLackey;
import com.github.teamfusion.rottencreatures.common.entities.Swampy;
import com.github.teamfusion.rottencreatures.common.entities.UndeadMiner;
import com.github.teamfusion.rottencreatures.common.entities.Zap;
import com.github.teamfusion.rottencreatures.common.entities.ZombieLackey;
import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;
import com.github.teamfusion.rottencreatures.common.registries.RCItems;
import com.github.teamfusion.rottencreatures.common.registries.RCPotions;
import com.github.teamfusion.rottencreatures.common.worldgen.WorldGeneration;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.level.levelgen.Heightmap;

public class CommonSetup {
    public static void common() {
        EntityHandler.addAttributes(RCEntityTypes.BURNED, Burned::createAttributes);
        EntityHandler.addAttributes(RCEntityTypes.FROSTBITTEN, Frostbitten::createAttributes);
        EntityHandler.addAttributes(RCEntityTypes.SWAMPY, Swampy::createAttributes);
        EntityHandler.addAttributes(RCEntityTypes.UNDEAD_MINER, UndeadMiner::createAttributes);
        EntityHandler.addAttributes(RCEntityTypes.MUMMY, Mummy::createAttributes);
        EntityHandler.addAttributes(RCEntityTypes.GLACIAL_HUNTER, GlacialHunter::createAttributes);
        EntityHandler.addAttributes(RCEntityTypes.HUNTER_WOLF, Wolf::createAttributes);
        EntityHandler.addAttributes(RCEntityTypes.SCARAB, Scarab::createAttributes);
        EntityHandler.addAttributes(RCEntityTypes.FLYING_SCARAB, FlyingScarab::createAttributes);
        EntityHandler.addAttributes(RCEntityTypes.DEAD_BEARD, DeadBeard::createAttributes);
        EntityHandler.addAttributes(RCEntityTypes.ZOMBIE_LACKEY, ZombieLackey::createAttributes);
        EntityHandler.addAttributes(RCEntityTypes.SKELETON_LACKEY, SkeletonLackey::createAttributes);
        EntityHandler.addAttributes(RCEntityTypes.IMMORTAL, Immortal::createAttributes);
        EntityHandler.addAttributes(RCEntityTypes.ZAP, Zap::createAttributes);
        EntityHandler.addAttributes(RCEntityTypes.TREASURE_CHEST, Mob::createMobAttributes);
    }

    public static void postCommon(ParallelDispatch dispatch) {
        WorldGeneration.setup();

        EntityHandler.registerSpawnPlacement(RCEntityTypes.BURNED, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Burned::checkBurnedSpawnRules);
        EntityHandler.registerSpawnPlacement(RCEntityTypes.FROSTBITTEN, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Frostbitten::checkFrostbittenSpawnRules);
        EntityHandler.registerSpawnPlacement(RCEntityTypes.SWAMPY, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Swampy::checkSwampySpawnRules);
        EntityHandler.registerSpawnPlacement(RCEntityTypes.UNDEAD_MINER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, UndeadMiner::checkUndeadMinerSpawnRules);
        EntityHandler.registerSpawnPlacement(RCEntityTypes.GLACIAL_HUNTER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GlacialHunter::checkGlacialHunterSpawnRules);
        EntityHandler.registerSpawnPlacement(RCEntityTypes.MUMMY, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mummy::checkMummySpawnRules);
        EntityHandler.registerSpawnPlacement(RCEntityTypes.SCARAB, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        EntityHandler.registerSpawnPlacement(RCEntityTypes.FLYING_SCARAB, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        EntityHandler.registerSpawnPlacement(RCEntityTypes.DEAD_BEARD, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DeadBeard::checkDeadBeardSpawnRules);
        EntityHandler.registerSpawnPlacement(RCEntityTypes.ZOMBIE_LACKEY, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        EntityHandler.registerSpawnPlacement(RCEntityTypes.SKELETON_LACKEY, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        EntityHandler.registerSpawnPlacement(RCEntityTypes.IMMORTAL, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        EntityHandler.registerSpawnPlacement(RCEntityTypes.ZAP, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);

        IntegrationHandler.addFuel(RCItems.MAGMA_ROTTEN_FLESH.get(), 67);
        RCPotions.registerPotionMixes();
    }
}