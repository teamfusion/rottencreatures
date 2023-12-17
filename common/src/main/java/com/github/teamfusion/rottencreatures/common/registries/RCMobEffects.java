package com.github.teamfusion.rottencreatures.common.registries;

import com.github.teamfusion.rottencreatures.core.platform.CoreRegistry;
import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import com.github.teamfusion.rottencreatures.core.mixin.access.MobEffectAccessor;
import net.minecraft.core.Registry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.function.Supplier;

public class RCMobEffects {
    public static final CoreRegistry<MobEffect> EFFECTS = CoreRegistry.create(Registry.MOB_EFFECT, RottenCreatures.MOD_ID);

    public static final Supplier<MobEffect> FREEZE = EFFECTS.register("freeze", () -> MobEffectAccessor.createMobEffect(MobEffectCategory.HARMFUL, 5883888).addAttributeModifier(Attributes.MOVEMENT_SPEED, "8a01a7ba-52ed-4d58-85e4-4f6e22d8a9ab", -0.5F, AttributeModifier.Operation.MULTIPLY_TOTAL));
    public static final Supplier<MobEffect> CHANNELLED = EFFECTS.register("channelled", () -> MobEffectAccessor.createMobEffect(MobEffectCategory.HARMFUL, 2064988));
}