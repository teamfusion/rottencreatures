package com.github.teamfusion.rottencreatures.client.level.entities.layer;

import com.github.teamfusion.rottencreatures.client.level.entities.model.RottenModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ElytraModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

@Environment(EnvType.CLIENT)
public class RottenElytraLayer<T extends LivingEntity, M extends RottenModel<T>> extends RenderLayer<T, M> {
	private static final ResourceLocation WINGS_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/elytra.png");
	private final ElytraModel<T> elytraModel;

	public RottenElytraLayer(RenderLayerParent<T, M> renderer, EntityModelSet modelSet) {
		super(renderer);
		this.elytraModel = new ElytraModel<>(modelSet.bakeLayer(ModelLayers.ELYTRA));
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
		ItemStack itemStack = livingEntity.getItemBySlot(EquipmentSlot.CHEST);
		if (itemStack.is(Items.ELYTRA)) {
			ResourceLocation resourceLocation;
			if (livingEntity instanceof AbstractClientPlayer abstractClientPlayer) {
				PlayerSkin playerSkin = abstractClientPlayer.getSkin();
				if (playerSkin.elytraTexture() != null) {
					resourceLocation = playerSkin.elytraTexture();
				} else if (playerSkin.capeTexture() != null && abstractClientPlayer.isModelPartShown(PlayerModelPart.CAPE)) {
					resourceLocation = playerSkin.capeTexture();
				} else {
					resourceLocation = WINGS_LOCATION;
				}
			} else {
				resourceLocation = WINGS_LOCATION;
			}

			poseStack.pushPose();
			this.getParentModel().root.translateAndRotate(poseStack);
			this.getParentModel().body.translateAndRotate(poseStack);
			poseStack.translate(0.0F, -0.65F, 0.0F);
			this.getParentModel().copyPropertiesTo(this.elytraModel);
			this.elytraModel.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(resourceLocation), itemStack.hasFoil());
			this.elytraModel.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
			poseStack.popPose();
		}
	}
}
