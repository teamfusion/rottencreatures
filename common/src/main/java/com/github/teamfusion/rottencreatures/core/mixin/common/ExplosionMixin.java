package com.github.teamfusion.rottencreatures.core.mixin.common;

import com.github.teamfusion.rottencreatures.common.entities.PrimedTntBarrel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Explosion.class)
public class ExplosionMixin {
    @Shadow @Final @Nullable private Entity source;

    /**
     * checks for the owner of the PrimedTntBarrel
     */
    @Inject(method = "getSourceMob", at = @At("TAIL"), cancellable = true)
    private void getSource(CallbackInfoReturnable<LivingEntity> cir) {
        if (this.source instanceof PrimedTntBarrel barrel) {
            cir.setReturnValue(barrel.getOwner());
        }
    }
}