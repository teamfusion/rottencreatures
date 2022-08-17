package com.github.teamfusion.rottencreatures.client.renderer.entity;

import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.github.teamfusion.rottencreatures.client.model.BurnedModel;
import com.github.teamfusion.rottencreatures.client.renderer.entity.layers.BurnedLavaLayer;
import com.github.teamfusion.rottencreatures.common.entities.Burned;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class BurnedRenderer extends AbstractZombieRenderer<Burned, BurnedModel> {
    public static final ModelLayerLocation BURNED = new ModelLayerLocation(new ResourceLocation(RottenCreatures.MOD_ID, "burned"), "main");
    public static final ModelLayerLocation BURNED_INNER_ARMOR = new ModelLayerLocation(new ResourceLocation(RottenCreatures.MOD_ID, "burned"), "inner_armor");
    public static final ModelLayerLocation BURNED_OUTER_ARMOR = new ModelLayerLocation(new ResourceLocation(RottenCreatures.MOD_ID, "burned"), "outer_armor");
    public static final ModelLayerLocation BURNED_LAVA_LAYER = new ModelLayerLocation(new ResourceLocation(RottenCreatures.MOD_ID, "burned"), "lava");

    public static final ResourceLocation BURNED_LOCATION = new ResourceLocation(RottenCreatures.MOD_ID, "textures/entity/burned/burned.png");
    public static final ResourceLocation OBSIDIAN_LOCATION = new ResourceLocation(RottenCreatures.MOD_ID, "textures/entity/burned/burned_obsidian.png");
    public static final ResourceLocation CRAZY_LOCATION = new ResourceLocation(RottenCreatures.MOD_ID, "textures/entity/burned/burned_crazy.png");

    public BurnedRenderer(EntityRendererProvider.Context context) {
        super(context, new BurnedModel(context.bakeLayer(BURNED)), new BurnedModel(context.bakeLayer(BURNED_INNER_ARMOR)), new BurnedModel(context.bakeLayer(BURNED_OUTER_ARMOR)));
        this.addLayer(new BurnedLavaLayer(this, context.getModelSet()));
    }

    @Override
    public ResourceLocation getTextureLocation(Burned burned) {
        if (burned.isObsidian()) {
            return OBSIDIAN_LOCATION;
        } else if (burned.isCrazy()) {
            return CRAZY_LOCATION;
        } else {
            return BURNED_LOCATION;
        }
    }
}