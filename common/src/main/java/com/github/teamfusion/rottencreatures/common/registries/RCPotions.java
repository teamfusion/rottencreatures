package com.github.teamfusion.rottencreatures.common.registries;

import com.github.teamfusion.platform.CoreRegistry;
import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.github.teamfusion.rottencreatures.mixin.access.PotionBrewingAccessor;
import net.minecraft.core.Registry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;

import java.util.function.Supplier;

public class RCPotions {
    public static final CoreRegistry<Potion> POTIONS = CoreRegistry.create(Registry.POTION, RottenCreatures.MOD_ID);

    public static final Supplier<Potion> CORRUPTED = create("corrupted", Potion::new);
    public static final Supplier<Potion> FREEZE = create("freeze", () -> new Potion(new MobEffectInstance(RCMobEffects.FREEZE.get(), 400)));
    public static final Supplier<Potion> LONG_FREEZE = create("long_freeze", () -> new Potion(new MobEffectInstance(RCMobEffects.FREEZE.get(), 800)));
    public static final Supplier<Potion> STRONG_FREEZE = create("strong_freeze", () -> new Potion(new MobEffectInstance(RCMobEffects.FREEZE.get(), 200, 1)));

    private static Supplier<Potion> create(String key, Supplier<Potion> potion) {
        return POTIONS.register(key, potion);
    }

    public static void bootstrap() {
        PotionBrewingAccessor.callAddMix(Potions.WATER, RCItems.CORRUPTED_WART.get(), RCPotions.CORRUPTED.get());
        PotionBrewingAccessor.callAddMix(RCPotions.CORRUPTED.get(), RCItems.FROZEN_ROTTEN_FLESH.get(), RCPotions.FREEZE.get());
        PotionBrewingAccessor.callAddMix(RCPotions.FREEZE.get(), Items.REDSTONE, RCPotions.LONG_FREEZE.get());
        PotionBrewingAccessor.callAddMix(RCPotions.FREEZE.get(), Items.GLOWSTONE_DUST, RCPotions.STRONG_FREEZE.get());
    }
}