package com.github.teamfusion.rottencreatures.client.level.entities.renderer.burned;

import com.github.teamfusion.rottencreatures.client.level.entities.layer.RottenCustomHeadLayer;
import com.github.teamfusion.rottencreatures.client.level.entities.layer.RottenElytraLayer;
import com.github.teamfusion.rottencreatures.client.level.entities.layer.RottenItemInHandLayer;
import com.github.teamfusion.rottencreatures.client.level.entities.layer.RottenArmorLayer;
import com.github.teamfusion.rottencreatures.client.level.entities.model.BurnedModel;
import com.github.teamfusion.rottencreatures.client.registries.RCModelLayers;
import com.github.teamfusion.rottencreatures.common.level.entities.burned.Burned;
import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BurnedRenderer extends MobRenderer<Burned, BurnedModel> {
    public static final ResourceLocation BURNED_LOCATION = RottenCreatures.resource("textures/entity/burned/burned.png");
    public static final ResourceLocation OBSIDIAN_LOCATION = RottenCreatures.resource("textures/entity/burned/burned_obsidian.png");
    public static final ResourceLocation CRAZY_LOCATION = RottenCreatures.resource("textures/entity/burned/burned_crazy.png");

    public BurnedRenderer(EntityRendererProvider.Context context) {
        super(context, new BurnedModel(context.bakeLayer(RCModelLayers.BURNED)), 0.5F);
        this.addLayer(new BurnedLavaLayer(this));
        this.addLayer(new RottenArmorLayer<>(
            this,
            new BurnedModel(context.bakeLayer(RCModelLayers.BURNED_INNER_ARMOR)),
            new BurnedModel(context.bakeLayer(RCModelLayers.BURNED_OUTER_ARMOR)),
            context.getModelManager()
        ));
        this.addLayer(new RottenCustomHeadLayer<>(this, context.getModelSet(), context.getItemInHandRenderer()));
        this.addLayer(new RottenItemInHandLayer<>(this, context.getItemInHandRenderer()));
        this.addLayer(new RottenElytraLayer<>(this, context.getModelSet()));
    }

    @Override
    public ResourceLocation getTextureLocation(Burned burned) {
        return burned.isObsidian() ? OBSIDIAN_LOCATION : burned.isCrazy() ? CRAZY_LOCATION : BURNED_LOCATION;
    }
}