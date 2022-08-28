package com.github.teamfusion.rottencreatures.common.registries;

import com.github.teamfusion.platform.CoreRegistry;
import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.github.teamfusion.rottencreatures.mixin.access.MobEffectAccessor;
import net.minecraft.core.Registry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

import java.util.function.Supplier;

public class RCMobEffects {
    public static final CoreRegistry<MobEffect> EFFECTS = CoreRegistry.create(Registry.MOB_EFFECT, RottenCreatures.MOD_ID);

    public static final Supplier<MobEffect> FREEZE = EFFECTS.register("freeze", () -> MobEffectAccessor.createMobEffect(MobEffectCategory.HARMFUL, 5883888));
}