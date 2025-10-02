package com.github.teamfusion.rottencreatures.client.level.blockentities.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

@Environment(EnvType.CLIENT)
public class MummySkullModel extends SkullModelBase {
    private final ModelPart root;
    private final ModelPart head;

    public MummySkullModel(ModelPart root) {
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

        return LayerDefinition.create(mesh, 64, 70);
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