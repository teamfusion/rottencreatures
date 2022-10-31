package com.github.teamfusion.platform.fabric;

import com.github.teamfusion.platform.Environment;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class EnvironmentImpl {
    public static CreativeModeTab createTab(ResourceLocation location, ItemStack icon) {
        return FabricItemGroupBuilder.build(location, () -> icon);
    }

    public static boolean isLoaded(String modid) {
        return FabricLoader.getInstance().isModLoaded(modid);
    }

    public static Environment.Platform getPlatform() {
        return Environment.Platform.FABRIC;
    }
}