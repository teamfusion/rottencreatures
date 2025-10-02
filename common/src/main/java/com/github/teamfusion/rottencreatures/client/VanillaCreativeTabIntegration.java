package com.github.teamfusion.rottencreatures.client;

import com.blackgear.platform.common.CreativeTabs;
import com.github.teamfusion.rottencreatures.common.registries.RCBlocks;
import com.github.teamfusion.rottencreatures.common.registries.RCItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

public class VanillaCreativeTabIntegration {
    public static void modifyVanillaCreativeTabs() {
        CreativeTabs.modify(CreativeModeTabs.REDSTONE_BLOCKS, (featureFlag, output, operator) -> output.addAfter(new ItemStack(Blocks.TNT), new ItemStack(RCBlocks.TNT_BARREL.get())));
        CreativeTabs.modify(CreativeModeTabs.COMBAT, (featureFlag, output, operator) -> output.addAfter(new ItemStack(Blocks.TNT), new ItemStack(RCBlocks.TNT_BARREL.get())));

        CreativeTabs.modify(CreativeModeTabs.FUNCTIONAL_BLOCKS, (featureFlag, output, operator) -> {
            output.addAllAfter(new ItemStack(Blocks.DRAGON_HEAD),
                List.of(
                    new ItemStack(RCBlocks.ZAP_HEAD.get()),
                    new ItemStack(RCBlocks.IMMORTAL_HEAD.get()),
                    new ItemStack(RCBlocks.DEAD_BEARD_HEAD.get()),
                    new ItemStack(RCBlocks.GLACIAL_HUNTER_HEAD.get()),
                    new ItemStack(RCBlocks.MUMMY_HEAD.get()),
                    new ItemStack(RCBlocks.UNDEAD_MINER_HEAD.get()),
                    new ItemStack(RCBlocks.SWAMPY_HEAD.get()),
                    new ItemStack(RCBlocks.FROSTBITTEN_HEAD.get()),
                    new ItemStack(RCBlocks.BURNED_HEAD.get())
                )
            );

            output.addAfter(new ItemStack(Items.ENDER_CHEST), new ItemStack(RCBlocks.TREASURE_CHEST.get()));
        });

        CreativeTabs.modify(CreativeModeTabs.INGREDIENTS, (featureFlag, output, operator) -> {
            output.addAfter(new ItemStack(Items.NETHER_WART), new ItemStack(RCItems.CORRUPTED_WART.get()));
        });

        CreativeTabs.modify(CreativeModeTabs.FOOD_AND_DRINKS, (featureFlag, output, operator) -> {
            output.addAllAfter(new ItemStack(Items.ROTTEN_FLESH),
                List.of(
                    new ItemStack(RCItems.MAGMA_ROTTEN_FLESH.get()),
                    new ItemStack(RCItems.FROZEN_ROTTEN_FLESH.get())
                )
            );
        });

        CreativeTabs.modify(CreativeModeTabs.SPAWN_EGGS, (featureFlag, output, operator) -> {
            output.addAllAfter(Items.ZOMBIFIED_PIGLIN_SPAWN_EGG,
                List.of(
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
                )
            );
        });
    }
}