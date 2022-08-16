package com.github.teamfusion.rottencreatures.client.renderer.entity;

import com.github.teamfusion.rottencreatures.common.entities.PrimedTntBarrel;
import com.github.teamfusion.rottencreatures.common.registries.RCBlocks;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.TntMinecartRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class TntBarrelRenderer extends EntityRenderer<PrimedTntBarrel> {
    public TntBarrelRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.5F;
    }

    @Override
    public void render(PrimedTntBarrel tnt, float yaw, float angle, PoseStack stack, MultiBufferSource buffer, int light) {
        stack.pushPose();
        stack.translate(0.0D, 0.5D, 0.0D);
        int cooldown = tnt.getFuse();
        if ((float)cooldown - angle + 1.0F < 10.0f) {
            float delta = 1.0F - ((float)cooldown - angle + 1.0F) / 10.0F;
            delta = Mth.clamp(delta, 0.0F, 1.0F);
            delta *= delta;
            delta *= delta;
            float scale = 1.0F + delta * 0.3F;
            stack.scale(scale, scale, scale);
        }
        stack.mulPose(Vector3f.YP.rotationDegrees(-90.0F));
        stack.translate(-0.5D, -0.5D, 0.5D);
        stack.mulPose(Vector3f.YP.rotationDegrees(90.0F));
        TntMinecartRenderer.renderWhiteSolidBlock(RCBlocks.TNT_BARREL.get().defaultBlockState(), stack, buffer, light, cooldown / 5 % 2 == 0);
        stack.popPose();
        super.render(tnt, yaw, angle, stack, buffer, light);
    }

    @Override
    public ResourceLocation getTextureLocation(PrimedTntBarrel entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}