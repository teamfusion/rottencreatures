package com.github.teamfusion.rottencreatures.client.model;

import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.monster.Zombie;

public class GlacialHunterModel<T extends Zombie> extends ZombieModel<T> {
    public GlacialHunterModel(ModelPart modelPart) {
        super(modelPart);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
        root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -5.0F, 8.0F, 8.0F, 8.0F).texOffs(32, 0).addBox(-4.5F, -8.5F, -4.0F, 9.0F, 9.0F, 8.0F).texOffs(67, 5).mirror().addBox(3.0F, -9.0F, -6.0F, 2.0F, 10.0F, 2.0F).texOffs(67, 5).addBox(-5.0F, -9.0F, -6.0F, 2.0F, 10.0F, 2.0F).texOffs(77, 0).addBox(-3.0F, -9.0F, -6.0F, 6.0F, 2.0F, 2.0F).texOffs(77, 13).addBox(-3.0F, -1.0F, -6.0F, 6.0F, 2.0F, 2.0F), PartPose.offset(0.0F, -14.0F, -1.0F));
        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 20).addBox(-6.0F, -14.0F, -4.0F, 12.0F, 14.0F, 8.0F).texOffs(40, 31).addBox(-6.5F, -2.0F, -4.5F, 13.0F, 2.0F, 9.0F).texOffs(86, 33).mirror().addBox(5.5F, -6.0F, -1.5F, 2.0F, 6.0F, 3.0F).texOffs(86, 33).mirror().addBox(-7.5F, -6.0F, -1.5F, 2.0F, 6.0F, 3.0F), PartPose.offset(0.0F, 14.0F, 0.0F));
        root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 42).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 13.0F, 4.0F).texOffs(36, 52).addBox(-3.5F, 7.0F, -2.5F, 5.0F, 2.0F, 5.0F), PartPose.offset(-7.0F, -12.0F, 0.0F));
        root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 42).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 13.0F, 4.0F).texOffs(36, 52).mirror().addBox(-1.5F, 7.0F, -2.5F, 5.0F, 2.0F, 5.0F), PartPose.offset(7.0F, -12.0F, 0.0F));
        root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(16, 44).addBox(-2.5F, 0.0F, -3.5F, 5.0F, 10.0F, 5.0F).texOffs(36, 42).addBox(-3.0F, 6.0F, -4.0F, 6.0F, 2.0F, 6.0F), PartPose.offset(-3.5F, 14.0F, 1.0F));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(16, 44).mirror().addBox(-2.5F, 0.0F, -3.5F, 5.0F, 10.0F, 5.0F).texOffs(36, 42).mirror().addBox(-3.0F, 6.0F, -4.0F, 6.0F, 2.0F, 6.0F), PartPose.offset(3.5F, 14.0F, 1.0F));
        return LayerDefinition.create(mesh, 96, 64);
    }

    @Override
    public boolean isAggressive(T zombie) {
        return zombie.isAggressive();
    }

    @Override
    public void setupAnim(T hunter, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        super.setupAnim(hunter, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
        this.head.z = -2;
        this.body.y = 14;
        this.body.z = -1;
        this.rightArm.x = -7;
        this.leftArm.x = 7;
        this.rightLeg.y = 14;
        this.leftLeg.y = 14;
    }
}