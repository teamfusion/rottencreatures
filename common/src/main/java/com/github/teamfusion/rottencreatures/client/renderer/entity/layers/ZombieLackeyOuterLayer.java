package com.github.teamfusion.rottencreatures.client.renderer.entity.layers;

import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import com.github.teamfusion.rottencreatures.client.renderer.entity.ZombieLackeyRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.DrownedModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Zombie;

public class ZombieLackeyOuterLayer<T extends Zombie> extends RenderLayer<T, DrownedModel<T>> {
    private final DrownedModel<T> model;

    public ZombieLackeyOuterLayer(RenderLayerParent<T, DrownedModel<T>> parent, EntityModelSet modelSet) {
        super(parent);
        this.model = new DrownedModel<>(modelSet.bakeLayer(ZombieLackeyRenderer.OUTER_LAYER));
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource source, int light, T lackey, float angle, float distance, float tickDelta, float animationProgress, float yaw, float pitch) {
        coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, new ResourceLocation(RottenCreatures.MOD_ID, "textures/entity/zombie_lackey_outer_layer.png"), matrices, source, light, lackey, angle, distance, animationProgress, yaw, pitch, tickDelta, 1.0F, 1.0F, 1.0F);
    }
}