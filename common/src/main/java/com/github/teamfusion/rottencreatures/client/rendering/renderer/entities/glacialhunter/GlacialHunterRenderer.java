package com.github.teamfusion.rottencreatures.client.rendering.renderer.entities.glacialhunter;

import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import com.github.teamfusion.rottencreatures.client.registries.RCModelLayers;
import com.github.teamfusion.rottencreatures.client.rendering.model.entities.GlacialHunterModel;
import com.github.teamfusion.rottencreatures.common.level.entities.living.glacialhunter.GlacialHunter;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GlacialHunterRenderer<T extends GlacialHunter> extends HumanoidMobRenderer<T, GlacialHunterModel<T>> {
    public GlacialHunterRenderer(EntityRendererProvider.Context context) {
        super(context, new GlacialHunterModel<>(context.bakeLayer(RCModelLayers.GLACIAL_HUNTER)), 0.6F);
    }

    @Override
    public ResourceLocation getTextureLocation(T hunter) {
        return RottenCreatures.resource("textures/entity/glacial_hunter.png");
    }
}