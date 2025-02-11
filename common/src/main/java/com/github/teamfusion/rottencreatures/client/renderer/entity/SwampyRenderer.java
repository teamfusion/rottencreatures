package com.github.teamfusion.rottencreatures.client.renderer.entity;

import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.github.teamfusion.rottencreatures.client.RCModelLayers;
import com.github.teamfusion.rottencreatures.client.model.SwampyModel;
import com.github.teamfusion.rottencreatures.common.entities.Swampy;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class SwampyRenderer extends HumanoidMobRenderer<Swampy, SwampyModel> {
    public SwampyRenderer(EntityRendererProvider.Context context) {
        super(context, new SwampyModel(context.bakeLayer(RCModelLayers.SWAMPY)), 0.5F);
        this.addLayer(
            new HumanoidArmorLayer<>(
                this,
                new SwampyModel(context.bakeLayer(RCModelLayers.SWAMPY_INNER_ARMOR)),
                new SwampyModel(context.bakeLayer(RCModelLayers.SWAMPY_OUTER_ARMOR))
            )
        );
    }

    @Override
    public ResourceLocation getTextureLocation(Swampy swampy) {
        return RottenCreatures.resource("textures/entity/swampy.png");
    }
}