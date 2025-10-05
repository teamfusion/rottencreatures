package com.github.teamfusion.rottencreatures.client.level.entities.model;

import com.github.teamfusion.rottencreatures.client.level.entities.animations.BurnedAnimations;
import com.github.teamfusion.rottencreatures.common.level.entities.burned.Burned;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;

public class BurnedModel extends RottenModel<Burned> {
    public BurnedModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition base = mesh.getRoot();

        PartDefinition root = base.addOrReplaceChild("root",
            CubeListBuilder.create(),
            PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body",
            CubeListBuilder.create()
                .texOffs(0, 16)
                .addBox(-4.0F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F)
                .texOffs(24, 18)
                .addBox(-3.5F, -8.0F, -1.5F, 7.0F, 6.0F, 3.0F),
            PartPose.offset(0.0F, -12.0F, 0.0F));

        body.addOrReplaceChild("head",
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F)
                .texOffs(32, 0)
                .addBox(-4.5F, -8.5F, -4.5F, 9.0F, 9.0F, 9.0F)
                .texOffs(44, 18)
                .addBox(-3.5F, -7.0F, -3.5F, 7.0F, 7.0F, 1.0F),
            PartPose.offset(0.0F, -12.0F, 0.0F));

        body.addOrReplaceChild("right_arm",
            CubeListBuilder.create()
                .texOffs(0, 32)
                .addBox(-2.01F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F)
                .texOffs(68, 0)
                .addBox(-4.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F)
                .texOffs(68, 12)
                .addBox(-4.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F),
            PartPose.offset(-6.0F, -10.0F, 0.0F));

        body.addOrReplaceChild("left_arm",
            CubeListBuilder.create()
                .texOffs(17, 32)
                .addBox(-1.99F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F)
                .texOffs(92, 0)
                .addBox(-2.0F, -4.0F, -3.5F, 7.0F, 7.0F, 7.0F)
                .texOffs(92, 14)
                .addBox(-2.0F, -4.0F, -3.5F, 7.0F, 7.0F, 7.0F), 
            PartPose.offset(6.0F, -10.0F, 0.0F));

        root.addOrReplaceChild("left_leg",
            CubeListBuilder.create()
                .texOffs(51, 32)
                .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F),
            PartPose.offset(2.0F, -12.0F, 0.0F));

        root.addOrReplaceChild("right_leg",
            CubeListBuilder.create()
                .texOffs(34, 32)
                .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), 
            PartPose.offset(-2.0F, -12.0F, 0.0F));

        return LayerDefinition.create(mesh, 120, 48);
    }

    public static LayerDefinition createArmorLayer(CubeDeformation deformation) {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition base = mesh.getRoot();

        PartDefinition root = base.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F, deformation), PartPose.offset(0.0F, -12.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F,  deformation), PartPose.offset(0.0F, -12.0F, 0.0F));

        PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-2.01F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F,  deformation), PartPose.offset(-6.0F, -10.0F, 0.0F));

        PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1.99F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F,  deformation).mirror(false), PartPose.offset(6.0F, -10.0F, 0.0F));

        PartDefinition left_leg = root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F,  deformation).mirror(false), PartPose.offset(2.0F, -12.0F, 0.0F));

        PartDefinition right_leg = root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F,  deformation), PartPose.offset(-2.0F, -12.0F, 0.0F));

        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    public void setupAnim(Burned entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.getHead().yRot = netHeadYaw * Mth.DEG_TO_RAD;
        this.getHead().xRot = headPitch * Mth.DEG_TO_RAD;
        boolean isMoving = new Vec2((float) entity.getDeltaMovement().x, (float) entity.getDeltaMovement().z).length() > 0.01;

        animateZombieArms(this.left_arm, this.right_arm, entity.isAggressive(), this.attackTime, ageInTicks);

        if (entity.isBaby()) {
            this.applyStatic(BurnedAnimations.BABY_TRANSFORM);
        }

        if (isMoving) {
            if (entity.isObsidian()) {
                this.applyProcedural(BurnedAnimations.WALK_OBSIDIAN, ageInTicks);
            } else if (entity.isBaby() || entity.isCrazy()) {
                this.applyProcedural(BurnedAnimations.WALK_CRAZED, ageInTicks);
            } else {
                this.applyProcedural(BurnedAnimations.WALK_NORMAL, ageInTicks);
            }
        } else {
            if (entity.isCrazy()) {
                this.applyProcedural(BurnedAnimations.IDLE_CRAZED, ageInTicks);
            } else if (entity.isObsidian()) {
                this.applyProcedural(BurnedAnimations.IDLE_OBSIDIAN, ageInTicks);
            } else {
                this.applyProcedural(BurnedAnimations.IDLE_NORMAL, ageInTicks);
            }
        }
    }

    public static void animateZombieArms(ModelPart leftArm, ModelPart rightArm, boolean isAggressive, float attackTime, float ageInTicks) {
        float f = Mth.sin(attackTime * (float) Math.PI);
        float g = Mth.sin((1.0F - (1.0F - attackTime) * (1.0F - attackTime)) * (float) Math.PI);
        rightArm.zRot = 0.0F;
        leftArm.zRot = 0.0F;
        rightArm.yRot = -(0.1F - f * 0.6F);
        leftArm.yRot = 0.1F - f * 0.6F;
        rightArm.xRot -= f * 1.2F - g * 0.4F;
        leftArm.xRot -= f * 1.2F - g * 0.4F;
    }
}