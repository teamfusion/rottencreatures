package com.github.teamfusion.rottencreatures.client.level.entities.renderer.immortal;

import com.github.teamfusion.rottencreatures.client.level.entities.model.ImmortalModel;
import com.github.teamfusion.rottencreatures.common.level.entities.immortal.Immortal;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.TridentModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class ImpaledLayer<T extends Immortal> extends RenderLayer<T, ImmortalModel<T>> {
    private final TridentModel trident;

    public ImpaledLayer(RenderLayerParent<T, ImmortalModel<T>> parent, EntityModelSet modelSet) {
        super(parent);
        this.trident = new TridentModel(modelSet.bakeLayer(ModelLayers.TRIDENT));
    }

    /**
     * renders a vanilla trident on the Immortal's chest! if the Immortal is powered then it will apply a glint into the Trident.
     */
    @Override
    public void render(PoseStack matrices, MultiBufferSource source, int light, T entity, float angle, float distance, float tickDelta, float animationProgress, float yaw, float pitch) {
        matrices.pushPose();
        matrices.translate(0.0F, 0.5F, -0.75F);
        matrices.mulPose(Axis.XP.rotationDegrees(90.0F));
        VertexConsumer vertices = ItemRenderer.getFoilBufferDirect(source, this.trident.renderType(ResourceLocation.withDefaultNamespace("textures/entity/trident.png")), false, entity.hasPower());
        this.trident.renderToBuffer(matrices, vertices, light, OverlayTexture.NO_OVERLAY);
        matrices.popPose();
    }
}