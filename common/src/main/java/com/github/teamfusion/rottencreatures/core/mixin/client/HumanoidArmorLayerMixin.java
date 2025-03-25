package com.github.teamfusion.rottencreatures.core.mixin.client;

import com.github.teamfusion.rottencreatures.client.rendering.model.entities.MummyModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidArmorLayer.class)
public abstract class HumanoidArmorLayerMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {
    @Shadow @Final private A innerModel;

    public HumanoidArmorLayerMixin(RenderLayerParent<T, M> renderLayerParent) {
        super(renderLayerParent);
    }

    @Inject(
        method = "renderArmorPiece",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/model/HumanoidModel;copyPropertiesTo(Lnet/minecraft/client/model/HumanoidModel;)V",
            shift = At.Shift.AFTER
        )
    )
    public void translateMummyArmor(PoseStack matrices, MultiBufferSource buffer, T entity, EquipmentSlot slot, int packedLight, A model, CallbackInfo ci) {
        if (this.getParentModel() instanceof MummyModel<?>) {
            if (model == this.innerModel) {
                model.leftLeg.y = 10;
                model.rightLeg.y = 10;
                model.body.y = -2;
            } else {
                model.leftLeg.y = 12;
                model.rightLeg.y = 12;
            }
        }
    }
}