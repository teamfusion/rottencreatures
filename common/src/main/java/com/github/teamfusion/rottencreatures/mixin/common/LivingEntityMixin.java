package com.github.teamfusion.rottencreatures.mixin.common;

import com.github.teamfusion.rottencreatures.common.registries.RCMobEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow public abstract boolean hasEffect(MobEffect mobEffect);

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean isFullyFrozen() {
        return super.isFullyFrozen() || this.hasEffect(RCMobEffects.FREEZE.get());
    }
}