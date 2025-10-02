package com.github.teamfusion.rottencreatures.client.level.entities.animation;

import com.blackgear.platform.client.animator.AnimatedChannel;
import com.blackgear.platform.client.animator.AnimatedPoint;
import com.blackgear.platform.client.animator.MathAnimator;
import com.github.teamfusion.rottencreatures.client.level.entities.model.ScarabModel;
import net.minecraft.client.animation.AnimationChannel.Targets;
import net.minecraft.util.Mth;
import org.joml.Vector3f;

public class ScarabAnimations extends AnimationDefinition {
    public static final MathAnimator FLYING = new MathAnimator.Builder()
        .addAnimation(ScarabModel.BODY, new AnimatedChannel(
            new AnimatedPoint(Targets.ROTATION, animTime -> new Vector3f(
                toRadians(-37.5F),
                -Mth.cos(animTime * toRadians(180 - 90)) * toRadians(5) - Mth.sin(animTime * toRadians(2880)) * toRadians(1 / 2),
                -Mth.sin(animTime * toRadians(180)) * toRadians(10)
            ))
        ))
        .addAnimation(ScarabModel.RIGHT_ELYTRA, new AnimatedChannel(
            new AnimatedPoint(Targets.ROTATION, animTime -> new Vector3f(
                Mth.abs(Mth.cos(animTime * toRadians(1440)) * toRadians(5)) + toRadians(40),
                toRadians(-40),
                toRadians(-20)
            ))
        ))
        .addAnimation(ScarabModel.LEFT_ELYTRA, new AnimatedChannel(
            new AnimatedPoint(Targets.ROTATION, animTime -> new Vector3f(
                Mth.abs(Mth.cos(animTime * toRadians(1440)) * toRadians(5)) + toRadians(40),
                toRadians(40),
                toRadians(20)
            ))
        ))
        .addAnimation(ScarabModel.RIGHT_WING, new AnimatedChannel(
            new AnimatedPoint(Targets.ROTATION, animTime -> new Vector3f(
                Mth.abs(Mth.cos(animTime * toRadians(2880)) * toRadians(15)),
                toRadians(20),
                0
            ))
        ))
        .addAnimation(ScarabModel.LEFT_WING, new AnimatedChannel(
            new AnimatedPoint(Targets.ROTATION, animTime -> new Vector3f(
                Mth.abs(Mth.cos(animTime * toRadians(2880)) * toRadians(15)),
                toRadians(-20),
                0
            ))
        ))
        .addAnimation(ScarabModel.LEFT_FRONT_LEG, new AnimatedChannel(
            new AnimatedPoint(Targets.ROTATION, animTime -> new Vector3f(
                toRadians(55),
                Mth.abs(Mth.cos(animTime * toRadians(1440)) * toRadians(5)),
                0
            ))
        ))
        .addAnimation(ScarabModel.RIGHT_FRONT_LEG, new AnimatedChannel(
            new AnimatedPoint(Targets.ROTATION, animTime -> new Vector3f(
                toRadians(55),
                Mth.abs(Mth.cos(animTime * toRadians(1440)) * toRadians(5)),
                0
            ))
        ))
        .addAnimation(ScarabModel.LEFT_MIDDLE_LEG, new AnimatedChannel(
            new AnimatedPoint(Targets.ROTATION, animTime -> new Vector3f(
                0,
                Mth.abs(Mth.cos(animTime * toRadians(1440)) * toRadians(5)),
                toRadians(67.5)
            )),
            new AnimatedPoint(Targets.POSITION, animTime -> new Vector3f(
                toRadians(-0.5),
                toRadians(1),
                0
            ))
        ))
        .addAnimation(ScarabModel.RIGHT_MIDDLE_LEG, new AnimatedChannel(
            new AnimatedPoint(Targets.ROTATION, animTime -> new Vector3f(
                0,
                Mth.abs(Mth.cos(animTime * toRadians(1440)) * toRadians(5)),
                toRadians(-67.5)
            )),
            new AnimatedPoint(Targets.POSITION, animTime -> new Vector3f(
                toRadians(-0.5),
                toRadians(1),
                0
            ))
        ))
        .addAnimation(ScarabModel.LEFT_BACK_LEG, new AnimatedChannel(
            new AnimatedPoint(Targets.ROTATION, animTime -> new Vector3f(
                0,
                Mth.abs(Mth.cos(animTime * toRadians(1440)) * toRadians(5)) + toRadians(37.5),
                toRadians(60)
            )),
            new AnimatedPoint(Targets.POSITION, animTime -> new Vector3f(
                toRadians(-0.5),
                toRadians(1),
                0
            ))
        ))
        .addAnimation(ScarabModel.RIGHT_BACK_LEG, new AnimatedChannel(
            new AnimatedPoint(Targets.ROTATION, animTime -> new Vector3f(
                0,
                Mth.abs(Mth.cos(animTime * toRadians(1440)) * toRadians(5)) + toRadians(-37.5),
                toRadians(-60)
            )),
            new AnimatedPoint(Targets.POSITION, animTime -> new Vector3f(
                toRadians(0.5),
                toRadians(1),
                0
            ))
        ))
        .build();

    public static final MathAnimator WALK = new MathAnimator.Builder()
        .addAnimation(ScarabModel.BODY, new AnimatedChannel(
            new AnimatedPoint(Targets.ROTATION, animTime -> new Vector3f(
                Mth.sin(animTime * toRadians(360)) * toRadians(2.5),
                0F,
                Mth.cos(animTime * toRadians(720)) * toRadians(5)
            )),
            new AnimatedPoint(Targets.POSITION, animTime -> new Vector3f(
                0F,
                Mth.abs(Mth.cos(animTime * toRadians(720)) * 2 / 3) + Mth.abs(Mth.sin(animTime * toRadians(720 - 45)) * 2 / 3) - 2.25F,
                0F
            ))
        ))
        .addAnimation(ScarabModel.RIGHT_ELYTRA, new AnimatedChannel(
            new AnimatedPoint(Targets.ROTATION, animTime -> new Vector3f(
                0F,
                -Mth.abs(Mth.cos(animTime * toRadians(720)) * toRadians(5)),
                0F
            ))
        ))
        .addAnimation(ScarabModel.LEFT_ELYTRA, new AnimatedChannel(
            new AnimatedPoint(Targets.ROTATION, animTime -> new Vector3f(
                0F,
                Mth.abs(Mth.cos(animTime * toRadians(720)) * toRadians(5)),
                0F
            ))
        ))
        .addAnimation(ScarabModel.LEFT_FRONT_LEG, new AnimatedChannel(
            new AnimatedPoint(Targets.ROTATION, animTime -> new Vector3f(
                -Mth.cos(animTime * toRadians(720)) * toRadians(10),
                Mth.sin(animTime * toRadians(720)) * toRadians(30 - 10),
                0F
            ))
        ))
        .addAnimation(ScarabModel.RIGHT_FRONT_LEG, new AnimatedChannel(
            new AnimatedPoint(Targets.ROTATION, animTime -> new Vector3f(
                Mth.cos(animTime * toRadians(720)) * toRadians(10),
                Mth.sin(animTime * toRadians(720)) * toRadians(30 + 10),
                0F
            ))
        ))
        .addAnimation(ScarabModel.LEFT_MIDDLE_LEG, new AnimatedChannel(
            new AnimatedPoint(Targets.ROTATION, animTime -> new Vector3f(
                -Mth.cos(animTime * toRadians(720)) * toRadians(10),
                -Mth.sin(animTime * toRadians(720)) * toRadians(30 + 10),
                0F
            ))
        ))
        .addAnimation(ScarabModel.RIGHT_MIDDLE_LEG, new AnimatedChannel(
            new AnimatedPoint(Targets.ROTATION, animTime -> new Vector3f(
                Mth.cos(animTime * toRadians(720)) * toRadians(10),
                -Mth.sin(animTime * toRadians(720)) * toRadians(30 - 10),
                0F
            ))
        ))
        .addAnimation(ScarabModel.LEFT_BACK_LEG, new AnimatedChannel(
            new AnimatedPoint(Targets.ROTATION, animTime -> new Vector3f(
                -Mth.cos(animTime * toRadians(720)) * toRadians(10),
                Mth.sin(animTime * toRadians(720)) * toRadians(20),
                0F
            ))
        ))
        .addAnimation(ScarabModel.RIGHT_BACK_LEG, new AnimatedChannel(
            new AnimatedPoint(Targets.ROTATION, animTime -> new Vector3f(
                Mth.cos(animTime * toRadians(720)) * toRadians(10),
                Mth.sin(animTime * toRadians(720)) * toRadians(20),
                0F
            ))
        ))
        .build();
}