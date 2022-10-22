package com.github.teamfusion.rottencreatures.client.renderer.entity;

import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.github.teamfusion.rottencreatures.client.model.LayerBuilder;
import com.github.teamfusion.rottencreatures.client.renderer.entity.layers.ZombieLackeyOuterLayer;
import com.github.teamfusion.rottencreatures.common.entities.ZombieLackey;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.DrownedModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ZombieLackeyRenderer extends AbstractZombieRenderer<ZombieLackey, DrownedModel<ZombieLackey>> {
    public static final LayerBuilder LAYER = LayerBuilder.of("zombie_lackey");
    public static final ModelLayerLocation OUTER_LAYER = LAYER.create("outer_layer");

    public ZombieLackeyRenderer(EntityRendererProvider.Context context) {
        super(context, new DrownedModel<>(context.bakeLayer(LAYER.getMain())), new DrownedModel<>(context.bakeLayer(LAYER.getInner())), new DrownedModel<>(context.bakeLayer(LAYER.getOuter())));
        this.addLayer(new ZombieLackeyOuterLayer<>(this, context.getModelSet()));
    }

    @Override
    public ResourceLocation getTextureLocation(ZombieLackey lackey) {
        return new ResourceLocation(RottenCreatures.MOD_ID, "textures/entity/zombie_lackey.png");
    }

    @Override
    protected void setupRotations(ZombieLackey lackey, PoseStack matrices, float animationProgress, float bodyYaw, float ticksDelta) {
        super.setupRotations(lackey, matrices, animationProgress, bodyYaw, ticksDelta);
        float swimAmount = lackey.getSwimAmount(ticksDelta);
        if (swimAmount > 0.0F) matrices.mulPose(Vector3f.XP.rotationDegrees(Mth.lerp(swimAmount, lackey.getXRot(), -10.0F - lackey.getXRot())));
    }
}