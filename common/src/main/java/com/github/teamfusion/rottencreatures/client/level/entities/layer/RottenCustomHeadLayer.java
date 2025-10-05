package com.github.teamfusion.rottencreatures.client.level.entities.layer;

import com.github.teamfusion.rottencreatures.client.level.entities.model.RottenModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.WalkAnimationState;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.SkullBlock;

import java.util.Map;

@Environment(EnvType.CLIENT)
public class RottenCustomHeadLayer<T extends LivingEntity, M extends RottenModel<T> & HeadedModel> extends RenderLayer<T, M> {
	private final float scaleX;
	private final float scaleY;
	private final float scaleZ;
	private final Map<SkullBlock.Type, SkullModelBase> skullModels;
	private final ItemInHandRenderer itemInHandRenderer;

	public RottenCustomHeadLayer(RenderLayerParent<T, M> renderer, EntityModelSet modelSet, ItemInHandRenderer itemInHandRenderer) {
		this(renderer, modelSet, 1.0F, 1.0F, 1.0F, itemInHandRenderer);
	}

	public RottenCustomHeadLayer(
		RenderLayerParent<T, M> renderer, EntityModelSet modelSet, float scaleX, float scaleY, float scaleZ, ItemInHandRenderer itemInHandRenderer
	) {
		super(renderer);
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.scaleZ = scaleZ;
		this.skullModels = SkullBlockRenderer.createSkullRenderers(modelSet);
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
		ItemStack itemStack = livingEntity.getItemBySlot(EquipmentSlot.HEAD);
		if (!itemStack.isEmpty()) {
			Item item = itemStack.getItem();
			poseStack.pushPose();
			poseStack.scale(this.scaleX, this.scaleY, this.scaleZ);
			boolean bl = livingEntity instanceof Villager || livingEntity instanceof ZombieVillager;
			if (livingEntity.isBaby() && !(livingEntity instanceof Villager)) {
				float f = 2.0F;
				float g = 1.4F;
				poseStack.translate(0.0F, 0.03125F, 0.0F);
				poseStack.scale(0.7F, 0.7F, 0.7F);
				poseStack.translate(0.0F, 1.0F, 0.0F);
			}

			this.getParentModel().root.translateAndRotate(poseStack);
			this.getParentModel().body.translateAndRotate(poseStack);
			this.getParentModel().head.translateAndRotate(poseStack);
			if (item instanceof BlockItem && ((BlockItem)item).getBlock() instanceof AbstractSkullBlock) {
				poseStack.scale(1.1875F, -1.1875F, -1.1875F);
				if (bl) {
					poseStack.translate(0.0F, 0.0625F, 0.0F);
				}

				ResolvableProfile resolvableProfile = itemStack.get(DataComponents.PROFILE);
				poseStack.translate(-0.5, 0.0, -0.5);
				SkullBlock.Type type = ((AbstractSkullBlock)((BlockItem)item).getBlock()).getType();
				SkullModelBase skullModelBase = this.skullModels.get(type);
				RenderType renderType = SkullBlockRenderer.getRenderType(type, resolvableProfile);
				WalkAnimationState walkAnimationState;
				if (livingEntity.getVehicle() instanceof LivingEntity livingEntity2) {
					walkAnimationState = livingEntity2.walkAnimation;
				} else {
					walkAnimationState = livingEntity.walkAnimation;
				}

				float h = walkAnimationState.position(partialTicks);
				SkullBlockRenderer.renderSkull(null, 180.0F, h, poseStack, buffer, packedLight, skullModelBase, renderType);
			} else if (!(item instanceof ArmorItem armorItem) || armorItem.getEquipmentSlot() != EquipmentSlot.HEAD) {
				translateToHead(poseStack, bl);
				this.itemInHandRenderer.renderItem(livingEntity, itemStack, ItemDisplayContext.HEAD, false, poseStack, buffer, packedLight);
			}

			poseStack.popPose();
		}
	}

	public static void translateToHead(PoseStack poseStack, boolean isVillager) {
		poseStack.translate(0.0F, -0.25F, 0.0F);
		poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
		poseStack.scale(0.625F, -0.625F, -0.625F);
		if (isVillager) {
			poseStack.translate(0.0F, 0.1875F, 0.0F);
		}
	}
}
