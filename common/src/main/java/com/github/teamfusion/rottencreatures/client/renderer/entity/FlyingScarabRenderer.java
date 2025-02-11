package com.github.teamfusion.rottencreatures.client.renderer.entity;

import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.github.teamfusion.rottencreatures.client.RCModelLayers;
import com.github.teamfusion.rottencreatures.client.model.FlyingScarabModel;
import com.github.teamfusion.rottencreatures.common.entities.FlyingScarab;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class FlyingScarabRenderer<T extends FlyingScarab> extends MobRenderer<T, FlyingScarabModel<T>> {
    public FlyingScarabRenderer(EntityRendererProvider.Context context) {
        super(context, new FlyingScarabModel<>(context.bakeLayer(RCModelLayers.SCARAB)), 0.3F);
    }

    @Override
    protected float getFlipDegrees(T entity) {
        return 180.0F;
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return RottenCreatures.resource("textures/entity/scarab/flying_scarab.png");
    }
}