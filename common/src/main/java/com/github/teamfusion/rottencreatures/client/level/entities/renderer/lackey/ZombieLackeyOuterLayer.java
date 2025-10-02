package com.github.teamfusion.rottencreatures.client.level.entities.renderer.lackey;

import com.github.teamfusion.rottencreatures.client.registries.RCModelLayers;
import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.DrownedModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.monster.Zombie;

public class ZombieLackeyOuterLayer<T extends Zombie> extends RenderLayer<T, DrownedModel<T>> {
    private final DrownedModel<T> model;

    public ZombieLackeyOuterLayer(RenderLayerParent<T, DrownedModel<T>> parent, EntityModelSet modelSet) {
        super(parent);
        this.model = new DrownedModel<>(modelSet.bakeLayer(RCModelLayers.ZOMBIE_LACKEY_OUTER_LAYER));
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource buffer, int packedLight, T lackey, float limgSwing, float limgSwingAmount, float partialTick, float ageInTicks, float netHeadYaw, float headPitch) {
        coloredCutoutModelCopyLayerRender(
            this.getParentModel(),
            this.model,
            RottenCreatures.resource("textures/entity/zombie_lackey_outer_layer.png"),
            matrices,
            buffer,
            packedLight,
            lackey,
            limgSwing,
            limgSwingAmount,
            ageInTicks,
            netHeadYaw,
            headPitch,
            partialTick,
            -1
        );
    }
}