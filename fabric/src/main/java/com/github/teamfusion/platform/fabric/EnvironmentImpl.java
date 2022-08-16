package com.github.teamfusion.platform.fabric;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class EnvironmentImpl {
    public static CreativeModeTab createTab(ResourceLocation location, ItemStack icon) {
        return FabricItemGroupBuilder.build(location, () -> icon);
    }
}