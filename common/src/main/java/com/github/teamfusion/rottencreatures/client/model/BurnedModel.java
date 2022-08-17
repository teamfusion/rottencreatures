package com.github.teamfusion.rottencreatures.client.model;

import com.github.teamfusion.rottencreatures.common.entities.Burned;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class BurnedModel extends ZombieModel<Burned> {
    public BurnedModel(ModelPart part) {
        super(part);
    }

    public static LayerDefinition createBodyLayer(float grow) {
        MeshDefinition mesh = HumanoidModel.createMesh(of(grow), 0.0F);
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, of(grow)).texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, add(grow)), PartPose.ZERO);
        root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, of(grow)).texOffs(60, 0).addBox(-4.5F, -8.5F, -4.5F, 9.0F, 9.0F, 9.0F, of(grow)).texOffs(32, 2).addBox(-3.5F, -7.0F, -3.5F, 7.0F, 7.0F, 7.0F, of(grow)), PartPose.ZERO);
        root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, of(grow)).texOffs(40, 32).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, add(grow)).texOffs(72, 20).addBox(-4.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, of(grow)).texOffs(76, 38).addBox(-3.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, of(grow)), PartPose.ZERO);
        root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 48).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, of(grow)).texOffs(48, 48).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, add(grow)).texOffs(68, 50).addBox(-2.0F, -4.0F, -3.5F, 7.0F, 7.0F, 7.0F, of(grow)), PartPose.ZERO);
        root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, of(grow)).texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, add(grow)), PartPose.offset(-1.9F, 12.0F, 0.0F));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(16, 48).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, of(grow)).texOffs(0, 48).mirror().addBox(-2.0F,  0.0F, -2.0F, 4.0F, 12.0F, 4.0F, add(grow)), PartPose.offset(1.9F, 12.0F, 0.0F));
        root.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
        return LayerDefinition.create(mesh, 96, 64);
    }

    private static CubeDeformation of(float grow) {
        return new CubeDeformation(grow);
    }

    private static CubeDeformation add(float grow) {
        return new CubeDeformation(grow + 0.25F);
    }

    @Override
    public boolean isAggressive(Burned burned) {
        return burned.isAggressive();
    }
}