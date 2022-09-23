package com.github.teamfusion.rottencreatures.client.renderer.entity.layers;

import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.github.teamfusion.rottencreatures.client.model.BurnedModel;
import com.github.teamfusion.rottencreatures.client.renderer.entity.BurnedRenderer;
import com.github.teamfusion.rottencreatures.common.entities.Burned;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

public class BurnedLavaLayer extends RenderLayer<Burned, BurnedModel> {
    private static final ResourceLocation BURNED_LAVA_LOCATION = new ResourceLocation(RottenCreatures.MOD_ID, "textures/entity/burned/burned_lava_layer.png");
    private static final ResourceLocation CRAZY_LAVA_LOCATION = new ResourceLocation(RottenCreatures.MOD_ID, "textures/entity/burned/burned_crazy_lava_layer.png");
    private final BurnedModel model;

    public BurnedLavaLayer(RenderLayerParent<Burned, BurnedModel> parent, EntityModelSet set) {
        super(parent);
        this.model = new BurnedModel(set.bakeLayer(BurnedRenderer.LAVA));
    }

    @Override
    public void render(PoseStack stack, MultiBufferSource buffer, int light, Burned burned, float angle, float distance, float tickDelta, float animationProgress, float yaw, float pitch) {
        ResourceLocation layer = burned.isCrazy() ? CRAZY_LAVA_LOCATION : BURNED_LAVA_LOCATION;
        if (!burned.isObsidian()) coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, layer, stack, buffer, 15728640, burned, angle, distance, animationProgress, yaw, pitch, tickDelta, 1.0F, 1.0F, 1.0F);
    }
}