package com.github.teamfusion.rottencreatures.client.level.entities.renderer.lackey;

import com.github.teamfusion.rottencreatures.client.registries.RCModelLayers;
import com.github.teamfusion.rottencreatures.common.level.entities.lackey.ZombieLackey;
import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
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
    protected void setupRotations(ZombieLackey lackey, PoseStack matrices, float bob, float yBodyRot, float partialTick, float scale) {
        super.setupRotations(lackey, matrices, bob, yBodyRot, partialTick, scale);
        float swimAmount = lackey.getSwimAmount(partialTick);
        if (swimAmount > 0.0F) matrices.mulPose(Axis.XP.rotationDegrees(Mth.lerp(swimAmount, lackey.getXRot(), -10.0F - lackey.getXRot())));
    }
}