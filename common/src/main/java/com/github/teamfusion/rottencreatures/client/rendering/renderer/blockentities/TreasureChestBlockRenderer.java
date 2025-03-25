package com.github.teamfusion.rottencreatures.client.rendering.renderer.blockentities;

import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import com.github.teamfusion.rottencreatures.client.registries.RCModelLayers;
import com.github.teamfusion.rottencreatures.client.rendering.model.blockentities.TreasureChestBlockModel;
import com.github.teamfusion.rottencreatures.common.level.blockentities.TreasureChestBlockEntity;
import com.github.teamfusion.rottencreatures.common.level.blocks.TreasureChestBlock;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;
import java.util.WeakHashMap;

@Environment(EnvType.CLIENT)
public class TreasureChestBlockRenderer implements BlockEntityRenderer<TreasureChestBlockEntity> {
    private final TreasureChestBlockModel model;
    private static final Map<BlockPos, ChestAnimationState> ANIMATION_STATES = new WeakHashMap<>();

    public TreasureChestBlockRenderer(BlockEntityRendererProvider.Context context) {
        this.model = new TreasureChestBlockModel(context.bakeLayer(RCModelLayers.TREASURE_CHEST));
    }

    @Override
    public void render(TreasureChestBlockEntity blockEntity, float partialTick, PoseStack matrices, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        BlockState state = blockEntity.getBlockState();
        float yRot = 22.5F * state.getValue(TreasureChestBlock.ROTATION);
        boolean isOpen = state.getValue(TreasureChestBlock.OPEN);
        BlockPos pos = blockEntity.getBlockPos();

        ChestAnimationState animState = ANIMATION_STATES.computeIfAbsent(pos, p -> new ChestAnimationState(isOpen ? 1.0F : 0.0F));

        long currentTime = System.currentTimeMillis();
        float deltaTime = Math.min(0.1F, (currentTime - animState.lastUpdateTime) / 100.0F);
        animState.lastUpdateTime = currentTime;

        animState.prevOpenness = animState.openness;

        if (isOpen && animState.openness < 1.0F) {
            animState.openness = Math.min(animState.openness + deltaTime * 0.5F, 1.0F);
        } else if (!isOpen && animState.openness > 0.0F) {
            animState.openness = Math.max(animState.openness - deltaTime * 0.5F, 0.0F);
        }

        float animationProgress = Mth.lerp(partialTick, animState.prevOpenness, animState.openness);

        matrices.pushPose();
        matrices.translate(0.5, 0.0, 0.5);
        matrices.scale(-1.0F, -1.0F, 1.0F);
        VertexConsumer vertices = buffer.getBuffer(RenderType.entityCutoutNoCullZOffset(this.getTextureLocation()));
        this.model.setupAnim(animationProgress, yRot, 0.0F);
        this.model.renderToBuffer(matrices, vertices, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrices.popPose();
    }

    public ResourceLocation getTextureLocation() {
        return RottenCreatures.resource("textures/block/treasure_chest.png");
    }

    private static class ChestAnimationState {
        public float openness;
        public float prevOpenness;
        public long lastUpdateTime;

        public ChestAnimationState(float initialOpenness) {
            this.openness = initialOpenness;
            this.prevOpenness = initialOpenness;
            this.lastUpdateTime = System.currentTimeMillis();
        }
    }
}