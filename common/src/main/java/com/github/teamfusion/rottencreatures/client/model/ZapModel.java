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

public class ZapModel<T extends Zombie> extends ZombieModel<T> {
    public ZapModel(ModelPart part) {
        super(part);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition root = mesh.getRoot();
        PartDefinition hat = root.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.offset(0.0F, -12.0F, 0.0F));
        hat.addOrReplaceChild("right_fin", CubeListBuilder.create().texOffs(32, 8).addBox(-5.0F, -4.0F, 0.0F, 5.0F, 8.0F, 0.0F), PartPose.offsetAndRotation(-4.0F, -4.0F, -1.0F, 0.0F, 0.7854F, 0.0F));
        hat.addOrReplaceChild("left_fin", CubeListBuilder.create().texOffs(40, 0).addBox(-4.0F, -5.0F, 0.0F, 8.0F, 5.0F, 0.0F), PartPose.offsetAndRotation(0.0F, -8.0F, -1.0F, -0.7854F, 0.0F, 0.0F));
        hat.addOrReplaceChild("top_fin", CubeListBuilder.create().texOffs(51, 8).addBox(0.0F, -4.0F, 0.0F, 5.0F, 8.0F, 0.0F), PartPose.offsetAndRotation(4.0F, -4.0F, -1.0F, 0.0F, -0.7854F, 0.0F));
        return LayerDefinition.create(mesh, 64, 64);
    }
}