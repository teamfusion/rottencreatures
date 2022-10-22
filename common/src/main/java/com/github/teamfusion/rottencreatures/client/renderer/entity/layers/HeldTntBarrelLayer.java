package com.github.teamfusion.rottencreatures.client.renderer.entity.layers;

import com.github.teamfusion.rottencreatures.client.model.DeadBeardModel;
import com.github.teamfusion.rottencreatures.common.entities.DeadBeard;
import com.github.teamfusion.rottencreatures.common.registries.RCBlocks;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;

public class HeldTntBarrelLayer<T extends DeadBeard> extends RenderLayer<T, DeadBeardModel<T>> {
    public HeldTntBarrelLayer(RenderLayerParent<T, DeadBeardModel<T>> renderLayerParent) {
        super(renderLayerParent);
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource source, int light, T deadBeard, float angle, float distance, float tickDelta, float animationProgress, float yaw, float pitch) {
        BlockState state = RCBlocks.TNT_BARREL.get().defaultBlockState();
        if (deadBeard.isIgnited()) {
            matrices.pushPose();
            matrices.mulPose(Vector3f.XP.rotationDegrees(180.0F));
            matrices.translate(-0.5F, 0.75F, -0.5F);
            this.renderTNTBarrel(deadBeard, tickDelta, state, matrices, source, light);
            matrices.popPose();
        }
    }

    private void renderTNTBarrel(T deadBeard, float tickDelta, BlockState state, PoseStack matrices, MultiBufferSource source, int light) {
        int fuse = deadBeard.getFuse();
        if (fuse > -1 && (float)fuse - tickDelta + 1.0F < 10.0F) {
            float cooldown = 1.0F - ((float)fuse - tickDelta + 1.0F) / 10.0F;
            cooldown = Mth.clamp(cooldown, 0.0F, 1.0F);
            cooldown *= cooldown;
            cooldown *= cooldown;
            float size = 1.0F + cooldown * 0.3F;
            matrices.scale(size, size, size);
        }

        boolean tickWhiteOverlay = fuse > -1 && fuse / 5 % 2 == 0;
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(state, matrices, source, light, tickWhiteOverlay ? OverlayTexture.pack(OverlayTexture.u(1.0F), 10) : OverlayTexture.NO_OVERLAY);
    }
}