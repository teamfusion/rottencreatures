package com.github.teamfusion.rottencreatures.client.level.entities.model;

import com.blackgear.platform.client.animator.v2.AnimationDefinition;
import com.blackgear.platform.client.animator.v2.KeyframeAnimations;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Vector3f;

public class RottenModel<T extends LivingEntity> extends HierarchicalModel<T> implements ArmedModel, HeadedModel {
    private static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();

    public final ModelPart root;
    public final ModelPart body;
    public final ModelPart head;
    public final ModelPart right_arm;
    public final ModelPart left_arm;
    public final ModelPart left_leg;
    public final ModelPart right_leg;

    public RottenModel(ModelPart root) {
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.head = this.body.getChild("head");
        this.right_arm = this.body.getChild("right_arm");
        this.left_arm = this.body.getChild("left_arm");
        this.left_leg = this.root.getChild("left_leg");
        this.right_leg = this.root.getChild("right_leg");
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    public void copyPropertiesTo(RottenModel<T> model) {
        super.copyPropertiesTo(model);
        model.root.copyFrom(this.root);
        model.head.copyFrom(this.head);
        model.body.copyFrom(this.body);
        model.right_arm.copyFrom(this.right_arm);
        model.left_arm.copyFrom(this.left_arm);
        model.right_leg.copyFrom(this.right_leg);
        model.left_leg.copyFrom(this.left_leg);
    }

    public void setAllVisible(boolean visible) {
        this.head.visible = visible;
        this.body.visible = visible;
        this.right_arm.visible = visible;
        this.left_arm.visible = visible;
        this.right_leg.visible = visible;
        this.left_leg.visible = visible;
    }

    @Override
    public void translateToHand(HumanoidArm side, PoseStack poseStack) {
        this.root.translateAndRotate(poseStack);
        this.body.translateAndRotate(poseStack);
        this.getArm(side).translateAndRotate(poseStack);
    }

    protected ModelPart getArm(HumanoidArm side) {
        return side == HumanoidArm.LEFT ? this.left_arm : this.right_arm;
    }

    @Override
    public ModelPart getHead() {
        return this.head;
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    protected void animate(AnimationState animationState, AnimationDefinition animationDefinition, float ageInTicks) {
        this.animate(animationState, animationDefinition, ageInTicks, 1.0F);
    }

    protected void animateWalk(AnimationDefinition animationDefinition, float limbSwing, float limbSwingAmount, float ageInTicks, float maxAnimationSpeed, float animationScaleFactor) {
        long l = (long)(limbSwing * 50.0F * maxAnimationSpeed);
        float f = Math.min(limbSwingAmount * animationScaleFactor, 1.0F);
        KeyframeAnimations.animate(this, animationDefinition, ageInTicks, l, f, ANIMATION_VECTOR_CACHE);
    }

    protected void animate(AnimationState animationState, AnimationDefinition animationDefinition, float ageInTicks, float speed) {
        animationState.updateTime(ageInTicks, speed);
        animationState.ifStarted(animationStatex -> KeyframeAnimations.animate(this, animationDefinition, ageInTicks, animationStatex.getAccumulatedTime(), 1.0F, ANIMATION_VECTOR_CACHE));
    }

    protected void applyStatic(AnimationDefinition animationDefinition) {
        KeyframeAnimations.animate(this, animationDefinition, 0F, 0L, 1.0F, ANIMATION_VECTOR_CACHE);
    }

    protected void applyProcedural(AnimationDefinition animationDefinition, float ageInTicks) {
        KeyframeAnimations.animate(this, animationDefinition, ageInTicks, 0L, 1.0F, ANIMATION_VECTOR_CACHE);
    }
}
