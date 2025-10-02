package com.github.teamfusion.rottencreatures.common.registries;

import com.blackgear.platform.core.CoreRegistry;
import com.blackgear.platform.core.RegistryHolder;
import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;

public class RCPotions {
    public static final CoreRegistry<Potion> POTIONS = CoreRegistry.create(BuiltInRegistries.POTION, RottenCreatures.MOD_ID);

    public static final RegistryHolder<Potion> CORRUPTED = POTIONS.registerHolder(
        "corrupted",
        Potion::new
    );
    public static final RegistryHolder<Potion> FREEZE = POTIONS.registerHolder(
        "freeze",
        () -> new Potion(new MobEffectInstance(RCMobEffects.FREEZE.getHolder().get(), 400))
    );
    public static final RegistryHolder<Potion> LONG_FREEZE = POTIONS.registerHolder(
        "long_freeze",
        () -> new Potion(new MobEffectInstance(RCMobEffects.FREEZE.getHolder().get(), 800))
    );
    public static final RegistryHolder<Potion> STRONG_FREEZE = POTIONS.registerHolder(
        "strong_freeze",
        () -> new Potion(new MobEffectInstance(RCMobEffects.FREEZE.getHolder().get(), 200, 1))
    );
}