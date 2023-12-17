package com.github.teamfusion.rottencreatures.core.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class Environment {
    @ExpectPlatform
    public static CreativeModeTab createTab(ResourceLocation location, Supplier<ItemStack> icon) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean isLoaded(String modid) {
        throw new AssertionError();
    }

    public static boolean isLoadedAt(Platform platform, String modId) {
        return isLoaded(modId) && getPlatform() == platform || getPlatform() != platform;
    }

    @ExpectPlatform
    public static Platform getPlatform() {
        throw new AssertionError();
    }

    public static boolean forge() {
        return getPlatform() == Platform.FORGE;
    }

    public static boolean fabric() {
        return getPlatform() == Platform.FABRIC;
    }

    public enum Platform {
        FORGE,
        FABRIC
    }
}