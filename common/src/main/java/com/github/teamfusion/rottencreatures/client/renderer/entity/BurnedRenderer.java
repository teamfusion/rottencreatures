package com.github.teamfusion.rottencreatures.client.renderer.entity;

import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.github.teamfusion.rottencreatures.client.model.BurnedModel;
import com.github.teamfusion.rottencreatures.client.model.LayerBuilder;
import com.github.teamfusion.rottencreatures.client.renderer.entity.layers.BurnedLavaLayer;
import com.github.teamfusion.rottencreatures.common.entities.Burned;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class BurnedRenderer extends AbstractZombieRenderer<Burned, BurnedModel> {
    public static final LayerBuilder LAYER = LayerBuilder.of("burned");
    public static final ResourceLocation BURNED_LOCATION = new ResourceLocation(RottenCreatures.MOD_ID, "textures/entity/burned/burned.png");
    public static final ResourceLocation OBSIDIAN_LOCATION = new ResourceLocation(RottenCreatures.MOD_ID, "textures/entity/burned/burned_obsidian.png");
    public static final ResourceLocation CRAZY_LOCATION = new ResourceLocation(RottenCreatures.MOD_ID, "textures/entity/burned/burned_crazy.png");

    public BurnedRenderer(EntityRendererProvider.Context context) {
        super(context, new BurnedModel(context.bakeLayer(LAYER.getMain())), new BurnedModel(context.bakeLayer(LAYER.getInner())), new BurnedModel(context.bakeLayer(LAYER.getOuter())));
        this.addLayer(new BurnedLavaLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(Burned burned) {
        return burned.isObsidian() ? OBSIDIAN_LOCATION : burned.isCrazy() ? CRAZY_LOCATION : BURNED_LOCATION;
    }

    @Override
    protected void setupRotations(Burned burned, PoseStack matrices, float animationProgress, float bodyYaw, float tickDelta) {
        super.setupRotations(burned, matrices, animationProgress, bodyYaw, tickDelta);
        if (!((double)burned.animationSpeed < 0.01D)) {
            float timestamp = burned.animationPosition - burned.animationSpeed * (1.0F - tickDelta) + 6.0F;
            float degreeModifier = (Math.abs(timestamp % 13.0F - 6.5F) - 3.25F) / 3.25F;
            matrices.mulPose(Vector3f.ZP.rotationDegrees(6.5F * degreeModifier));
        }
    }
}