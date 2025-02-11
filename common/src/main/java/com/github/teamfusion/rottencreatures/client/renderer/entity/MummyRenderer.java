package com.github.teamfusion.rottencreatures.client.renderer.entity;

import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.github.teamfusion.rottencreatures.client.RCModelLayers;
import com.github.teamfusion.rottencreatures.client.model.MummyModel;
import com.github.teamfusion.rottencreatures.common.entities.Mummy;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class MummyRenderer<T extends Mummy> extends HumanoidMobRenderer<T, MummyModel<T>> {
    public MummyRenderer(EntityRendererProvider.Context context) {
        super(context, new MummyModel<>(context.bakeLayer(RCModelLayers.MUMMY)), 0.5F);
        this.addLayer(new HumanoidArmorLayer<>(this, new MummyModel<>(context.bakeLayer(RCModelLayers.MUMMY_INNER_ARMOR)), new MummyModel<>(context.bakeLayer(RCModelLayers.MUMMY_OUTER_ARMOR))));
    }

    @Override
    public ResourceLocation getTextureLocation(T mummy) {
        String type = mummy.isAncient() ? "ancient_" : "";
        return RottenCreatures.resource("textures/entity/mummy/" + type + "mummy.png");
    }
}
