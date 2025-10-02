package com.github.teamfusion.rottencreatures.client.level.entities.renderer.burned;

import com.github.teamfusion.rottencreatures.client.level.entities.model.BurnedModel;
import com.github.teamfusion.rottencreatures.client.registries.RCModelLayers;
import com.github.teamfusion.rottencreatures.common.level.entities.burned.Burned;
import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class BurnedRenderer extends AbstractZombieRenderer<Burned, BurnedModel> {
    public static final ResourceLocation BURNED_LOCATION = RottenCreatures.resource("textures/entity/burned/burned.png");
    public static final ResourceLocation OBSIDIAN_LOCATION = RottenCreatures.resource("textures/entity/burned/burned_obsidian.png");
    public static final ResourceLocation CRAZY_LOCATION = RottenCreatures.resource("textures/entity/burned/burned_crazy.png");

    public BurnedRenderer(EntityRendererProvider.Context context) {
        super(
            context,
            new BurnedModel(context.bakeLayer(RCModelLayers.BURNED)),
            new BurnedModel(context.bakeLayer(RCModelLayers.BURNED_INNER_ARMOR)),
            new BurnedModel(context.bakeLayer(RCModelLayers.BURNED_OUTER_ARMOR))
        );
        this.addLayer(new BurnedLavaLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(Burned burned) {
        return burned.isObsidian() ? OBSIDIAN_LOCATION : burned.isCrazy() ? CRAZY_LOCATION : BURNED_LOCATION;
    }

    @Override
    protected void setupRotations(Burned burned, PoseStack matrices, float bob, float yBodyRot, float partialTick, float scale) {
        super.setupRotations(burned, matrices, bob, yBodyRot, partialTick, scale);

        if ((double) burned.walkAnimation.speed() >= 0.01) {
            float timestamp = burned.walkAnimation.position(partialTick) + 6.0F;
            float degreeModifier = (Math.abs(timestamp % 13.0F - 6.5F) - 3.25F) / 3.25F;
            matrices.mulPose(Axis.ZP.rotationDegrees(6.5F * degreeModifier));
        }
    }
}