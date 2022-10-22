package com.github.teamfusion.rottencreatures.client.model;

import com.github.teamfusion.rottencreatures.common.entities.Scarab;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class ScarabModel<T extends Scarab> extends HierarchicalModel<T> {
    protected final ModelPart root;
    protected final ModelPart head;
    protected final ModelPart body;
    protected final ModelPart rightFrontLeg;
    protected final ModelPart rightMiddleLeg;
    protected final ModelPart rightHindLeg;
    protected final ModelPart leftFrontLeg;
    protected final ModelPart leftMiddleLeg;
    protected final ModelPart leftHindLeg;
    protected final ModelPart rightElytra;
    protected final ModelPart leftElytra;
    protected final ModelPart rightWing;
    protected final ModelPart leftWing;

    public ScarabModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.head = this.body.getChild("head");
        this.rightFrontLeg = this.body.getChild("right_front_leg");
        this.leftFrontLeg = this.body.getChild("left_front_leg");
        this.rightMiddleLeg = this.body.getChild("right_middle_leg");
        this.leftMiddleLeg = this.body.getChild("left_middle_leg");
        this.rightHindLeg = this.body.getChild("right_hind_leg");
        this.leftHindLeg = this.body.getChild("left_hind_leg");
        this.rightElytra = this.body.getChild("right_elytra");
        this.leftElytra = this.body.getChild("left_elytra");
        this.rightWing = this.body.getChild("right_wing");
        this.leftWing = this.body.getChild("left_wing");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -4.0F, -2.0F, 7.0F, 4.0F, 9.0F), PartPose.offset(0.0F, 23.0F, -2.0F));
        body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(17, 26).addBox(-3.5F, 0.5F, -6.0F, 7.0F, 0.0F, 4.0F).texOffs(17, 20).addBox(-2.5F, -1.0F, -2.0F, 5.0F, 2.0F, 2.0F), PartPose.offset(0.0F, -1.0F, -2.0F));
        body.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(8, 27).addBox(0.0F, 0.0F, -0.5F, 2.0F, 0.0F, 1.0F), PartPose.offset(3.5F, 0.0F, -0.5F));
        body.addOrReplaceChild("left_middle_leg", CubeListBuilder.create().texOffs(8, 24).addBox(0.0F, 0.0F, -0.5F, 2.0F, 0.0F, 1.0F), PartPose.offset(3.5F, 0.0F, 2.5F));
        body.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(8, 21).addBox(0.0F, 0.0F, -0.5F, 2.0F, 0.0F, 1.0F), PartPose.offset(3.5F, 0.0F, 5.5F));
        body.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(3, 27).addBox(-2.0F, 0.0F, -0.5F, 2.0F, 0.0F, 1.0F), PartPose.offset(-3.5F, 0.0F, -0.5F));
        body.addOrReplaceChild("right_middle_leg", CubeListBuilder.create().texOffs(3, 24).addBox(-2.0F, 0.0F, -0.5F, 2.0F, 0.0F, 1.0F), PartPose.offset(-3.5F, 0.0F, 2.5F));
        body.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(3, 21).addBox(-2.0F, 0.0F, -0.5F, 2.0F, 0.0F, 1.0F), PartPose.offset(-3.5F, 0.0F, 5.5F));

        body.addOrReplaceChild("right_elytra", CubeListBuilder.create().texOffs(38, 51).addBox(-4.0F, -4.0F, 0.0F, 4.0F, 4.0F, 9.0F, new CubeDeformation(0.005F)), PartPose.offset(0.0F, 0.0F, -2.0F));
        body.addOrReplaceChild("left_elytra", CubeListBuilder.create().texOffs(38, 38).addBox(-0.0F, -4.0F, 0.0F, 4.0F, 4.0F, 9.0F, new CubeDeformation(0.005F)), PartPose.offset(0.0F, 0.0F, -2.0F));
        body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(-5, 52).addBox(-5.0F, 0.0F, 0.0F, 5.0F, 0.0F, 9.0F), PartPose.offset(3.0F, -4.0F, -2.0F));
        body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(15, 52).addBox(0.0F, 0.0F, 0.0F, 5.0F, 0.0F, 9.0F), PartPose.offset(-3.0F, -4.0F, -2.0F));

        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.head.yRot = headYaw * (float)(Math.PI / 180.0D);
        this.head.xRot = headPitch * (float)(Math.PI / 180.0D);
        float degree = 0.52F;
        this.rightFrontLeg.yRot = Mth.cos(limbAngle * 5.0F + (float)Math.PI) * 3.0F * limbDistance;
        this.rightFrontLeg.xRot = 0.0F;
        this.rightFrontLeg.zRot = -degree;
        this.rightMiddleLeg.yRot = -Mth.cos(limbAngle * 5.0F + (float)Math.PI) * 3.0F * limbDistance;
        this.rightMiddleLeg.xRot = 0.0F;
        this.rightMiddleLeg.zRot = -degree;
        this.rightHindLeg.yRot = Mth.cos(limbAngle * 5.0F + (float)Math.PI) * 3.0F * limbDistance;
        this.rightHindLeg.xRot = 0.0F;
        this.rightHindLeg.zRot = -degree;
        this.leftFrontLeg.yRot = Mth.cos(limbAngle * 5.0F) * 3.0F * limbDistance;
        this.leftFrontLeg.xRot = 0.0F;
        this.leftFrontLeg.zRot = degree;
        this.leftMiddleLeg.yRot = -Mth.cos(limbAngle * 5.0F) * 3.0F * limbDistance;
        this.leftMiddleLeg.xRot = 0.0F;
        this.leftMiddleLeg.zRot = degree;
        this.leftHindLeg.yRot = Mth.cos(limbAngle * 5.0F) * 3.0F * limbDistance;
        this.leftHindLeg.xRot = 0.0F;
        this.leftHindLeg.zRot = degree;
    }
}