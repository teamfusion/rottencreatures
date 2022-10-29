package com.github.teamfusion.rottencreatures.client.model;

import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.github.teamfusion.rottencreatures.common.entities.TreasureChest;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class TreasureChestModel<T extends TreasureChest> extends EntityModel<T> {
    public final ModelPart bottom;
    public final ModelPart lid;

    public TreasureChestModel(ModelPart root) {
        this.bottom = root.getChild("bottom");
        this.lid = root.getChild("lid");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 19).addBox(-5.0F, -5.0F, -4.0F, 10.0F, 5.0F, 8.0F), PartPose.offset(0.0F, 24.0F, 0.0F));
        root.addOrReplaceChild("lid", CubeListBuilder.create().texOffs(0, 5).addBox(-5.0F, -2.0F, -7.0F, 10.0F, 3.0F, 8.0F, new CubeDeformation(0.01F)).texOffs(1, 7).addBox(-1.0F, 0.0F, -8.0F, 2.0F, 2.0F, 1.0F), PartPose.offset(0.0F, 19.0F, 3.0F));
        return LayerDefinition.create(mesh, 48, 32);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        this.lid.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        this.bottom.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }

    @Override
    public void setupAnim(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        RottenCreatures.LOGGER.info("animProgress:" + animationProgress);
        this.lid.xRot = !entity.isOpen() ? animationProgress / -20.0F : -2.0F;
    }
}