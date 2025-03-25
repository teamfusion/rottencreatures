package com.github.teamfusion.rottencreatures.client;

import com.blackgear.platform.common.CreativeTabs;
import com.github.teamfusion.rottencreatures.common.registries.RCBlocks;
import com.github.teamfusion.rottencreatures.common.registries.RCItems;
import net.minecraft.world.item.Items;

public class VanillaCreativeTabIntegration {
    public static void modifyVanillaCreativeTabs() {
        CreativeTabs.modify(output -> {
            output.addAfter(Items.DRAGON_HEAD,
                RCBlocks.BURNED_HEAD.get(),
                RCBlocks.FROSTBITTEN_HEAD.get(),
                RCBlocks.SWAMPY_HEAD.get(),
                RCBlocks.UNDEAD_MINER_HEAD.get(),
                RCBlocks.MUMMY_HEAD.get(),
                RCBlocks.GLACIAL_HUNTER_HEAD.get(),
                RCBlocks.DEAD_BEARD_HEAD.get(),
                RCBlocks.IMMORTAL_HEAD.get(),
                RCBlocks.ZAP_HEAD.get()
            );

            output.addAfter(Items.ZOMBIFIED_PIGLIN_SPAWN_EGG,
                RCItems.BURNED_SPAWN_EGG.get(),
                RCItems.FROSTBITTEN_SPAWN_EGG.get(),
                RCItems.SWAMPY_SPAWN_EGG.get(),
                RCItems.UNDEAD_MINER_SPAWN_EGG.get(),
                RCItems.MUMMY_SPAWN_EGG.get(),
                RCItems.GLACIAL_HUNTER_SPAWN_EGG.get(),
                RCItems.DEAD_BEARD_SPAWN_EGG.get(),
                RCItems.IMMORTAL_SPAWN_EGG.get(),
                RCItems.ZAP_SPAWN_EGG.get(),
                RCItems.SCARAB_SPAWN_EGG.get(),
                RCItems.HUNTER_WOLF_SPAWN_EGG.get(),
                RCItems.SKELETON_LACKEY_SPAWN_EGG.get(),
                RCItems.ZOMBIE_LACKEY_SPAWN_EGG.get()
            );

            output.addAfter(Items.NETHER_WART, RCItems.CORRUPTED_WART.get());

            output.addAfter(Items.ROTTEN_FLESH,
                RCItems.MAGMA_ROTTEN_FLESH.get(),
                RCItems.FROZEN_ROTTEN_FLESH.get()
            );

            output.addAfter(Items.TNT, RCBlocks.TNT_BARREL.get());
            output.addAfter(Items.ENDER_CHEST, RCBlocks.TREASURE_CHEST.get());
        });
    }
}