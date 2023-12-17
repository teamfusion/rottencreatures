package com.github.teamfusion.rottencreatures.client.renderer.entity;

import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import com.github.teamfusion.rottencreatures.client.model.LayerBuilder;
import com.github.teamfusion.rottencreatures.client.model.ZapModel;
import com.github.teamfusion.rottencreatures.common.entities.Zap;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ZapRenderer extends AbstractZombieRenderer<Zap, ZapModel<Zap>> {
    public static final LayerBuilder LAYER = LayerBuilder.of("zap");

    public ZapRenderer(EntityRendererProvider.Context context) {
        super(context, new ZapModel<>(context.bakeLayer(LAYER.getMain())), new ZapModel<>(context.bakeLayer(LAYER.getInner())), new ZapModel<>(context.bakeLayer(LAYER.getOuter())));
    }

    @Override
    public ResourceLocation getTextureLocation(Zap mob) {
        return new ResourceLocation(RottenCreatures.MOD_ID, "textures/entity/zap.png");
    }
}