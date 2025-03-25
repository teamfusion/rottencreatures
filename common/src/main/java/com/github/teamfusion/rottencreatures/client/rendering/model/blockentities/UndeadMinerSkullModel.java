package com.github.teamfusion.rottencreatures.client.rendering.model.blockentities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

@Environment(EnvType.CLIENT)
public class UndeadMinerSkullModel extends SkullModelBase {
    private final ModelPart root;
    private final ModelPart head;

    public UndeadMinerSkullModel(ModelPart root) {
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
            "overlay",
            CubeListBuilder.create()
                .texOffs(32, 0)
                .addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.25F)),
            PartPose.ZERO
        );
        head.addOrReplaceChild(
            "hat",
            CubeListBuilder.create()
                .texOffs(64, 0)
                .addBox(-4.5F, -8.5F, -4.5F, 9.0F, 4.0F, 9.0F)
                .texOffs(64, 17)
                .addBox(-5.0F, -9.0F, -5.0F, 10.0F, 5.0F, 10.0F)
                .texOffs(102, 1)
                .addBox(-1.5F, -7.0F, -6.0F, 3.0F, 3.0F, 2.0F),
            PartPose.offset(0.0F, -0.5F, 0.0F)
        );

        return LayerDefinition.create(mesh, 112, 64);
    }
    
    @Override
    public void setupAnim(float animation, float yRot, float xRot) {
        this.head.yRot = yRot * (float) (Math.PI / 180.0);
        this.head.xRot = xRot * (float) (Math.PI / 180.0);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.root.render(matrices, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}