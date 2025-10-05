package com.github.teamfusion.rottencreatures.client.level.entities.layer;

import com.github.teamfusion.rottencreatures.client.level.entities.model.RottenModel;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Map;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.component.DyedItemColor;

@Environment(EnvType.CLIENT)
public class RottenArmorLayer<T extends LivingEntity, M extends RottenModel<T>, A extends RottenModel<T>> extends RenderLayer<T, M> {
	private static final Map<String, ResourceLocation> ARMOR_LOCATION_CACHE = Maps.newHashMap();
	private final A innerModel;
	private final A outerModel;
	private final TextureAtlas armorTrimAtlas;

	public RottenArmorLayer(RenderLayerParent<T, M> renderer, A innerModel, A outerModel, ModelManager modelManager) {
		super(renderer);
		this.innerModel = innerModel;
		this.outerModel = outerModel;
		this.armorTrimAtlas = modelManager.getAtlas(Sheets.ARMOR_TRIMS_SHEET);
	}

	@Override
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
		this.renderArmorPiece(poseStack, buffer, livingEntity, EquipmentSlot.CHEST, packedLight, this.getArmorModel(EquipmentSlot.CHEST));
		this.renderArmorPiece(poseStack, buffer, livingEntity, EquipmentSlot.LEGS, packedLight, this.getArmorModel(EquipmentSlot.LEGS));
		this.renderArmorPiece(poseStack, buffer, livingEntity, EquipmentSlot.FEET, packedLight, this.getArmorModel(EquipmentSlot.FEET));
		this.renderArmorPiece(poseStack, buffer, livingEntity, EquipmentSlot.HEAD, packedLight, this.getArmorModel(EquipmentSlot.HEAD));
	}

	private void renderArmorPiece(PoseStack poseStack, MultiBufferSource bufferSource, T livingEntity, EquipmentSlot slot, int packedLight, A model) {
		ItemStack itemStack = livingEntity.getItemBySlot(slot);
		if (itemStack.getItem() instanceof ArmorItem armorItem) {
			if (armorItem.getEquipmentSlot() == slot) {
				this.getParentModel().copyPropertiesTo(model);
				this.setPartVisibility(model, slot);
				boolean bl = this.usesInnerModel(slot);
				ArmorMaterial armorMaterial = armorItem.getMaterial().value();
				int i = itemStack.is(ItemTags.DYEABLE) ? FastColor.ARGB32.opaque(DyedItemColor.getOrDefault(itemStack, -6265536)) : -1;

				for (ArmorMaterial.Layer layer : armorMaterial.layers()) {
					int j = layer.dyeable() ? i : -1;
					this.renderModel(poseStack, bufferSource, packedLight, model, j, layer.texture(bl));
				}

				ArmorTrim armorTrim = itemStack.get(DataComponents.TRIM);
				if (armorTrim != null) {
					this.renderTrim(armorItem.getMaterial(), poseStack, bufferSource, packedLight, armorTrim, model, bl);
				}

				if (itemStack.hasFoil()) {
					this.renderGlint(poseStack, bufferSource, packedLight, model);
				}
			}
		}
	}

	protected void setPartVisibility(A model, EquipmentSlot slot) {
		model.setAllVisible(false);
		switch (slot) {
			case HEAD:
				model.head.visible = true;
			case CHEST:
				model.body.visible = true;
				model.right_arm.visible = true;
				model.left_arm.visible = true;
				break;
			case LEGS:
				model.body.visible = true;
				model.right_leg.visible = true;
				model.left_leg.visible = true;
				break;
			case FEET:
				model.right_leg.visible = true;
				model.left_leg.visible = true;
		}
	}

	private void renderModel(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, A model, int dyeColor, ResourceLocation textureLocation) {
		VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.armorCutoutNoCull(textureLocation));
		model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, dyeColor);
	}

	private void renderTrim(
		Holder<ArmorMaterial> armorMaterial, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, ArmorTrim trim, A model, boolean innerTexture
	) {
		TextureAtlasSprite textureAtlasSprite = this.armorTrimAtlas.getSprite(innerTexture ? trim.innerTexture(armorMaterial) : trim.outerTexture(armorMaterial));
		VertexConsumer vertexConsumer = textureAtlasSprite.wrap(bufferSource.getBuffer(Sheets.armorTrimsSheet(trim.pattern().value().decal())));
		model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
	}

	private void renderGlint(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, A model) {
		model.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.armorEntityGlint()), packedLight, OverlayTexture.NO_OVERLAY);
	}

	private A getArmorModel(EquipmentSlot slot) {
		return this.usesInnerModel(slot) ? this.innerModel : this.outerModel;
	}

	private boolean usesInnerModel(EquipmentSlot slot) {
		return slot == EquipmentSlot.LEGS;
	}
}
