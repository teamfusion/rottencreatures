package com.github.teamfusion.rottencreatures.client.model;

import com.github.teamfusion.rottencreatures.common.entities.Immortal;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class ImmortalOverlayModel<T extends Immortal> extends HumanoidModel<T> {
    public ImmortalOverlayModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition root = mesh.getRoot();
        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(20, 17).addBox(-4.5F, 0.0F, -2.5F, 9.0F, 10.0F, 5.0F, new CubeDeformation(0.025F)).texOffs(46, 0).addBox(-4.5F, 11.0F, 2.5F, 9.0F, 4.0F, 0.0F, new CubeDeformation(0.025F)), PartPose.ZERO);
        body.addOrReplaceChild("right_neck", CubeListBuilder.create().texOffs(48, 28).addBox(-4.0F, -4.0F, 0.0F, 8.0F, 4.0F, 0.0F), PartPose.offsetAndRotation(-4.0F, 0.0F, 0.0F, 0.0F, 1.5708F, -0.2618F));
        body.addOrReplaceChild("left_neck", CubeListBuilder.create().texOffs(48, 24).addBox(-4.0F, -4.0F, 0.0F, 8.0F, 4.0F, 0.0F), PartPose.offsetAndRotation(4.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.2618F));
        root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, 0.0F, -2.5F, 5.0F, 11.0F, 5.0F), PartPose.offsetAndRotation(-1.9F, 12.0F, 0.0F, 0.0F, 0.0F, 0.2618F));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.5F, 5.0F, 11.0F, 5.0F), PartPose.offsetAndRotation(1.9F, 12.0F, 0.0F, 0.0F, 0.0F, -0.2618F));
        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        super.renderToBuffer(matrices, vertices, light, overlay, red, green, blue, alpha);
        this.head.visible = false;
        this.rightArm.visible = false;
        this.leftArm.visible = false;
    }
}