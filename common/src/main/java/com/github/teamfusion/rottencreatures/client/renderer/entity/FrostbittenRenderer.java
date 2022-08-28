package com.github.teamfusion.rottencreatures.client.renderer.entity;

import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.github.teamfusion.rottencreatures.client.model.FrostbittenModel;
import com.github.teamfusion.rottencreatures.common.entities.Frostbitten;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class FrostbittenRenderer extends AbstractZombieRenderer<Frostbitten, FrostbittenModel> {
    public static final ModelLayerLocation MAIN = new ModelLayerLocation(new ResourceLocation(RottenCreatures.MOD_ID, "frostbitten"), "main");
    public static final ModelLayerLocation INNER_ARMOR = new ModelLayerLocation(new ResourceLocation(RottenCreatures.MOD_ID, "frostbitten"), "inner_armor");
    public static final ModelLayerLocation OUTER_ARMOR = new ModelLayerLocation(new ResourceLocation(RottenCreatures.MOD_ID, "frostbitten"), "outer_armor");

    public FrostbittenRenderer(EntityRendererProvider.Context context) {
        super(context, new FrostbittenModel(context.bakeLayer(MAIN)), new FrostbittenModel(context.bakeLayer(INNER_ARMOR)), new FrostbittenModel(context.bakeLayer(OUTER_ARMOR)));
    }

    @Override
    public ResourceLocation getTextureLocation(Frostbitten zombie) {
        return new ResourceLocation(RottenCreatures.MOD_ID, "textures/entity/frostbitten.png");
    }
}