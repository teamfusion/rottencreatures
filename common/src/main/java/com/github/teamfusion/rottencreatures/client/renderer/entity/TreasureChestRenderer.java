package com.github.teamfusion.rottencreatures.client.renderer.entity;

import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.github.teamfusion.rottencreatures.client.model.LayerBuilder;
import com.github.teamfusion.rottencreatures.client.model.TreasureChestModel;
import com.github.teamfusion.rottencreatures.common.entities.TreasureChest;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class TreasureChestRenderer<T extends TreasureChest> extends LivingEntityRenderer<T, TreasureChestModel<T>> {
    public static final LayerBuilder LAYER = LayerBuilder.of("treasure_chest");

    public TreasureChestRenderer(EntityRendererProvider.Context context) {
        super(context, new TreasureChestModel<>(context.bakeLayer(LAYER.getMain())), 0.2F);
    }

    @Override
    protected void renderNameTag(T entity, Component component, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {}

    @Override
    public ResourceLocation getTextureLocation(TreasureChest entity) {
        return new ResourceLocation(RottenCreatures.MOD_ID, "textures/entity/treasure_chest.png");
    }
}