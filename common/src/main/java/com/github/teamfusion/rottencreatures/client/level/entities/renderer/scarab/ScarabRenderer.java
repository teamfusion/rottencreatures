package com.github.teamfusion.rottencreatures.client.level.entities.renderer.scarab;

import com.github.teamfusion.rottencreatures.client.level.entities.model.ScarabModel;
import com.github.teamfusion.rottencreatures.client.registries.RCModelLayers;
import com.github.teamfusion.rottencreatures.common.level.entities.scarab.Scarab;
import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ScarabRenderer<T extends Scarab> extends MobRenderer<T, ScarabModel<T>> {
    public ScarabRenderer(EntityRendererProvider.Context context) {
        super(context, new ScarabModel<>(context.bakeLayer(RCModelLayers.SCARAB)), 0.3F);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        if (entity.isEmbellished()) {
            return RottenCreatures.resource("textures/entity/scarab/scarab_embellished_beetle.png");
        }

        return RottenCreatures.resource("textures/entity/scarab/scarab_death_beetle.png");
    }
}