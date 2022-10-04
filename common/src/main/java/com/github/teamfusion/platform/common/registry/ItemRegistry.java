package com.github.teamfusion.platform.common.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

public class ItemRegistry {
    @ExpectPlatform
    public static void registerFuel(ItemLike item, int ticks) {
        throw new AssertionError();
    }


    @ExpectPlatform
    public static FoodProperties registerFood(int nutrition, float saturation, boolean meat, boolean alwaysEat, boolean fastFood, Supplier<MobEffectInstance> effect, float probability) {
        throw new AssertionError();
    }
}