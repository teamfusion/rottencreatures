package com.github.teamfusion.rottencreatures.client.renderer.entity;

import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.github.teamfusion.rottencreatures.client.RCModelLayers;
import com.github.teamfusion.rottencreatures.client.model.ScarabModel;
import com.github.teamfusion.rottencreatures.common.entities.Scarab;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ScarabRenderer<T extends Scarab> extends MobRenderer<T, ScarabModel<T>> {
    public ScarabRenderer(EntityRendererProvider.Context context) {
        super(context, new ScarabModel<>(context.bakeLayer(RCModelLayers.SCARAB)), 0.3F);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return RottenCreatures.resource("textures/entity/scarab/scarab.png");
    }
}