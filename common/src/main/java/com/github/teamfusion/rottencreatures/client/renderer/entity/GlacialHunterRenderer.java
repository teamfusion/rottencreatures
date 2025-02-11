package com.github.teamfusion.rottencreatures.client.renderer.entity;

import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.github.teamfusion.rottencreatures.client.RCModelLayers;
import com.github.teamfusion.rottencreatures.client.model.GlacialHunterModel;
import com.github.teamfusion.rottencreatures.common.entities.GlacialHunter;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GlacialHunterRenderer<T extends GlacialHunter> extends HumanoidMobRenderer<T, GlacialHunterModel<T>> {
    public GlacialHunterRenderer(EntityRendererProvider.Context context) {
        super(context, new GlacialHunterModel<>(context.bakeLayer(RCModelLayers.GLACIAL_HUNTER)), 0.6F);
    }

    @Override
    public ResourceLocation getTextureLocation(T hunter) {
        return RottenCreatures.resource("textures/entity/glacial_hunter.png");
    }
}