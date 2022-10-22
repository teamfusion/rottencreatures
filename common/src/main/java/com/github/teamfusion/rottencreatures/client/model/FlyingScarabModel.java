package com.github.teamfusion.rottencreatures.client.model;

import com.github.teamfusion.rottencreatures.common.entities.FlyingScarab;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;

public class FlyingScarabModel<T extends FlyingScarab> extends ScarabModel<T> {
    public FlyingScarabModel(ModelPart root) {
        super(root);
    }

    @Override
    public void setupAnim(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        float degree = animationProgress * 120.32113F * (float)(Math.PI / 180.0D);
        this.body.xRot = -0.87F;
        this.head.xRot = 0.87F;
        this.rightElytra.setPos(-1.5F, -2.0F, -2.0F);
        this.rightElytra.setRotation(0.87F, -0.52F, -0.17F);
        this.leftElytra.setPos(1.5F, -2.0F, -2.0F);
        this.leftElytra.setRotation(0.87F, 0.52F, 0.17F);
        this.rightWing.yRot = 1.57F;
        this.rightWing.zRot = Mth.cos(degree) * (float)Math.PI * 0.15F;
        this.leftWing.yRot = -1.57F;
        this.leftWing.zRot = -Mth.cos(degree) * (float)Math.PI * 0.15F;
    }
}