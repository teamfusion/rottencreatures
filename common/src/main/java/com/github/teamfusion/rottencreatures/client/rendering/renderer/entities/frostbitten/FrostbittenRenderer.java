package com.github.teamfusion.rottencreatures.client.rendering.renderer.entities.frostbitten;

import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import com.github.teamfusion.rottencreatures.client.registries.RCModelLayers;
import com.github.teamfusion.rottencreatures.client.rendering.model.entities.FrostbittenModel;
import com.github.teamfusion.rottencreatures.common.level.entities.living.frostbitten.Frostbitten;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class FrostbittenRenderer extends AbstractZombieRenderer<Frostbitten, FrostbittenModel<Frostbitten>> {
    public FrostbittenRenderer(EntityRendererProvider.Context context) {
        super(
            context,
            new FrostbittenModel<>(context.bakeLayer(RCModelLayers.FROSTBITTEN)),
            new FrostbittenModel<>(context.bakeLayer(RCModelLayers.FROSTBITTEN_INNER_ARMOR)),
            new FrostbittenModel<>(context.bakeLayer(RCModelLayers.FROSTBITTEN_OUTER_ARMOR))
        );
    }

    @Override
    public ResourceLocation getTextureLocation(Frostbitten frostbitten) {
        return RottenCreatures.resource("textures/entity/frostbitten.png");
    }
}