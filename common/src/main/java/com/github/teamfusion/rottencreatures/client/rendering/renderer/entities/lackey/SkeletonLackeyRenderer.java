package com.github.teamfusion.rottencreatures.client.rendering.renderer.entities.lackey;

import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import com.github.teamfusion.rottencreatures.client.registries.RCModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractSkeleton;

public class SkeletonLackeyRenderer extends SkeletonRenderer {
    public SkeletonLackeyRenderer(EntityRendererProvider.Context context) {
        super(context, RCModelLayers.SKELETON_LACKEY, RCModelLayers.SKELETON_LACKEY_INNER_ARMOR, RCModelLayers.SKELETON_LACKEY_OUTER_ARMOR);
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractSkeleton skeleton) {
        return RottenCreatures.resource("textures/entity/skeleton_lackey.png");
    }
}