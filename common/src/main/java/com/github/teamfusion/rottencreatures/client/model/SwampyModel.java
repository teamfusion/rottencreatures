package com.github.teamfusion.rottencreatures.client.model;

import com.github.teamfusion.rottencreatures.common.entities.Swampy;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class SwampyModel extends HumanoidModel<Swampy> {
    public SwampyModel(ModelPart modelPart) {
        super(modelPart);
        this.crouching = true;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(64, 0).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(64, 16).addBox(-2.0F, -2.0F, 2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(40, 32).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(-6.0F, -10.0F, 0.0F, -1.5708F, 0.0F, 0.0F));
        root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 48).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(64, 48).addBox(-2.0F, -2.0F, 2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(48, 48).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(6.0F, -10.0F, 0.0F, -1.5708F, 0.0F, 0.0F));
        return LayerDefinition.create(mesh, 96, 64);
    }

    @Override
    public void setupAnim(Swampy swampy, float angle, float distance, float animProgress, float yaw, float pitch) {
        super.setupAnim(swampy, angle, distance, animProgress, yaw, pitch);
        animateZombieArms(this.leftArm, this.rightArm, swampy.isAggressive(), this.attackTime, animProgress);
    }

    public static void animateZombieArms(ModelPart leftArm, ModelPart rightArm, boolean isAggressive, float attackTime, float animProgress) {
        float j = (float)(-Math.PI) / (isAggressive ? 3.5F : 10.0F);
        float h = Mth.sin(attackTime * (float)Math.PI);
        float i = Mth.sin((1.0F - (1.0F - attackTime) * (1.0F - attackTime)) * (float)Math.PI);
        rightArm.zRot = 0.0F;
        leftArm.zRot = 0.0F;
        rightArm.yRot = -(0.1F - h * 0.6F);
        leftArm.yRot = 0.1F - h * 0.6F;
        rightArm.xRot = j;
        leftArm.xRot = j;
        rightArm.xRot += h * 1.2F - i * 0.4F;
        leftArm.xRot += h * 1.2F - i * 0.4F;
        AnimationUtils.bobArms(rightArm, leftArm, animProgress);
    }
}