package com.github.teamfusion.rottencreatures.client.renderer.entity.layers;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.ResourceLocationException;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class MummyArmorLayer<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {
    private static final Map<String, ResourceLocation> ARMOR_LOCATION_CACHE = Maps.newHashMap();
    private final A innerModel;
    private final A outerModel;

    public MummyArmorLayer(RenderLayerParent<T, M> parent, A innerModel, A outerModel) {
        super(parent);
        this.innerModel = innerModel;
        this.outerModel = outerModel;
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource source, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        this.renderArmorPiece(matrices, source, entity, EquipmentSlot.CHEST, light, this.getArmorModel(EquipmentSlot.CHEST));
        this.renderArmorPiece(matrices, source, entity, EquipmentSlot.LEGS, light, this.getArmorModel(EquipmentSlot.LEGS));
        this.renderArmorPiece(matrices, source, entity, EquipmentSlot.FEET, light, this.getArmorModel(EquipmentSlot.FEET));
        this.renderArmorPiece(matrices, source, entity, EquipmentSlot.HEAD, light, this.getArmorModel(EquipmentSlot.HEAD));
    }

    private void renderArmorPiece(PoseStack matrices, MultiBufferSource source, T entity, EquipmentSlot slot, int light, A model) {
        ItemStack stack = entity.getItemBySlot(slot);
        if (stack.getItem() instanceof ArmorItem item) {
            if (item.getSlot() == slot) {
                this.getParentModel().copyPropertiesTo(model);
                if (model == this.innerModel) {
                    model.leftLeg.y = 10;
                    model.rightLeg.y = 10;
                    model.body.y = -2;
                } else {
                    model.leftLeg.y = 12;
                    model.rightLeg.y = 12;
                }
                this.setPartVisibility(model, slot);
                boolean useInnerModel = this.usesInnerModel(slot);
                boolean hasFoil = stack.hasFoil();
                if (item instanceof DyeableArmorItem dye) {
                    int color = dye.getColor(stack);
                    float red = (float)(color >> 16 & 0xFF) / 255.0F;
                    float green = (float)(color >> 8 & 0xFF) / 255.0F;
                    float blue = (float)(color & 0xFF) / 255.0F;
                    this.renderModel(matrices, source, light, item, hasFoil, model, useInnerModel, red, green, blue, null);
                    this.renderModel(matrices, source, light, item, hasFoil, model, useInnerModel, 1.0F, 1.0F, 1.0F, "overlay");
                } else {
                    this.renderModel(matrices, source, light, item, hasFoil, model, useInnerModel, 1.0F, 1.0F, 1.0F, null);
                }
            }
        }
    }

    protected void setPartVisibility(A model, EquipmentSlot slot) {
        model.setAllVisible(false);
        switch (slot) {
            case HEAD -> {
                model.head.visible = true;
                model.hat.visible = true;
            }
            case CHEST -> {
                model.body.visible = true;
                model.rightArm.visible = true;
                model.leftArm.visible = true;
            }
            case LEGS -> {
                model.body.visible = true;
                model.rightLeg.visible = true;
                model.leftLeg.visible = true;
            }
            case FEET -> {
                model.rightLeg.visible = true;
                model.leftLeg.visible = true;
            }
        }

    }

    private void renderModel(PoseStack matrices, MultiBufferSource source, int light, ArmorItem item, boolean hasFoil, A model, boolean useInnerModel, float red, float green, float blue, @Nullable String overlay) {
        ResourceLocation armor_location = this.getArmorLocation(item, useInnerModel, overlay);
        if (armor_location != null) {
            VertexConsumer vertices = ItemRenderer.getArmorFoilBuffer(source, RenderType.armorCutoutNoCull(armor_location), false, hasFoil);
            model.renderToBuffer(matrices, vertices, light, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
        }
    }

    private A getArmorModel(EquipmentSlot slot) {
        return this.usesInnerModel(slot) ? this.innerModel : this.outerModel;
    }

    private boolean usesInnerModel(EquipmentSlot slot) {
        return slot == EquipmentSlot.LEGS;
    }

    @Nullable
    private ResourceLocation getArmorLocation(ArmorItem armor, boolean legs, @Nullable String overlay) {
        String location = "textures/models/armor/" + armor.getMaterial().getName() + "_layer_" + (legs ? 2 : 1) + (overlay == null ? "" : "_" + overlay) + ".png";
        try {
            return ARMOR_LOCATION_CACHE.computeIfAbsent(location, ResourceLocation::new);
        } catch (ResourceLocationException e) {
            return null;
        }
    }
}