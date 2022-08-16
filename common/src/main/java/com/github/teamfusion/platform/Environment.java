package com.github.teamfusion.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class Environment {
    @ExpectPlatform
    public static CreativeModeTab createTab(ResourceLocation location, ItemStack icon) {
        throw new AssertionError();
    }
}