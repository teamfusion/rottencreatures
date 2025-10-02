package com.github.teamfusion.rottencreatures.client.level.entities.renderer.deadbeard;

import com.github.teamfusion.rottencreatures.client.level.entities.model.DeadBeardModel;
import com.github.teamfusion.rottencreatures.client.registries.RCModelLayers;
import com.github.teamfusion.rottencreatures.common.level.entities.deadbeard.DeadBeard;
import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DeadBeardRenderer<T extends DeadBeard> extends HumanoidMobRenderer<T, DeadBeardModel<T>> {
    public DeadBeardRenderer(EntityRendererProvider.Context context) {
        super(context, new DeadBeardModel<>(context.bakeLayer(RCModelLayers.DEAD_BEARD)), 1.0F);
        this.addLayer(new HeldTntBarrelLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(T mob) {
        return RottenCreatures.resource("textures/entity/dead_beard.png");
    }

    @Override
    protected void setupRotations(T deadBeard, PoseStack matrices, float bob, float yBodyRot, float partialTick, float scale) {
        super.setupRotations(deadBeard, matrices, bob, yBodyRot, partialTick, scale);
        if ((double) deadBeard.walkAnimation.speed() >= 0.01) {
            float timestamp = deadBeard.walkAnimation.position(partialTick) + 6.0F;
            float degreeModifier = (Math.abs(timestamp % 13.0F - 6.5F) - 3.25F) / 3.25F;
            matrices.mulPose(Axis.ZP.rotationDegrees(6.5F * degreeModifier));
        }
    }
}