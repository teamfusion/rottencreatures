package com.github.teamfusion.rottencreatures.client.renderer.entity;

import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.github.teamfusion.rottencreatures.client.model.FrostbittenModel;
import com.github.teamfusion.rottencreatures.client.model.LayerBuilder;
import com.github.teamfusion.rottencreatures.common.entities.Frostbitten;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class FrostbittenRenderer extends AbstractZombieRenderer<Frostbitten, FrostbittenModel<Frostbitten>> {
    public static final LayerBuilder LAYER = LayerBuilder.of("frostbitten");

    public FrostbittenRenderer(EntityRendererProvider.Context context) {
        super(context, new FrostbittenModel<>(context.bakeLayer(LAYER.getMain())), new FrostbittenModel<>(context.bakeLayer(LAYER.getInner())), new FrostbittenModel<>(context.bakeLayer(LAYER.getOuter())));
    }

    @Override
    public ResourceLocation getTextureLocation(Frostbitten frostbitten) {
        return new ResourceLocation(RottenCreatures.MOD_ID, "textures/entity/frostbitten.png");
    }
}