package com.github.teamfusion.rottencreatures.client.level.entities.renderer.zap;

import com.github.teamfusion.rottencreatures.client.level.entities.model.ZapModel;
import com.github.teamfusion.rottencreatures.client.registries.RCModelLayers;
import com.github.teamfusion.rottencreatures.common.level.entities.zap.Zap;
import com.github.teamfusion.rottencreatures.core.RottenCreatures;
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