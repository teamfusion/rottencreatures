package com.github.teamfusion.rottencreatures.client.level.entities.renderer.swampy;

import com.github.teamfusion.rottencreatures.client.level.entities.model.SwampyModel;
import com.github.teamfusion.rottencreatures.client.registries.RCModelLayers;
import com.github.teamfusion.rottencreatures.common.level.entities.swampy.Swampy;
import com.github.teamfusion.rottencreatures.core.RottenCreatures;
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
                new SwampyModel(context.bakeLayer(RCModelLayers.SWAMPY_OUTER_ARMOR)),
                context.getModelManager()
            )
        );
    }

    @Override
    public ResourceLocation getTextureLocation(Swampy swampy) {
        return RottenCreatures.resource("textures/entity/swampy.png");
    }
}