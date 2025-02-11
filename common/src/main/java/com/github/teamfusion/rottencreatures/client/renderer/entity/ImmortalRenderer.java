package com.github.teamfusion.rottencreatures.client.renderer.entity;

import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.github.teamfusion.rottencreatures.client.RCModelLayers;
import com.github.teamfusion.rottencreatures.client.model.ImmortalModel;
import com.github.teamfusion.rottencreatures.client.renderer.entity.layers.DashAttackLayer;
import com.github.teamfusion.rottencreatures.client.renderer.entity.layers.ImmortalOverlayLayer;
import com.github.teamfusion.rottencreatures.client.renderer.entity.layers.ImpaledLayer;
import com.github.teamfusion.rottencreatures.common.entities.Immortal;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class ImmortalRenderer<T extends Immortal> extends HumanoidMobRenderer<T, ImmortalModel<T>> {
    public ImmortalRenderer(EntityRendererProvider.Context context) {
        super(context, new ImmortalModel<>(context.bakeLayer(RCModelLayers.IMMORTAL)), 0.5F);
        this.addLayer(new HumanoidArmorLayer<>(this, new HumanoidModel<>(context.bakeLayer(RCModelLayers.IMMORTAL_INNER_ARMOR)), new HumanoidModel<>(context.bakeLayer(RCModelLayers.IMMORTAL_OUTER_ARMOR))));
        this.addLayer(new ImpaledLayer<>(this, context.getModelSet()));
        this.addLayer(new DashAttackLayer<>(this, context.getModelSet()));
        this.addLayer(new ImmortalOverlayLayer<>(this, context.getModelSet()));
    }

    @Override
    public ResourceLocation getTextureLocation(Immortal mob) {
        return RottenCreatures.resource("textures/entity/immortal/immortal.png");
    }

    @Override
    protected void setupRotations(T immortal, PoseStack matrices, float animationProgress, float bodyYaw, float tickDelta) {
        super.setupRotations(immortal, matrices, animationProgress, bodyYaw, tickDelta);
        if ((double) immortal.animationSpeed >= 0.01) {
            float timestamp = immortal.animationPosition - immortal.animationSpeed * (1.0F - tickDelta) + 6.0F;
            float degreeModifier = (Math.abs(timestamp % 13.0F - 6.5F) - 3.25F) / 3.25F;
            matrices.mulPose(Vector3f.ZP.rotationDegrees(6.5F * degreeModifier));
        }
    }
}