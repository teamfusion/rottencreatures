package com.github.teamfusion.rottencreatures.client.level.entities.renderer.lackey;

import com.github.teamfusion.rottencreatures.client.registries.RCModelLayers;
import com.github.teamfusion.rottencreatures.common.level.entities.lackey.SkeletonLackey;
import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.resources.ResourceLocation;

public class SkeletonLackeyRenderer extends SkeletonRenderer<SkeletonLackey> {
    public SkeletonLackeyRenderer(EntityRendererProvider.Context context) {
        super(context, RCModelLayers.SKELETON_LACKEY, RCModelLayers.SKELETON_LACKEY_INNER_ARMOR, RCModelLayers.SKELETON_LACKEY_OUTER_ARMOR);
    }

    @Override
    public ResourceLocation getTextureLocation(SkeletonLackey entity) {
        return RottenCreatures.resource("textures/entity/skeleton_lackey.png");
    }
}