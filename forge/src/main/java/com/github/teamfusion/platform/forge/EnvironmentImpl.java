package com.github.teamfusion.platform.forge;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class EnvironmentImpl {
    public static CreativeModeTab createTab(ResourceLocation location, ItemStack icon) {
        return new CreativeModeTab(location.toString().replace(":", ".")) {
            @Override public ItemStack makeIcon() {
                return icon;
            }
        };
    }
}