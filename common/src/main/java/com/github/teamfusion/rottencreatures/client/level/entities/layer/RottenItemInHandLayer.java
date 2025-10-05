package com.github.teamfusion.rottencreatures.client.level.entities.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public class RottenItemInHandLayer<T extends LivingEntity, M extends EntityModel<T> & ArmedModel> extends RenderLayer<T, M> {
	private final ItemInHandRenderer itemInHandRenderer;

	public RottenItemInHandLayer(RenderLayerParent<T, M> renderer, ItemInHandRenderer itemInHandRenderer) {
		super(renderer);
		this.itemInHandRenderer = itemInHandRenderer;
	}

	public void render(
		PoseStack poseStack,
		MultiBufferSource buffer,
		int packedLight,
		T livingEntity,
		float limbSwing,
		float limbSwingAmount,
		float partialTicks,
		float ageInTicks,
		float netHeadYaw,
		float headPitch
	) {
		boolean bl = livingEntity.getMainArm() == HumanoidArm.RIGHT;
		ItemStack itemStack = bl ? livingEntity.getOffhandItem() : livingEntity.getMainHandItem();
		ItemStack itemStack2 = bl ? livingEntity.getMainHandItem() : livingEntity.getOffhandItem();
		if (!itemStack.isEmpty() || !itemStack2.isEmpty()) {
			poseStack.pushPose();
			if (this.getParentModel().young) {
				float f = 0.5F;
				poseStack.translate(0.0F, 0.75F, 0.0F);
				poseStack.scale(0.5F, 0.5F, 0.5F);
			}

			this.renderArmWithItem(livingEntity, itemStack2, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, HumanoidArm.RIGHT, poseStack, buffer, packedLight);
			this.renderArmWithItem(livingEntity, itemStack, ItemDisplayContext.THIRD_PERSON_LEFT_HAND, HumanoidArm.LEFT, poseStack, buffer, packedLight);
			poseStack.popPose();
		}
	}

	protected void renderArmWithItem(
		LivingEntity livingEntity,
		ItemStack itemStack,
		ItemDisplayContext displayContext,
		HumanoidArm arm,
		PoseStack poseStack,
		MultiBufferSource buffer,
		int packedLight
	) {
		if (!itemStack.isEmpty()) {
			poseStack.pushPose();
			this.getParentModel().translateToHand(arm, poseStack);
			poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
			poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
			boolean bl = arm == HumanoidArm.LEFT;
			poseStack.translate((float)(bl ? -1 : 1) / 16.0F, 0.125F, -0.625F);
//			poseStack.translate((float)(bl ? -1 : 1) / 16.0F, 0.125F, -0.625F);
			this.itemInHandRenderer.renderItem(livingEntity, itemStack, displayContext, bl, poseStack, buffer, packedLight);
			poseStack.popPose();
		}
	}
}
