package com.github.teamfusion.rottencreatures.client.level.entities.renderer.glacialhunter;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.world.entity.animal.Wolf;

public class HunterWolfRenderer extends WolfRenderer {
    public HunterWolfRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void scale(Wolf wolf, PoseStack matrices, float partialTickTime) {
        matrices.scale(1.2F, 1.2F, 1.2F);
    }
}