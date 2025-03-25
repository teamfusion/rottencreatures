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
public class DeadBeardSkullModel extends SkullModelBase {
    private final ModelPart root;
    private final ModelPart head;

    public DeadBeardSkullModel(ModelPart root) {
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
                .addBox(-4.0F, -9.0F, -4.0F, 8.0F, 9.0F, 8.0F)
                .texOffs(56, 16)
                .addBox(-3.5F, -8.5F, -3.5F, 7.0F, 8.0F, 7.0F), 
            PartPose.ZERO
        );

        head.addOrReplaceChild(
            "hat",
            CubeListBuilder.create()
                .texOffs(32, 0)
                .addBox(-3.0F, -13.0F, -4.5F, 6.0F, 7.0F, 9.0F)
                .texOffs(62, 3).mirror()
                .addBox(-6.0F, -10.0F, -4.5F, 3.0F, 4.0F, 9.0F).mirror(false)
                .texOffs(62, 3).mirror()
                .addBox(3.0F, -10.0F, -4.5F, 3.0F, 4.0F, 9.0F).mirror(false),
            PartPose.ZERO
        );

        head.addOrReplaceChild(
            "beard",
            CubeListBuilder.create()
                .texOffs(2, 3).mirror()
                .addBox(-6.0F, -5.0F, -4.0F, 2.0F, 5.0F, 0.0F).mirror(false)
                .texOffs(2, 3).mirror()
                .addBox(4.0F, -5.0F, -4.0F, 2.0F, 5.0F, 0.0F).mirror(false)
                .texOffs(88, 6).mirror()
                .addBox(-4.0F, 0.0F, -4.0F, 8.0F, 6.0F, 0.0F).mirror(false),
            PartPose.ZERO
        );

        return LayerDefinition.create(mesh, 109, 125);
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