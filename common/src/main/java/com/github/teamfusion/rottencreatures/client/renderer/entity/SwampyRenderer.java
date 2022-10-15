package com.github.teamfusion.rottencreatures.client.renderer.entity;

import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.github.teamfusion.rottencreatures.client.model.LayerBuilder;
import com.github.teamfusion.rottencreatures.client.model.SwampyModel;
import com.github.teamfusion.rottencreatures.common.entities.Swampy;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class SwampyRenderer extends HumanoidMobRenderer<Swampy, SwampyModel> {
    public static final LayerBuilder LAYER = LayerBuilder.of("swampy");

    public SwampyRenderer(EntityRendererProvider.Context context) {
        super(context, new SwampyModel(context.bakeLayer(LAYER.getMain())), 0.5F);
        this.addLayer(new HumanoidArmorLayer<>(this, new SwampyModel(context.bakeLayer(LAYER.getInner())), new SwampyModel(context.bakeLayer(LAYER.getOuter()))));
    }

    @Override
    public ResourceLocation getTextureLocation(Swampy swampy) {
        return new ResourceLocation(RottenCreatures.MOD_ID, "textures/entity/swampy.png");
    }
}