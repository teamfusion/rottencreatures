package com.github.teamfusion.rottencreatures.client.renderer.entity;

import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.github.teamfusion.rottencreatures.client.model.ModelBuilder;
import com.github.teamfusion.rottencreatures.client.model.SwampyModel;
import com.github.teamfusion.rottencreatures.common.entities.Swampy;
import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class SwampyRenderer extends HumanoidMobRenderer<Swampy, SwampyModel> {
    public static final ModelLayerLocation MAIN = ModelBuilder.createLayer("swampy");
    public static final ModelLayerLocation INNER_ARMOR = ModelBuilder.createLayer("swampy", "inner_armor");
    public static final ModelLayerLocation OUTER_ARMOR = ModelBuilder.createLayer("swampy", "outer_armor");

    public SwampyRenderer(EntityRendererProvider.Context context) {
        super(context, new SwampyModel(context.bakeLayer(MAIN)), 0.5F);
        this.addLayer(new HumanoidArmorLayer<>(this, new SwampyModel(context.bakeLayer(INNER_ARMOR)), new SwampyModel(context.bakeLayer(OUTER_ARMOR))));
    }

    @Override
    public ResourceLocation getTextureLocation(Swampy swampy) {
        return new ResourceLocation(RottenCreatures.MOD_ID, "textures/entity/swampy.png");
    }
}