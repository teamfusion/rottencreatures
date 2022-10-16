package com.github.teamfusion.rottencreatures.client.renderer.entity;

import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.github.teamfusion.rottencreatures.client.model.BurnedModel;
import com.github.teamfusion.rottencreatures.client.model.LayerBuilder;
import com.github.teamfusion.rottencreatures.client.renderer.entity.layers.BurnedLavaLayer;
import com.github.teamfusion.rottencreatures.common.entities.Burned;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class BurnedRenderer extends AbstractZombieRenderer<Burned, BurnedModel> {
    public static final LayerBuilder LAYER = LayerBuilder.of("burned");
    public static final ResourceLocation BURNED_LOCATION = new ResourceLocation(RottenCreatures.MOD_ID, "textures/entity/burned/burned.png");
    public static final ResourceLocation OBSIDIAN_LOCATION = new ResourceLocation(RottenCreatures.MOD_ID, "textures/entity/burned/burned_obsidian.png");
    public static final ResourceLocation CRAZY_LOCATION = new ResourceLocation(RottenCreatures.MOD_ID, "textures/entity/burned/burned_crazy.png");

    public BurnedRenderer(EntityRendererProvider.Context context) {
        super(context, new BurnedModel(context.bakeLayer(LAYER.getMain())), new BurnedModel(context.bakeLayer(LAYER.getInner())), new BurnedModel(context.bakeLayer(LAYER.getOuter())));
        this.addLayer(new BurnedLavaLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(Burned burned) {
        return burned.isObsidian() ? OBSIDIAN_LOCATION : burned.isCrazy() ? CRAZY_LOCATION : BURNED_LOCATION;
    }
}