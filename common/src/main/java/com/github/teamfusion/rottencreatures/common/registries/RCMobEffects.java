package com.github.teamfusion.rottencreatures.common.registries;

import com.blackgear.platform.core.CoreRegistry;
import com.blackgear.platform.core.RegistryHolder;
import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import com.github.teamfusion.rottencreatures.core.mixin.access.MobEffectAccessor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class RCMobEffects {
    public static final CoreRegistry<MobEffect> EFFECTS = CoreRegistry.create(BuiltInRegistries.MOB_EFFECT, RottenCreatures.MOD_ID);

    public static final RegistryHolder<MobEffect> FREEZE = EFFECTS.registerHolder(
        "freeze",
        () -> MobEffectAccessor.createMobEffect(MobEffectCategory.HARMFUL, 5883888)
            .addAttributeModifier(
                Attributes.MOVEMENT_SPEED,
                RottenCreatures.resource("freeze"),
                -0.5F,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
            )
    );
    public static final RegistryHolder<MobEffect> CHANNELLED = EFFECTS.registerHolder(
        "channelled",
        () -> MobEffectAccessor.createMobEffect(MobEffectCategory.HARMFUL, 2064988)
    );
}