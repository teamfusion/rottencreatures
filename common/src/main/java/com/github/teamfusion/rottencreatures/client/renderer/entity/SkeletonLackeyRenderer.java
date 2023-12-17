package com.github.teamfusion.rottencreatures.client.renderer.entity;

import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import com.github.teamfusion.rottencreatures.client.model.LayerBuilder;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractSkeleton;

public class SkeletonLackeyRenderer extends SkeletonRenderer {
    public static final LayerBuilder LAYER = LayerBuilder.of("skeleton_lackey");

    public SkeletonLackeyRenderer(EntityRendererProvider.Context context) {
        super(context, LAYER.getMain(), LAYER.getInner(), LAYER.getOuter());
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractSkeleton skeleton) {
        return new ResourceLocation(RottenCreatures.MOD_ID, "textures/entity/skeleton_lackey.png");
    }
}