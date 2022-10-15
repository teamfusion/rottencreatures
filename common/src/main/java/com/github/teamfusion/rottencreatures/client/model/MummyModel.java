package com.github.teamfusion.rottencreatures.client.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.monster.Zombie;

public class MummyModel<T extends Zombie> extends ZombieModel<T> {
    public MummyModel(ModelPart modelPart) {
        super(modelPart);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F).texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.ZERO);
        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 14.0F, 4.0F).texOffs(16, 34).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 14.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.ZERO);
        root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 14.0F, 4.0F).texOffs(40, 34).addBox(-3.0F, -2.0F, 2.0F, 4.0F, 14.0F, 4.0F), PartPose.offset(-5.0F, -4.0F, 0.0F));
        root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 52).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 14.0F, 4.0F).texOffs(48, 52).addBox(-1.0F, -2.0F, 2.0F, 4.0F, 14.0F, 4.0F), PartPose.offset(5.0F, -4.0F, 0.0F));
        root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F).texOffs(0, 34).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-1.9F, 10.0F, 0.0F));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(16, 52).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F).texOffs(0, 52).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 14.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(1.9F, 10.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 70);
    }

    @Override
    public boolean isAggressive(T zombie) {
        return zombie.isAggressive();
    }

    @Override
    public void setupAnim(T mummy, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        super.setupAnim(mummy, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
        this.head.y = -4;
        this.hat.y = -4;
        this.body.y = -4;
        this.leftArm.y = -2;
        this.rightArm.y = -2;
        this.leftLeg.y = 10;
        this.rightLeg.y = 10;
    }
}