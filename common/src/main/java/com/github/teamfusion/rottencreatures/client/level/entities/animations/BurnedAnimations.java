package com.github.teamfusion.rottencreatures.client.level.entities.animations;

import com.blackgear.platform.client.animator.v2.AnimationChannel;
import com.blackgear.platform.client.animator.v2.AnimationChannel.Targets;
import com.blackgear.platform.client.animator.v2.AnimationDefinition;
import com.blackgear.platform.client.animator.v2.Keyframe;
import com.blackgear.platform.client.animator.v2.KeyframeAnimations;
import net.minecraft.util.Mth;

public class BurnedAnimations {
    public static final AnimationDefinition BABY_TRANSFORM = AnimationDefinition.Builder.withLength(0.0F)
        .addAnimation("root", new AnimationChannel(Targets.SCALE,
            new Keyframe(KeyframeAnimations.scaleVec(0.5F, 0.5F, 0.5F)))
        )
        .addAnimation("head", new AnimationChannel(Targets.SCALE,
            new Keyframe(KeyframeAnimations.scaleVec(1.5F, 1.5F, 1.5F)))
        )
        .build();

    public static final AnimationDefinition IDLE_NORMAL = AnimationDefinition.Builder.withLength(0.0F)
        .addAnimation("body", new AnimationChannel(Targets.ROTATION,
            new Keyframe(query -> KeyframeAnimations.degreeVec(
                Mth.sin(query.animTime() * 180) * 2.5F + 15,
                0.0F,
                Mth.sin(query.animTime() * 270) * 1 + 2.5F
            )))
        )
        .addAnimation("body", new AnimationChannel(Targets.POSITION,
            new Keyframe(query -> KeyframeAnimations.posVec(
                0.0F,
                Mth.sin(query.animTime() * 270) * 1 / 4F - 1 / 4F,
                -Mth.cos(query.animTime() * 180) * 1 / 8F - 1 / 8F
            )))
        )
        .addAnimation("head", new AnimationChannel(Targets.ROTATION,
            new Keyframe(query -> KeyframeAnimations.degreeVec(
                Mth.sin(query.animTime() * 270) * 2.5F - 10,
                0.0F,
                Mth.cos(query.animTime() * 180) * 5 - 2.5F
            )))
        )
        .addAnimation("right_arm", new AnimationChannel(Targets.ROTATION,
            new Keyframe(query -> KeyframeAnimations.degreeVec(
                -Mth.sin(query.animTime() * 270) * 5 - 55,
                0.0F,
                0.0F
            )))
        )
        .addAnimation("right_arm", new AnimationChannel(Targets.POSITION,
            new Keyframe(query -> KeyframeAnimations.posVec(
                0.0F,
                0.0F,
                Mth.cos(query.animTime() * 270) * 1 / 4
            )))
        )
        .addAnimation("left_arm", new AnimationChannel(Targets.ROTATION,
            new Keyframe(query -> KeyframeAnimations.degreeVec(
                Mth.cos(query.animTime() * 180) * 10 - 55,
                0.0F,
                0.0F
            )))
        )
        .addAnimation("left_arm", new AnimationChannel(Targets.POSITION,
            new Keyframe(query -> KeyframeAnimations.posVec(
                0.0F,
                0.0F,
                Mth.sin(query.animTime() * 180) * 1 / 3
            )))
        )
        .addAnimation("right_leg", new AnimationChannel(Targets.ROTATION,
            new Keyframe(KeyframeAnimations.degreeVec(0.0F, 0.0F, 2.5F)))
        )
        .addAnimation("left_leg", new AnimationChannel(Targets.ROTATION,
            new Keyframe(KeyframeAnimations.degreeVec(0.0F, 0.0F, -5F)))
        )
        .build();

    public static final AnimationDefinition IDLE_CRAZED = AnimationDefinition.Builder.withLength(0.0F)
        .addAnimation("body", new AnimationChannel(Targets.ROTATION,
            new Keyframe(query -> KeyframeAnimations.degreeVec(
                Mth.cos(query.animTime()*270)*5-Mth.sin(query.animTime()*360)*2.5F+15,
                -Mth.sin(query.animTime()*720)*2.5F,
                Mth.cos(query.animTime()*270)*2.5F
            )))
        )
        .addAnimation("body", new AnimationChannel(Targets.POSITION,
            new Keyframe(query -> KeyframeAnimations.posVec(
                0.0F,
                Mth.sin(query.animTime()*270)*1/4,
                0.0F
            )))
        )
        .addAnimation("head", new AnimationChannel(Targets.ROTATION,
            new Keyframe(query -> KeyframeAnimations.degreeVec(
                Mth.sin(query.animTime()*180)*2.5F+Mth.cos(query.animTime()*270)*2.5F-10,
                Mth.cos(query.animTime()*360)*5,
                Mth.sin(query.animTime()*270)*5+Mth.cos(query.animTime()*2880)*1
            )))
        )
        .addAnimation("head", new AnimationChannel(Targets.POSITION,
            new Keyframe(query -> KeyframeAnimations.posVec(
                0.0F,
                0.0F,
                -Mth.sin(query.animTime()*540)*1/2-1/2F
            )))
        )
        .addAnimation("right_arm", new AnimationChannel(Targets.ROTATION,
            new Keyframe(query -> KeyframeAnimations.degreeVec(
                Mth.cos(query.animTime()*270)*15-Mth.sin(query.animTime()*540-55)*5-45,
                0.0F,
                0.0F
            )))
        )
        .addAnimation("right_arm", new AnimationChannel(Targets.POSITION,
            new Keyframe(query -> KeyframeAnimations.posVec(
                0.0F,
                -Mth.cos(query.animTime()*360)*1/3,
                0.0F
            )))
        )
        .addAnimation("left_arm", new AnimationChannel(Targets.ROTATION,
            new Keyframe(query -> KeyframeAnimations.degreeVec(
                Mth.sin(query.animTime()*270)*10-Mth.cos(query.animTime()*360-45)*15-45,
                0.0F,
                0.0F
            )))
        )
        .addAnimation("left_arm", new AnimationChannel(Targets.POSITION,
            new Keyframe(query -> KeyframeAnimations.posVec(
                0.0F,
                -Mth.sin(query.animTime()*540)*1/4,
                0.0F
            )))
        )
        .addAnimation("right_leg", new AnimationChannel(Targets.ROTATION,
            new Keyframe(KeyframeAnimations.degreeVec(-10.0F, 0.0F, 5.0F)))
        )
        .addAnimation("left_leg", new AnimationChannel(Targets.ROTATION,
            new Keyframe(KeyframeAnimations.degreeVec(10.0F, 0.0F, -5F)))
        )
        .build();

    public static final AnimationDefinition IDLE_OBSIDIAN = AnimationDefinition.Builder.withLength(0.0F)
        .addAnimation("body", new AnimationChannel(Targets.ROTATION,
            new Keyframe(query -> KeyframeAnimations.degreeVec(
                Mth.clamp(Mth.cos(query.animTime() * 90) * 20, -2.5F, 2.5F) + Mth.cos(query.animTime() * 90) * 1 + 20,
                0.0F,
                Mth.clamp(Mth.sin(query.animTime() * 180) * 5, -1, 2.5F)
            )))
        )
        .addAnimation("head", new AnimationChannel(Targets.ROTATION,
            new Keyframe(query -> KeyframeAnimations.degreeVec(
                Mth.clamp(Mth.cos(query.animTime() * 90) * 40, -1, 1) + Mth.sin(query.animTime() * 180) * 1 - 10,
                0.0F,
                Mth.clamp(-Mth.sin(query.animTime() * 180) * 7.5F, -1, 2.5F)
            )))
        )
        .addAnimation("right_arm", new AnimationChannel(Targets.ROTATION,
            new Keyframe(query -> KeyframeAnimations.degreeVec(
                -Mth.cos(query.animTime() * 180) * 1 + Mth.clamp(-Mth.cos(query.animTime() * 90) * 10, -2.5F, 2.5F) - 60,
                0.0F,
                0.0F
            )))
        )
        .addAnimation("right_arm", new AnimationChannel(Targets.POSITION,
            new Keyframe(query -> KeyframeAnimations.posVec(
                0.0F,
                Mth.clamp(-Mth.sin(query.animTime() * 90) * 6, -1 / 6F, 1 / 6F),
                0.0F
            )))
        )
        .addAnimation("left_arm", new AnimationChannel(Targets.ROTATION,
            new Keyframe(query -> KeyframeAnimations.degreeVec(
                -Mth.cos(query.animTime() * 90 + 30) * 2.5F + Mth.clamp(-Mth.cos(query.animTime() * 90 + 30) * 10, -2.5F, 2.5F) - 60,
                0.0F,
                0.0F
            )))
        )
        .addAnimation("left_arm", new AnimationChannel(Targets.POSITION,
            new Keyframe(query -> KeyframeAnimations.posVec(
                0.0F,
                Mth.clamp(-Mth.cos(query.animTime() * 90) * 2, -1 / 6F, 1 / 6F),
                0.0F
            )))
        )
        .addAnimation("right_leg", new AnimationChannel(Targets.ROTATION,
            new Keyframe(KeyframeAnimations.degreeVec(-5.0F, 0.0F, 5.0F)))
        )
        .addAnimation("left_leg", new AnimationChannel(Targets.ROTATION,
            new Keyframe(KeyframeAnimations.degreeVec(5.0F, 0.0F, -5F)))
        )
        .build();

    public static final AnimationDefinition WALK_NORMAL = AnimationDefinition.Builder.withLength(0.0F)
        .addAnimation("root", new AnimationChannel(Targets.ROTATION,
            new Keyframe(query -> KeyframeAnimations.degreeVec(
                Mth.sin(query.animTime() * 180) * 2.5F,
                0.0F,
                -Mth.cos(query.animTime() * 180) * 5F
            )))
        )
        .addAnimation("body", new AnimationChannel(Targets.ROTATION,
            new Keyframe(query -> KeyframeAnimations.degreeVec(
                Mth.sin(query.animTime() * 180) * 5 -Mth.cos(query.animTime() * 270) * 2.5F - Mth.cos(query.animTime() * 360) * 2.5F + 5,
                0.0F,
                0.0F
            )))
        )
        .addAnimation("body", new AnimationChannel(Targets.POSITION,
            new Keyframe(query -> KeyframeAnimations.posVec(
                0.0F,
                Mth.abs(Mth.cos(query.animTime() * 180) * 1) - 1,
                -Mth.abs(Mth.sin(query.animTime() * 180) * 1 / 2)
            )))
        )
        .addAnimation("head", new AnimationChannel(Targets.ROTATION,
            new Keyframe(query -> KeyframeAnimations.degreeVec(
                -Mth.cos(query.animTime() * 180) * 5 - Mth.cos(query.animTime() * 270 + 45) * 5 + 5,
                0.0F,
                -Mth.sin(query.animTime() * 180) * 5
            )))
        )
        .addAnimation("right_arm", new AnimationChannel(Targets.ROTATION,
            new Keyframe(query -> KeyframeAnimations.degreeVec(
                -Mth.sin(query.animTime() * 180) * 10 - 45,
                0.0F,
                0.0F
            )))
        )
        .addAnimation("right_arm", new AnimationChannel(Targets.POSITION,
            new Keyframe(query -> KeyframeAnimations.posVec(
                0.0F,
                Mth.cos(query.animTime() * 360) * 1 / 3,
                0.0F
            )))
        )
        .addAnimation("left_arm", new AnimationChannel(Targets.ROTATION,
            new Keyframe(query -> KeyframeAnimations.degreeVec(
                -Mth.sin(query.animTime() * 180 + 135) * 10 - 45,
                0.0F,
                0.0F
            )))
        )
        .addAnimation("left_arm", new AnimationChannel(Targets.POSITION,
            new Keyframe(query -> KeyframeAnimations.posVec(
                0.0F,
                Mth.cos(query.animTime() * 360) * 1 / 3,
                0.0F
            )))
        )
        .addAnimation("right_leg", new AnimationChannel(Targets.ROTATION,
            new Keyframe(query -> KeyframeAnimations.degreeVec(
                Mth.sin(query.animTime() * 180) * 15 + Mth.clamp(Mth.cos(query.animTime() * 180) * 20, -5, 5),
                0.0F,
                0.0F
            )))
        )
        .addAnimation("right_leg", new AnimationChannel(Targets.POSITION,
            new Keyframe(query -> KeyframeAnimations.posVec(
                0.0F,
                Mth.clamp(-Mth.cos(query.animTime() * 180) * 2, 0, 2),
                0.0F
            )))
        )
        .addAnimation("left_leg", new AnimationChannel(Targets.ROTATION,
            new Keyframe(query -> KeyframeAnimations.degreeVec(
                -Mth.sin(query.animTime() * 180) * 15 + Mth.clamp(-Mth.cos(query.animTime() * 180) * 20, -5, 5),
                0.0F,
                0.0F
            )))
        )
        .addAnimation("left_leg", new AnimationChannel(Targets.POSITION,
            new Keyframe(query -> KeyframeAnimations.posVec(
                0.0F,
                Mth.clamp(Mth.cos(query.animTime() * 180) * 2, 0, 2),
                0.0F
            )))
        )
        .build();

    public static final AnimationDefinition WALK_CRAZED = AnimationDefinition.Builder.withLength(0.0F)
        .addAnimation("root", new AnimationChannel(Targets.ROTATION,
            new Keyframe(query -> KeyframeAnimations.degreeVec(
                Mth.sin(query.animTime() * 1440) * 2.5F,
                0.0F,
                -Mth.cos(query.animTime() * 720) * 5
            )))
        )
        .addAnimation("body", new AnimationChannel(Targets.ROTATION,
            new Keyframe(query -> KeyframeAnimations.degreeVec(
                Mth.cos(query.animTime() * 1440) * 5 + 20,
                Mth.sin(query.animTime() * 720) * 10,
                0.0F
            )))
        )
        .addAnimation("body", new AnimationChannel(Targets.POSITION,
            new Keyframe(query -> KeyframeAnimations.posVec(
                0.0F,
                Mth.sin(query.animTime() * 1440) * 1 + Mth.clamp(-Mth.cos(query.animTime() * 1440) * 1, 0, 1) - 1,
                0.0F
            )))
        )
        .addAnimation("head", new AnimationChannel(Targets.ROTATION,
            new Keyframe(query -> KeyframeAnimations.degreeVec(
                Mth.sin(query.animTime() * 1440) * 5 - 15 + Mth.cos(query.animTime() * 540) * 5,
                -Mth.sin(query.animTime() * 720 + 10) * 10,
                -Mth.abs(Mth.cos(query.animTime() * 540) * 15)
            )))
        )
        .addAnimation("head", new AnimationChannel(Targets.POSITION,
            new Keyframe(query -> KeyframeAnimations.posVec(
                0.0F,
                0.0F,
                Mth.cos(query.animTime() * 1440) * 1
            )))
        )
        .addAnimation("right_arm", new AnimationChannel(Targets.ROTATION,
            new Keyframe(query -> KeyframeAnimations.degreeVec(
                -Mth.sin(query.animTime() * 720) * 35 - Mth.cos(query.animTime() * 540) * 10 - 95,
                0.0F,
                0.0F
            )))
        )
        .addAnimation("right_arm", new AnimationChannel(Targets.POSITION,
            new Keyframe(query -> KeyframeAnimations.posVec(
                0.0F,
                -Mth.cos(query.animTime() * 720) * 1,
                Mth.sin(query.animTime() * 540) * 1
            )))
        )
        .addAnimation("left_arm", new AnimationChannel(Targets.ROTATION,
            new Keyframe(query -> KeyframeAnimations.degreeVec(
                -Mth.cos(query.animTime() * 720) * 25 + Mth.sin(query.animTime() * 540 + 45) * 20 - 95,
                0.0F,
                0.0F
            )))
        )
        .addAnimation("left_arm", new AnimationChannel(Targets.POSITION,
            new Keyframe(query -> KeyframeAnimations.posVec(
                0.0F,
                Mth.sin(query.animTime() * 540) * 1,
                -Mth.sin(query.animTime() * 720) * 1
            )))
        )
        .addAnimation("right_leg", new AnimationChannel(Targets.ROTATION,
            new Keyframe(query -> KeyframeAnimations.degreeVec(
                -Mth.cos(query.animTime() * 720) * 60 + 20,
                0.0F,
                0.0F
            )))
        )
        .addAnimation("right_leg", new AnimationChannel(Targets.POSITION,
            new Keyframe(query -> KeyframeAnimations.posVec(
                0.0F,
                Mth.clamp(-Mth.sin(query.animTime() * 720) * 2, 0, 2),
                -Mth.cos(query.animTime() * 720 + 45) * 2 - 1
            )))
        )
        .addAnimation("left_leg", new AnimationChannel(Targets.ROTATION,
            new Keyframe(query -> KeyframeAnimations.degreeVec(
                Mth.cos(query.animTime() * 720) * 60 + 20,
                0.0F,
                0.0F
            )))
        )
        .addAnimation("left_leg", new AnimationChannel(Targets.POSITION,
            new Keyframe(query -> KeyframeAnimations.posVec(
                0.0F,
                Mth.clamp(Mth.sin(query.animTime() * 720) * 2, 0, 2),
                Mth.cos(query.animTime() * 720 + 45) * 2 - 1
            )))
        )
        .build();

    public static final AnimationDefinition WALK_OBSIDIAN = AnimationDefinition.Builder.withLength(0.0F)
        .addAnimation("root", new AnimationChannel(Targets.ROTATION,
            new Keyframe(query -> KeyframeAnimations.degreeVec(
                -Mth.sin(query.animTime()*360)*2.5F,
                0.0F,
                -Mth.cos(query.animTime()*180)*4+Mth.cos(query.animTime()*360+45)*1
            )))
        )
        .addAnimation("body", new AnimationChannel(Targets.ROTATION,
            new Keyframe(query -> KeyframeAnimations.degreeVec(
                Mth.clamp(-Mth.cos(query.animTime()*360)*5, -2.5F, 2.5F)+15,
                0.0F,
                Mth.clamp(-Mth.sin(query.animTime()*180)*5, -2.5F, 2.5F)
            )))
        )
        .addAnimation("body", new AnimationChannel(Targets.POSITION,
            new Keyframe(query -> KeyframeAnimations.posVec(
                0.0F,
                Mth.clamp(Mth.cos(query.animTime()*360)*1, 0, 1)-1,
                0.0F
            )))
        )
        .addAnimation("head", new AnimationChannel(Targets.ROTATION,
            new Keyframe(query -> KeyframeAnimations.degreeVec(
                Mth.clamp(-Mth.sin(query.animTime()*360)*10, -5, 5)+Mth.cos(query.animTime()*360)*5-10,
                0.0F,
                Mth.clamp(-Mth.sin(query.animTime()*180)*10, -2.5F, 2.5F)
            )))
        )
        .addAnimation("right_arm", new AnimationChannel(Targets.ROTATION,
            new Keyframe(query -> KeyframeAnimations.degreeVec(
                Mth.clamp(Mth.cos(query.animTime()*360)*15, -7.5F, 7.5F)-Mth.sin(query.animTime()*360)*10-65,
                0.0F,
                Mth.clamp(Mth.sin(query.animTime()*180)*5, -2.5F, 2.5F)
            )))
        )
        .addAnimation("right_arm", new AnimationChannel(Targets.POSITION,
            new Keyframe(query -> KeyframeAnimations.posVec(
                0.0F,
                Mth.clamp(Mth.sin(query.animTime()*360)*20, -1/6F, 1/6F),
                0.0F
            )))
        )
        .addAnimation("left_arm", new AnimationChannel(Targets.ROTATION,
            new Keyframe(query -> KeyframeAnimations.degreeVec(
                Mth.clamp(Mth.cos(query.animTime()*360)*15, -7.5F, 7.5F)+Mth.sin(query.animTime()*360)*10-65,
                0.0F,
                Mth.clamp(Mth.cos(query.animTime()*180)*5, -2.5F, 2.5F)
            )))
        )
        .addAnimation("left_arm", new AnimationChannel(Targets.POSITION,
            new Keyframe(query -> KeyframeAnimations.posVec(
                0.0F,
                Mth.clamp(-Mth.cos(query.animTime()*360)*20, -1/6F, 1/6F),
                0.0F
            )))
        )
        .addAnimation("right_leg", new AnimationChannel(Targets.ROTATION,
            new Keyframe(query -> KeyframeAnimations.degreeVec(
                Mth.sin(query.animTime()*180)*10+Mth.clamp(Mth.cos(query.animTime()*180)*20, -5, 5),
                0.0F,
                0.0F
            )))
        )
        .addAnimation("right_leg", new AnimationChannel(Targets.POSITION,
            new Keyframe(query -> KeyframeAnimations.posVec(
                0.0F,
                Mth.clamp(-Mth.cos(query.animTime()*180+45)*5, 0, 5/3F),
                0.0F
            )))
        )
        .addAnimation("left_leg", new AnimationChannel(Targets.ROTATION,
            new Keyframe(query -> KeyframeAnimations.degreeVec(
                -Mth.sin(query.animTime()*180)*10+Mth.clamp(-Mth.cos(query.animTime()*180)*20, -5, 5),
                0.0F,
                0.0F
            )))
        )
        .addAnimation("left_leg", new AnimationChannel(Targets.POSITION,
            new Keyframe(query -> KeyframeAnimations.posVec(
                0.0F,
                Mth.clamp(Mth.cos(query.animTime()*180+45)*5, 0, 5/3F),
                0.0F
            )))
        )
        .build();
}