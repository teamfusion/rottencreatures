package com.github.teamfusion.rottencreatures.core.platform.fabric;

import com.github.teamfusion.rottencreatures.core.platform.Environment;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class EnvironmentImpl {
    public static CreativeModeTab createTab(ResourceLocation location, Supplier<ItemStack> icon) {
        return FabricItemGroupBuilder.build(location, icon);
    }

    public static boolean isLoaded(String modid) {
        return FabricLoader.getInstance().isModLoaded(modid);
    }

    public static Environment.Platform getPlatform() {
        return Environment.Platform.FABRIC;
    }
}