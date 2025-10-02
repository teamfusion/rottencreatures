package com.github.teamfusion.rottencreatures.common.registries;

import com.blackgear.platform.core.CoreRegistry;
import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import com.github.teamfusion.rottencreatures.core.data.LangConstants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.function.Supplier;

public class RCCreativeModeTabs {
    public static final CoreRegistry<CreativeModeTab> CREATIVE_TABS = CoreRegistry.create(BuiltInRegistries.CREATIVE_MODE_TAB, RottenCreatures.MOD_ID);

    public static final Supplier<CreativeModeTab> TAB = CREATIVE_TABS.register(
        "rotten_creatures",
        () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
            .title(Component.translatable(LangConstants.CREATIVE_TAB))
            .icon(() -> new ItemStack(Items.ROTTEN_FLESH))
            .displayItems((parameters, output) -> {
                output.accept(RCBlocks.BURNED_HEAD.get());
                output.accept(RCBlocks.FROSTBITTEN_HEAD.get());
                output.accept(RCBlocks.SWAMPY_HEAD.get());
                output.accept(RCBlocks.UNDEAD_MINER_HEAD.get());
                output.accept(RCBlocks.MUMMY_HEAD.get());
                output.accept(RCBlocks.GLACIAL_HUNTER_HEAD.get());
                output.accept(RCBlocks.DEAD_BEARD_HEAD.get());
                output.accept(RCBlocks.IMMORTAL_HEAD.get());
                output.accept(RCBlocks.ZAP_HEAD.get());
                output.accept(RCItems.BURNED_SPAWN_EGG.get());
                output.accept(RCItems.FROSTBITTEN_SPAWN_EGG.get());
                output.accept(RCItems.SWAMPY_SPAWN_EGG.get());
                output.accept(RCItems.UNDEAD_MINER_SPAWN_EGG.get());
                output.accept(RCItems.MUMMY_SPAWN_EGG.get());
                output.accept(RCItems.GLACIAL_HUNTER_SPAWN_EGG.get());
                output.accept(RCItems.DEAD_BEARD_SPAWN_EGG.get());
                output.accept(RCItems.IMMORTAL_SPAWN_EGG.get());
                output.accept(RCItems.ZAP_SPAWN_EGG.get());
                output.accept(RCItems.SCARAB_SPAWN_EGG.get());
                output.accept(RCItems.HUNTER_WOLF_SPAWN_EGG.get());
                output.accept(RCItems.SKELETON_LACKEY_SPAWN_EGG.get());
                output.accept(RCItems.ZOMBIE_LACKEY_SPAWN_EGG.get());
                output.accept(RCItems.CORRUPTED_WART.get());
                output.accept(RCItems.MAGMA_ROTTEN_FLESH.get());
                output.accept(RCItems.FROZEN_ROTTEN_FLESH.get());
                output.accept(RCBlocks.TNT_BARREL.get());
                output.accept(RCBlocks.TREASURE_CHEST.get());
            })
            .build()
    );
}