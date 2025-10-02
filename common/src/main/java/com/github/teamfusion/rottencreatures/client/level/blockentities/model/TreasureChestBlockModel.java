package com.github.teamfusion.rottencreatures.client.level.blockentities.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;

public class TreasureChestBlockModel extends Model {
    public static final String ROOT = "root";
    public static final String LID = "lid";

    private final ModelPart root;
    private final ModelPart lid;

    public TreasureChestBlockModel(ModelPart root) {
        super(RenderType::entityTranslucent);
        this.root = root.getChild(ROOT);
        this.lid = this.root.getChild(LID);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition part = mesh.getRoot();

        PartDefinition root = part.addOrReplaceChild(
            ROOT,
            CubeListBuilder.create()
                .texOffs(0, 19)
                .addBox(-5.0F, -5.0F, -4.0F, 10.0F, 5.0F, 8.0F),
            PartPose.offset(0.0F, 0.0F, 0.0F)
        );

        root.addOrReplaceChild(
            LID,
            CubeListBuilder.create()
                .texOffs(0, 5)
                .addBox("base", -5.0F, -3.0F, -8.0F, 10.0F, 3.0F, 8.0F, new CubeDeformation(0.025F))
                .texOffs(1, 7)
                .addBox("lock", -1.0F, -1.0F, -9.0F, 2.0F, 2.0F, 1.0F),
            PartPose.offset(0.0F, -4.0F, 4.0F)
        );

        return LayerDefinition.create(mesh, 48, 32);
    }

    public void setupAnim(float animationProgress, float yRot, float xRot) {
        this.root.yRot = yRot * (Mth.PI / 180.0F);
        this.root.xRot = xRot * (Mth.PI / 180.0F);

        // Animation for lid opening/closing
        float maxAngle = -80.0F * (Mth.PI / 180.0F); // Max open angle in radians

        // Use animation progress directly for smooth transition
        this.lid.xRot = maxAngle * animationProgress;
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertices, int packedLight, int packedOverlay, int color) {
        this.root.render(matrices, vertices, packedLight, packedOverlay, color);
    }
}