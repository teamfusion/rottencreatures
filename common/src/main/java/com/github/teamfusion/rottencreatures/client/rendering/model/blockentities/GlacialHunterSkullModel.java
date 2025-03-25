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
public class GlacialHunterSkullModel extends SkullModelBase {
    private final ModelPart root;
    private final ModelPart head;

    public GlacialHunterSkullModel(ModelPart root) {
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
            "hat",
            CubeListBuilder.create()
                .texOffs(32, 0)
                .addBox(-4.0F, -8.0F, -3.75F, 9.0F, 9.0F, 8.0F)
                .texOffs(67, 5)
                .addBox(-4.5F, -8.5F, -5.75F, 2.0F, 10.0F, 2.0F)
                .texOffs(67, 5)
                .addBox(3.5F, -8.5F, -5.75F, 2.0F, 10.0F, 2.0F)
                .texOffs(77, 0)
                .addBox(-2.5F, -8.5F, -5.75F, 6.0F, 2.0F, 2.0F)
                .texOffs(77, 13)
                .addBox(-2.5F, -0.5F, -5.75F, 6.0F, 2.0F, 2.0F), 
            PartPose.offset(-0.5F, -0.5F, 0.0F)
        );

        return LayerDefinition.create(mesh, 96, 64);
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