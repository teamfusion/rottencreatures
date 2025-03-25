package com.github.teamfusion.rottencreatures.client.rendering.renderer.entities.immortal;

import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import com.github.teamfusion.rottencreatures.client.registries.RCModelLayers;
import com.github.teamfusion.rottencreatures.client.rendering.model.entities.ImmortalModel;
import com.github.teamfusion.rottencreatures.client.rendering.model.entities.ImmortalOverlayModel;
import com.github.teamfusion.rottencreatures.common.level.entities.living.immortal.Immortal;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class ImmortalOverlayLayer<T extends Immortal> extends RenderLayer<T, ImmortalModel<T>> {
    private final ImmortalOverlayModel<T> overlay;

    public ImmortalOverlayLayer(RenderLayerParent<T, ImmortalModel<T>> parent, EntityModelSet modelSet) {
        super(parent);
        this.overlay = new ImmortalOverlayModel<>(modelSet.bakeLayer(RCModelLayers.IMMORTAL_OVERLAY));
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource source, int light, T entity, float angle, float distance, float tickDelta, float animationProgress, float yaw, float pitch) {
        matrices.pushPose();
        matrices.scale(2.25F, 2.25F, 2.25F);
        matrices.translate(0.0F, -0.75F, 0.0F);
        VertexConsumer vertices = source.getBuffer(RenderType.entityCutoutNoCull(RottenCreatures.resource("textures/entity/immortal/immortal_overlay.png")));
        this.overlay.renderToBuffer(matrices, vertices, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrices.popPose();
    }
}