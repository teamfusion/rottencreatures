package com.github.teamfusion.platform.forge;

import com.github.teamfusion.platform.Environment;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;

public class EnvironmentImpl {
    public static CreativeModeTab createTab(ResourceLocation location, ItemStack icon) {
        return new CreativeModeTab(location.toString().replace(":", ".")) {
            @Override public ItemStack makeIcon() {
                return icon;
            }
        };
    }

    public static boolean isLoaded(String modid) {
        return ModList.get().isLoaded(modid);
    }

    public static Environment.Platform getPlatform() {
        return Environment.Platform.FORGE;
    }
}