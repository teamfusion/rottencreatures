package com.github.teamfusion.rottencreatures.client.level.blockentities.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

@Environment(EnvType.CLIENT)
public class ZapSkullModel extends SkullModelBase {
    private final ModelPart root;
    private final ModelPart head;

    public ZapSkullModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
    }

    public static LayerDefinition createMobHeadLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition head = root.addOrReplaceChild(
            "head",
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F),
            PartPose.ZERO
        );

        head.addOrReplaceChild(
            "head_r1",
            CubeListBuilder.create()
                .texOffs(51, 8)
                .addBox(0.0F, -3.5F, 0.0F, 2.0F, 8.0F, 0.0F),
            PartPose.offsetAndRotation(4.0F, -4.5F, -1.0F, 0.0F, -0.7854F, 0.0F)
        );
        head.addOrReplaceChild(
            "head_r2",
            CubeListBuilder.create()
                .texOffs(35, 8)
                .addBox(-2.0F, -4.5F, 0.0F, 2.0F, 8.0F, 0.0F),
            PartPose.offsetAndRotation(-4.0F, -3.5F, -1.0F, 0.0F, 0.7854F, 0.0F)
        );
        head.addOrReplaceChild(
            "head_r3",
            CubeListBuilder.create()
                .texOffs(40, 3)
                .addBox(-4.0F, -2.0F, 0.0F, 8.0F, 2.0F, 0.0F),
            PartPose.offsetAndRotation(0.0F, -8.0F, -1.0F, -0.7854F, 0.0F, 0.0F)
        );

        return LayerDefinition.create(mesh, 64, 64);
    }
    
    @Override
    public void setupAnim(float animation, float yRot, float xRot) {
        this.head.yRot = yRot * (float) (Math.PI / 180.0);
        this.head.xRot = xRot * (float) (Math.PI / 180.0);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        this.root.render(matrices, buffer, packedLight, packedOverlay, color);
    }
}