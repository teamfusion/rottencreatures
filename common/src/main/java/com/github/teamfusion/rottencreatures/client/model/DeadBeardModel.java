package com.github.teamfusion.rottencreatures.client.model;

import com.github.teamfusion.rottencreatures.common.entities.DeadBeard;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class DeadBeardModel<T extends DeadBeard> extends HumanoidModel<T> {
    public DeadBeardModel(ModelPart modelPart) {
        super(modelPart);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition root = meshdefinition.getRoot();
        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 17).addBox(-8.0F, -18.0F, -6.0F, 16.0F, 18.0F, 12.0F).texOffs(0, 47).addBox(-8.5F, -18.5F, -6.5F, 17.0F, 22.0F, 13.0F).texOffs(61, 47).addBox(-7.5F, -19.0F, -7.0F, 15.0F, 9.0F, 9.0F), PartPose.offset(0.0F, 13.0F, 0.0F));
        root.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-4.0F, -9.0F, -4.0F, 8.0F, 9.0F, 8.0F)
                .texOffs(56, 16).mirror().addBox(-3.5F, -8.5F, -3.5F, 7.0F, 8.0F, 7.0F)
                .texOffs(32, 0).mirror().addBox(-3.0F, -13.0F, -4.5F, 6.0F, 7.0F, 9.0F)
                .texOffs(62, 3).mirror().addBox(3.0F, -10.0F, -4.5F, 3.0F, 4.0F, 9.0F)
                .texOffs(62, 3).addBox(-6.0F, -10.0F, -4.5F, 3.0F, 4.0F, 9.0F)
                .texOffs(88, 6).mirror().addBox(-4.0F, 0.0F, -4.0F, 8.0F, 6.0F, 0.0F)
                .texOffs(2, 3).addBox(-6.0F, -5.0F, -4.0F, 2.0F, 5.0F, 0.0F)
                .texOffs(2, 3).mirror().addBox(4.0F, -5.0F, -4.0F, 2.0F, 5.0F, 0.0F), PartPose.offset(0.0F, -15.0F, -5.0F));

        root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(60, 65).addBox(-2.5F, -2.0F, -2.5F, 5.0F, 13.0F, 5.0F).texOffs(80, 75).addBox(-3.0F, 6.0F, -3.0F, 6.0F, 2.0F, 6.0F), PartPose.offset(-10.5F, -16.0F, 0.0F));
        root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(60, 65).mirror().addBox(-2.5F, -2.0F, -2.5F, 5.0F, 13.0F, 5.0F).texOffs(80, 75).mirror().addBox(-3.0F, 6.0F, -3.0F, 6.0F, 2.0F, 6.0F), PartPose.offset(10.5F, -16.0F, 0.0F));
        root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(56, 31).addBox(-2.5F, 0.0F, -3.0F, 5.0F, 11.0F, 5.0F), PartPose.offset(-5.5F, 13.0F, 0.5F));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(76, 35).mirror().addBox(-2.5F, 0.0F, -3.0F, 5.0F, 7.0F, 5.0F).texOffs(96, 41).mirror().addBox(-1.0F, 7.0F, -1.5F, 2.0F, 4.0F, 2.0F), PartPose.offset(5.5F, 13.0F, 0.5F));
        return LayerDefinition.create(meshdefinition, 109, 125);
    }
    
    @Override
    public void setupAnim(T deadBeard, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        super.setupAnim(deadBeard, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
        this.body.y = 13;
        this.head.y = -2;
        this.rightArm.setPos(-10.5F, -3.0F, 0.0F);
        this.leftArm.setPos(10.5F, -3.0F, 0.0F);
        this.rightLeg.y = 13;
        this.leftLeg.y = 13;
        if (deadBeard.isIgnited()) {
            this.rightArm.setRotation(-135, 0, 0);
            this.leftArm.setRotation(-135, 0, 0);
        } else {
            this.animateZombieArms(this.leftArm, this.rightArm, deadBeard.isAggressive(), this.attackTime, animationProgress);
        }
    }

    private void animateZombieArms(ModelPart leftArm, ModelPart rightArm, boolean isAggressive, float attackTime, float animProgress) {
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