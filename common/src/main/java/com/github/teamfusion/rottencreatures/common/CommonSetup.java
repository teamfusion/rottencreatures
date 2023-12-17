package com.github.teamfusion.rottencreatures.common;

import com.github.teamfusion.rottencreatures.core.platform.common.MobHandler;
import com.github.teamfusion.rottencreatures.core.platform.common.registry.ItemRegistry;
import com.github.teamfusion.rottencreatures.core.platform.common.worldgen.BiomeManager;
import com.github.teamfusion.rottencreatures.common.entities.Burned;
import com.github.teamfusion.rottencreatures.common.entities.DeadBeard;
import com.github.teamfusion.rottencreatures.common.entities.FlyingScarab;
import com.github.teamfusion.rottencreatures.common.entities.Frostbitten;
import com.github.teamfusion.rottencreatures.common.entities.GlacialHunter;
import com.github.teamfusion.rottencreatures.common.entities.Immortal;
import com.github.teamfusion.rottencreatures.common.entities.Mummy;
import com.github.teamfusion.rottencreatures.common.entities.Scarab;
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
import net.minecraft.world.entity.animal.Wolf;

public class CommonSetup {
    public static void common() {
        MobHandler.registerAttributes(RCEntityTypes.BURNED, Burned::createAttributes);
        MobHandler.registerAttributes(RCEntityTypes.FROSTBITTEN, Frostbitten::createAttributes);
        MobHandler.registerAttributes(RCEntityTypes.SWAMPY, Swampy::createAttributes);
        MobHandler.registerAttributes(RCEntityTypes.UNDEAD_MINER, UndeadMiner::createAttributes);
        MobHandler.registerAttributes(RCEntityTypes.MUMMY, Mummy::createAttributes);
        MobHandler.registerAttributes(RCEntityTypes.GLACIAL_HUNTER, GlacialHunter::createAttributes);
        MobHandler.registerAttributes(RCEntityTypes.HUNTER_WOLF, Wolf::createAttributes);
        MobHandler.registerAttributes(RCEntityTypes.SCARAB, Scarab::createAttributes);
        MobHandler.registerAttributes(RCEntityTypes.FLYING_SCARAB, FlyingScarab::createAttributes);
        MobHandler.registerAttributes(RCEntityTypes.DEAD_BEARD, DeadBeard::createAttributes);
        MobHandler.registerAttributes(RCEntityTypes.ZOMBIE_LACKEY, ZombieLackey::createAttributes);
        MobHandler.registerAttributes(RCEntityTypes.SKELETON_LACKEY, SkeletonLackey::createAttributes);
        MobHandler.registerAttributes(RCEntityTypes.IMMORTAL, Immortal::createAttributes);
        MobHandler.registerAttributes(RCEntityTypes.ZAP, Zap::createAttributes);
        MobHandler.registerAttributes(RCEntityTypes.TREASURE_CHEST, Mob::createMobAttributes);
    }

    public static void postCommon() {
        WorldGeneration.setup();
        BiomeManager.setup();
        ItemRegistry.registerFuel(RCItems.MAGMA_ROTTEN_FLESH.get(), 67);
        RCPotions.bootstrap();
    }
}