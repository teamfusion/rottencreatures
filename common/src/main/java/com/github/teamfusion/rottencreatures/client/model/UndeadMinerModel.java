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

public class UndeadMinerModel<T extends Zombie> extends ZombieModel<T> {
    public UndeadMinerModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition root = mesh.getRoot();
        root.getChild("body").addOrReplaceChild("belt", CubeListBuilder.create().texOffs(100, 7).addBox(-6.0F, -4.0F, -1.0F, 2.0F, 5.0F, 4.0F).texOffs(100, 7).mirror().addBox(4.0F, -4.0F, -1.0F, 2.0F, 5.0F, 4.0F), PartPose.offset(0.0F, 12.0F, -1.0F));
        root.getChild("head").addOrReplaceChild("helmet", CubeListBuilder.create().texOffs(64, 0).addBox(-4.5F, -9.0F, -4.5F, 9.0F, 4.0F, 9.0F).texOffs(64, 17).addBox(-5.0F, -9.5F, -5.0F, 10.0F, 5.0F, 10.0F).texOffs(102, 1).addBox(-1.5F, -7.5F, -6.0F, 3.0F, 3.0F, 2.0F), PartPose.ZERO);
        return LayerDefinition.create(mesh, 112, 64);
    }

    @Override
    public boolean isAggressive(T zombie) {
        return zombie.isAggressive();
    }
}