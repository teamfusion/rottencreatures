package com.github.teamfusion.rottencreatures.common;

import com.blackgear.platform.common.IntegrationHandler;
import com.blackgear.platform.common.entity.EntityFactory;
import com.blackgear.platform.common.worldgen.modifier.BiomeManager;
import com.blackgear.platform.core.ParallelDispatch;
import com.github.teamfusion.rottencreatures.common.level.entities.living.deadbeard.DeadBeard;
import com.github.teamfusion.rottencreatures.common.level.entities.living.glacialhunter.GlacialHunter;
import com.github.teamfusion.rottencreatures.common.level.entities.living.immortal.Immortal;
import com.github.teamfusion.rottencreatures.common.level.entities.living.mummy.Mummy;
import com.github.teamfusion.rottencreatures.common.level.entities.living.burned.Burned;
import com.github.teamfusion.rottencreatures.common.level.entities.living.frostbitten.Frostbitten;
import com.github.teamfusion.rottencreatures.common.level.entities.living.lackey.SkeletonLackey;
import com.github.teamfusion.rottencreatures.common.level.entities.living.scarab.Scarab;
import com.github.teamfusion.rottencreatures.common.level.entities.living.swampy.Swampy;
import com.github.teamfusion.rottencreatures.common.level.entities.living.undeadminer.UndeadMiner;
import com.github.teamfusion.rottencreatures.common.level.entities.living.zap.Zap;
import com.github.teamfusion.rottencreatures.common.level.entities.living.lackey.ZombieLackey;
import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;
import com.github.teamfusion.rottencreatures.common.registries.RCItems;
import com.github.teamfusion.rottencreatures.common.registries.RCPotions;
import com.github.teamfusion.rottencreatures.common.worldgen.WorldGeneration;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.level.levelgen.Heightmap;

public class CommonSetup {
    public static void setup() {
        EntityFactory.registerMobAttributes(CommonSetup::registerMobAttributes);
    }

    public static void asyncSetup(ParallelDispatch dispatch) {
        BiomeManager.add(WorldGeneration::setupMobSpawns);
        EntityFactory.registerSpawnPlacements(CommonSetup::registerSpawnPlacement);
        IntegrationHandler.addFuel(RCItems.MAGMA_ROTTEN_FLESH.get(), 67);
        RCPotions.registerPotionMixes();
    }

    public static void registerMobAttributes(EntityFactory.EntityAttributesEvent event) {
        event.register(RCEntityTypes.BURNED, Burned::createAttributes);
        event.register(RCEntityTypes.FROSTBITTEN, Frostbitten::createAttributes);
        event.register(RCEntityTypes.SWAMPY, Swampy::createAttributes);
        event.register(RCEntityTypes.UNDEAD_MINER, UndeadMiner::createAttributes);
        event.register(RCEntityTypes.MUMMY, Mummy::createAttributes);
        event.register(RCEntityTypes.GLACIAL_HUNTER, GlacialHunter::createAttributes);
        event.register(RCEntityTypes.HUNTER_WOLF, Wolf::createAttributes);
        event.register(RCEntityTypes.SCARAB, Scarab::createAttributes);
        event.register(RCEntityTypes.DEAD_BEARD, DeadBeard::createAttributes);
        event.register(RCEntityTypes.ZOMBIE_LACKEY, ZombieLackey::createAttributes);
        event.register(RCEntityTypes.SKELETON_LACKEY, SkeletonLackey::createAttributes);
        event.register(RCEntityTypes.IMMORTAL, Immortal::createAttributes);
        event.register(RCEntityTypes.ZAP, Zap::createAttributes);
    }

    public static void registerSpawnPlacement(EntityFactory.EntityPlacementEvent event) {
        event.register(RCEntityTypes.BURNED, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Burned::checkBurnedSpawnRules);
        event.register(RCEntityTypes.FROSTBITTEN, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Frostbitten::checkFrostbittenSpawnRules);
        event.register(RCEntityTypes.SWAMPY, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Swampy::checkSwampySpawnRules);
        event.register(RCEntityTypes.UNDEAD_MINER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, UndeadMiner::checkUndeadMinerSpawnRules);
        event.register(RCEntityTypes.GLACIAL_HUNTER, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GlacialHunter::checkGlacialHunterSpawnRules);
        event.register(RCEntityTypes.MUMMY, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mummy::checkMummySpawnRules);
        event.register(RCEntityTypes.SCARAB, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        event.register(RCEntityTypes.DEAD_BEARD, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DeadBeard::checkDeadBeardSpawnRules);
        event.register(RCEntityTypes.ZOMBIE_LACKEY, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        event.register(RCEntityTypes.SKELETON_LACKEY, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        event.register(RCEntityTypes.IMMORTAL, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        event.register(RCEntityTypes.ZAP, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
    }
}