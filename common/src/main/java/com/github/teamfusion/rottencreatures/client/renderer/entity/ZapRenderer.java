package com.github.teamfusion.rottencreatures.client.renderer.entity;

import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.github.teamfusion.rottencreatures.client.RCModelLayers;
import com.github.teamfusion.rottencreatures.client.model.ZapModel;
import com.github.teamfusion.rottencreatures.common.entities.Zap;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ZapRenderer extends AbstractZombieRenderer<Zap, ZapModel<Zap>> {
    public ZapRenderer(EntityRendererProvider.Context context) {
        super(
            context,
            new ZapModel<>(context.bakeLayer(RCModelLayers.ZAP)),
            new ZapModel<>(context.bakeLayer(RCModelLayers.ZAP_INNER_ARMOR)),
            new ZapModel<>(context.bakeLayer(RCModelLayers.ZAP_OUTER_ARMOR))
        );
    }

    @Override
    public ResourceLocation getTextureLocation(Zap mob) {
        return RottenCreatures.resource("textures/entity/zap.png");
    }
}