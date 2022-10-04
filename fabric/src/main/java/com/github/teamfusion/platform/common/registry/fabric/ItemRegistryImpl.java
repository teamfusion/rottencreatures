package com.github.teamfusion.platform.common.registry.fabric;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

public class ItemRegistryImpl {
    public static void registerFuel(ItemLike item, int ticks) {
        FuelRegistry.INSTANCE.add(item, ticks);
    }

    public static FoodProperties registerFood(int nutrition, float saturation, boolean meat, boolean alwaysEat, boolean fastFood, Supplier<MobEffectInstance> effect, float probability) {
        FoodProperties.Builder builder = new FoodProperties.Builder();
        builder.nutrition(nutrition);
        builder.saturationMod(saturation);
        if (meat) builder.meat();
        if (alwaysEat) builder.alwaysEat();
        if (fastFood) builder.fast();
        builder.effect(effect.get(), probability);
        return builder.build();
    }
}
