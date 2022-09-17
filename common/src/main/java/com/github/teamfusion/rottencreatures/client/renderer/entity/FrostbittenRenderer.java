package com.github.teamfusion.rottencreatures.client.renderer.entity;

import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.github.teamfusion.rottencreatures.client.model.FrostbittenModel;
import com.github.teamfusion.rottencreatures.client.model.ModelBuilder;
import com.github.teamfusion.rottencreatures.common.entities.Frostbitten;
import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class FrostbittenRenderer extends AbstractZombieRenderer<Frostbitten, FrostbittenModel<Frostbitten>> {
    public static final ModelLayerLocation MAIN = ModelBuilder.createLayer(RCEntityTypes.FROSTBITTEN.get());
    public static final ModelLayerLocation INNER_ARMOR = ModelBuilder.createLayer(RCEntityTypes.FROSTBITTEN.get(), "inner_armor");
    public static final ModelLayerLocation OUTER_ARMOR = ModelBuilder.createLayer(RCEntityTypes.FROSTBITTEN.get(), "outer_armor");

    public FrostbittenRenderer(EntityRendererProvider.Context context) {
        super(context, new FrostbittenModel<>(context.bakeLayer(MAIN)), new FrostbittenModel<>(context.bakeLayer(INNER_ARMOR)), new FrostbittenModel<>(context.bakeLayer(OUTER_ARMOR)));
    }

    @Override
    public ResourceLocation getTextureLocation(Frostbitten frostbitten) {
        return new ResourceLocation(RottenCreatures.MOD_ID, "textures/entity/frostbitten.png");
    }
}