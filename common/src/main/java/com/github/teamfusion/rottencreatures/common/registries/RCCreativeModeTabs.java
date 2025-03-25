package com.github.teamfusion.rottencreatures.common.registries;

import com.blackgear.platform.common.CreativeTabs;
import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class RCCreativeModeTabs {
    public static final CreativeModeTab TAB = CreativeTabs.create(
        RottenCreatures.resource(RottenCreatures.MOD_ID),
        Items.ROTTEN_FLESH::getDefaultInstance,
        stacks -> {
            stacks.add(new ItemStack(RCBlocks.BURNED_HEAD.get()));
            stacks.add(new ItemStack(RCBlocks.FROSTBITTEN_HEAD.get()));
            stacks.add(new ItemStack(RCBlocks.SWAMPY_HEAD.get()));
            stacks.add(new ItemStack(RCBlocks.UNDEAD_MINER_HEAD.get()));
            stacks.add(new ItemStack(RCBlocks.MUMMY_HEAD.get()));
            stacks.add(new ItemStack(RCBlocks.GLACIAL_HUNTER_HEAD.get()));
            stacks.add(new ItemStack(RCBlocks.DEAD_BEARD_HEAD.get()));
            stacks.add(new ItemStack(RCBlocks.IMMORTAL_HEAD.get()));
            stacks.add(new ItemStack(RCBlocks.ZAP_HEAD.get()));
            stacks.add(new ItemStack(RCItems.BURNED_SPAWN_EGG.get()));
            stacks.add(new ItemStack(RCItems.FROSTBITTEN_SPAWN_EGG.get()));
            stacks.add(new ItemStack(RCItems.SWAMPY_SPAWN_EGG.get()));
            stacks.add(new ItemStack(RCItems.UNDEAD_MINER_SPAWN_EGG.get()));
            stacks.add(new ItemStack(RCItems.MUMMY_SPAWN_EGG.get()));
            stacks.add(new ItemStack(RCItems.GLACIAL_HUNTER_SPAWN_EGG.get()));
            stacks.add(new ItemStack(RCItems.DEAD_BEARD_SPAWN_EGG.get()));
            stacks.add(new ItemStack(RCItems.IMMORTAL_SPAWN_EGG.get()));
            stacks.add(new ItemStack(RCItems.ZAP_SPAWN_EGG.get()));
            stacks.add(new ItemStack(RCItems.SCARAB_SPAWN_EGG.get()));
            stacks.add(new ItemStack(RCItems.HUNTER_WOLF_SPAWN_EGG.get()));
            stacks.add(new ItemStack(RCItems.SKELETON_LACKEY_SPAWN_EGG.get()));
            stacks.add(new ItemStack(RCItems.ZOMBIE_LACKEY_SPAWN_EGG.get()));
            stacks.add(new ItemStack(RCItems.CORRUPTED_WART.get()));
            stacks.add(new ItemStack(RCItems.MAGMA_ROTTEN_FLESH.get()));
            stacks.add(new ItemStack(RCItems.FROZEN_ROTTEN_FLESH.get()));
            stacks.add(new ItemStack(RCBlocks.TNT_BARREL.get()));
            stacks.add(new ItemStack(RCBlocks.TREASURE_CHEST.get()));
        }
    );
}