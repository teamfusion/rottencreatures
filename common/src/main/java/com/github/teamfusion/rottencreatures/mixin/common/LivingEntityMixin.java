package com.github.teamfusion.rottencreatures.mixin.common;

import com.github.teamfusion.rottencreatures.common.registries.RCMobEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow public abstract boolean hasEffect(MobEffect mobEffect);
    @Shadow public abstract @Nullable MobEffectInstance getEffect(MobEffect mobEffect);

    public LivingEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * checks if the entity is frozen
     * this will apply the shaking rendering and the frozen hearts to the player
     */
    @Override
    public boolean isFullyFrozen() {
        return super.isFullyFrozen() || this.hasEffect(RCMobEffects.FREEZE.get());
    }

    /**
     * calculates the amount of ticks that the entity will remain frozen
     * this way the overlay will slowly vanish instead of instantly disappearing.
     */
    @Override
    public int getTicksFrozen() {
        return this.hasEffect(RCMobEffects.FREEZE.get()) ? this.getEffect(RCMobEffects.FREEZE.get()).getDuration() : super.getTicksFrozen();
    }

    /**
     * prevents the entity from being hurt due the effect,
     * but if the entity has the effect and is in powder snow it will be hurt
     */
    @Inject(method = "canFreeze", at = @At("TAIL"), cancellable = true)
    private void rc$canFreeze(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue((!this.hasEffect(RCMobEffects.FREEZE.get()) || (this.hasEffect(RCMobEffects.FREEZE.get()) && this.isInPowderSnow)) && cir.getReturnValue());
    }

    /**
     * prevents the entity from jumping if it has the freeze effect
     * this is because the buffed slowness can be avoided by jumping
     */
    @Inject(method = "jumpFromGround", at = @At("HEAD"), cancellable = true)
    private void rc$jumpFromGround(CallbackInfo ci) {
        if (this.hasEffect(RCMobEffects.FREEZE.get())) ci.cancel();
    }

    /**
     * checks if the freeze effect has been removed
     * so when the entity clears the effect, the freezing will also leave
     */
    @Inject(method = "onEffectRemoved", at = @At("TAIL"))
    private void rc$onEffectRemoved(MobEffectInstance instance, CallbackInfo ci) {
        if (instance.getEffect() == RCMobEffects.FREEZE.get() && !this.isInPowderSnow) {
            this.setTicksFrozen(0);
        }
    }
}