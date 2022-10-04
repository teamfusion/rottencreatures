package com.github.teamfusion.rottencreatures.common.misc;

import com.github.teamfusion.platform.common.registry.ItemRegistry;
import com.github.teamfusion.rottencreatures.common.registries.RCMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class RCFoodProperties {
    public static final FoodProperties FROZEN_ROTTEN_FLESH = ItemRegistry.registerFood(4, 1.0F, true, false, false, () -> new MobEffectInstance(RCMobEffects.FREEZE.get(), 300, 0), 0.8F);
    public static final FoodProperties MAGMA_ROTTEN_FLESH = new FoodProperties.Builder().nutrition(4).saturationMod(1.0F).meat().build();
//    public static final FoodProperties MAGMA_ROTTEN_FLESH = ItemRegistry.registerFood(4, 1.0F, true, false, false, () -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 300, 0), 0.5F);
}