package com.github.teamfusion.rottencreatures.client.renderer.entity.layers;

import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.github.teamfusion.rottencreatures.client.model.BurnedModel;
import com.github.teamfusion.rottencreatures.client.renderer.entity.BurnedRenderer;
import com.github.teamfusion.rottencreatures.common.entities.Burned;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class BurnedLavaLayer extends RenderLayer<Burned, BurnedModel> {
    private static final ResourceLocation BURNED_LAVA_LOCATION = new ResourceLocation(RottenCreatures.MOD_ID, "textures/entity/burned/burned_lava_layer.png");
    private static final ResourceLocation CRAZY_LAVA_LOCATION = new ResourceLocation(RottenCreatures.MOD_ID, "textures/entity/burned/burned_crazy_lava_layer.png");

    public BurnedLavaLayer(RenderLayerParent<Burned, BurnedModel> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource source, int light, Burned burned, float angle, float distance, float tickDelta, float animationProgress, float yaw, float pitch) {
        if (!burned.isInvisible() && !burned.isObsidian()) {
            ResourceLocation location = burned.isCrazy() ? CRAZY_LAVA_LOCATION : BURNED_LAVA_LOCATION;
            VertexConsumer vertices = source.getBuffer(RenderType.eyes(location));
            this.getParentModel().renderToBuffer(matrices, vertices, light, LivingEntityRenderer.getOverlayCoords(burned, 0.0F), 1.0F, 1.0F, 10.F, 1.0F);
        }
    }
}