package com.github.teamfusion.rottencreatures.client.renderer.entity.layers;

import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import com.github.teamfusion.rottencreatures.client.model.ImmortalModel;
import com.github.teamfusion.rottencreatures.client.model.ImmortalOverlayModel;
import com.github.teamfusion.rottencreatures.client.model.LayerBuilder;
import com.github.teamfusion.rottencreatures.common.entities.Immortal;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class ImmortalOverlayLayer<T extends Immortal> extends RenderLayer<T, ImmortalModel<T>> {
    public static final LayerBuilder LAYER = LayerBuilder.of("immortal_overlay");
    private final ImmortalOverlayModel<T> overlay;

    public ImmortalOverlayLayer(RenderLayerParent<T, ImmortalModel<T>> parent, EntityModelSet modelSet) {
        super(parent);
        this.overlay = new ImmortalOverlayModel<>(modelSet.bakeLayer(LAYER.getMain()));
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource source, int light, T entity, float angle, float distance, float tickDelta, float animationProgress, float yaw, float pitch) {
        matrices.pushPose();
        matrices.scale(2.25F, 2.25F, 2.25F);
        matrices.translate(0.0F, -0.75F, 0.0F);
        VertexConsumer vertices = source.getBuffer(RenderType.entityCutoutNoCull(new ResourceLocation(RottenCreatures.MOD_ID, "textures/entity/immortal/immortal_overlay.png")));
        this.overlay.renderToBuffer(matrices, vertices, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrices.popPose();
    }
}