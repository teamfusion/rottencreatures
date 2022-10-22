package com.github.teamfusion.rottencreatures.client.renderer.entity;

import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.github.teamfusion.rottencreatures.client.model.DeadBeardModel;
import com.github.teamfusion.rottencreatures.client.model.LayerBuilder;
import com.github.teamfusion.rottencreatures.client.renderer.entity.layers.HeldTntBarrelLayer;
import com.github.teamfusion.rottencreatures.common.entities.DeadBeard;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DeadBeardRenderer<T extends DeadBeard> extends HumanoidMobRenderer<T, DeadBeardModel<T>> {
    public static final LayerBuilder LAYER = LayerBuilder.of("dead_beard");

    public DeadBeardRenderer(EntityRendererProvider.Context context) {
        super(context, new DeadBeardModel<>(context.bakeLayer(LAYER.getMain())), 1.0F);
        this.addLayer(new HeldTntBarrelLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(T mob) {
        return new ResourceLocation(RottenCreatures.MOD_ID, "textures/entity/dead_beard.png");
    }

    @Override
    protected void setupRotations(T deadBeard, PoseStack matrices, float animationProgress, float bodyYaw, float tickDelta) {
        super.setupRotations(deadBeard, matrices, animationProgress, bodyYaw, tickDelta);
        if (!((double)deadBeard.animationSpeed < 0.01D)) {
            float timestamp = deadBeard.animationPosition - deadBeard.animationSpeed * (1.0F - tickDelta) + 6.0F;
            float degreeModifier = (Math.abs(timestamp % 13.0F - 6.5F) - 3.25F) / 3.25F;
            matrices.mulPose(Vector3f.ZP.rotationDegrees(6.5F * degreeModifier));
        }
    }
}