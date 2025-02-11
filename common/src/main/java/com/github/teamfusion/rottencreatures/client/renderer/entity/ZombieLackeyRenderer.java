package com.github.teamfusion.rottencreatures.client.renderer.entity;

import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.github.teamfusion.rottencreatures.client.RCModelLayers;
import com.github.teamfusion.rottencreatures.client.renderer.entity.layers.ZombieLackeyOuterLayer;
import com.github.teamfusion.rottencreatures.common.entities.ZombieLackey;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.DrownedModel;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ZombieLackeyRenderer extends AbstractZombieRenderer<ZombieLackey, DrownedModel<ZombieLackey>> {
    public ZombieLackeyRenderer(EntityRendererProvider.Context context) {
        super(
            context,
            new DrownedModel<>(context.bakeLayer(RCModelLayers.ZOMBIE_LACKEY)),
            new DrownedModel<>(context.bakeLayer(RCModelLayers.ZOMBIE_LACKEY_INNER_ARMOR)),
            new DrownedModel<>(context.bakeLayer(RCModelLayers.ZOMBIE_LACKEY_OUTER_ARMOR))
        );
        this.addLayer(new ZombieLackeyOuterLayer<>(this, context.getModelSet()));
    }

    @Override
    public ResourceLocation getTextureLocation(ZombieLackey lackey) {
        return RottenCreatures.resource("textures/entity/zombie_lackey.png");
    }

    @Override
    protected void setupRotations(ZombieLackey lackey, PoseStack matrices, float animationProgress, float bodyYaw, float ticksDelta) {
        super.setupRotations(lackey, matrices, animationProgress, bodyYaw, ticksDelta);
        float swimAmount = lackey.getSwimAmount(ticksDelta);
        if (swimAmount > 0.0F) matrices.mulPose(Vector3f.XP.rotationDegrees(Mth.lerp(swimAmount, lackey.getXRot(), -10.0F - lackey.getXRot())));
    }
}