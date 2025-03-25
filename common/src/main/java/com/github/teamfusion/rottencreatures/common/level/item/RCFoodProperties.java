package com.github.teamfusion.rottencreatures.common.level.item;

import com.github.teamfusion.rottencreatures.common.registries.RCMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

import java.util.function.Supplier;

public class RCFoodProperties {
    public static final FoodProperties FROZEN_ROTTEN_FLESH = register(
        4,
        1.0F,
        true,
        false,
        false,
        () -> new MobEffectInstance(RCMobEffects.FREEZE.get(), 300),
        0.8F
    );
    public static final FoodProperties MAGMA_ROTTEN_FLESH = register(
        4,
        1.0F,
        true,
        false,
        false,
        () -> new MobEffectInstance(MobEffects.HUNGER, 300),
        0.8F
    );

    public static FoodProperties register(
        int nutrition,
        float saturation,
        boolean meat,
        boolean alwaysEat,
        boolean fastFood,
        Supplier<MobEffectInstance> effect,
        float probability
    ) {
        FoodProperties.Builder builder = new FoodProperties.Builder();
        builder.nutrition(nutrition);
        builder.saturationMod(saturation);
        builder.effect(effect.get(), probability);

        if (meat) builder.meat();
        if (alwaysEat) builder.alwaysEat();
        if (fastFood) builder.fast();

        return builder.build();
    }
}