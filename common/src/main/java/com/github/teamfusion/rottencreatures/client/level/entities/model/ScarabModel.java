package com.github.teamfusion.rottencreatures.client.level.entities.model;

import com.blackgear.platform.client.animator.MathAnimator;
import com.github.teamfusion.rottencreatures.client.level.entities.animation.LegacyScarabAnimations;
import com.github.teamfusion.rottencreatures.common.level.entities.scarab.Scarab;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.phys.Vec2;

public class ScarabModel<T extends Scarab> extends HierarchicalModel<T> {
    public static final String BODY = "body";
    public static final String RIGHT_ELYTRA = "right_elytra";
    public static final String LEFT_ELYTRA = "left_elytra";
    public static final String RIGHT_WING = "right_wing";
    public static final String LEFT_WING = "left_wing";
    public static final String LEFT_FRONT_LEG = "left_front_leg";
    public static final String RIGHT_FRONT_LEG = "right_front_leg";
    public static final String LEFT_MIDDLE_LEG = "left_middle_leg";
    public static final String RIGHT_MIDDLE_LEG = "right_middle_leg";
    public static final String LEFT_BACK_LEG = "left_back_leg";
    public static final String RIGHT_BACK_LEG = "right_back_leg";

    protected final ModelPart root;
    protected final ModelPart body;
    protected final ModelPart elytraRight;
    protected final ModelPart elytraLeft;
    protected final ModelPart wingRight;
    protected final ModelPart wingLeft;
    protected final ModelPart legFrontLeft;
    protected final ModelPart legFrontRight;
    protected final ModelPart legMiddleLeft;
    protected final ModelPart legMiddleRight;
    protected final ModelPart legBackLeft;
    protected final ModelPart legBackRight;

    public ScarabModel(ModelPart root) {
        this.root = root;
        this.body = this.root.getChild(BODY);
        this.elytraRight = this.body.getChild(RIGHT_ELYTRA);
        this.elytraLeft = this.body.getChild(LEFT_ELYTRA);
        this.wingRight = this.body.getChild(RIGHT_WING);
        this.wingLeft = this.body.getChild(LEFT_WING);
        this.legFrontLeft = this.body.getChild(LEFT_FRONT_LEG);
        this.legFrontRight = this.body.getChild(RIGHT_FRONT_LEG);
        this.legMiddleLeft = this.body.getChild(LEFT_MIDDLE_LEG);
        this.legMiddleRight = this.body.getChild(RIGHT_MIDDLE_LEG);
        this.legBackLeft = this.body.getChild(LEFT_BACK_LEG);
        this.legBackRight = this.body.getChild(RIGHT_BACK_LEG);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition body = root.addOrReplaceChild(
            BODY, 
            CubeListBuilder.create()
                .texOffs(0, 0)
                .addBox(-3.5F, -3.0F, -6.0F, 7.0F, 3.0F, 4.0F)
                .texOffs(-2, 24)
                .addBox(-2.5F, -1.0F, -8.0F, 5.0F, 0.0F, 2.0F)
                .texOffs(0, 16)
                .addBox(-3.0F, -2.5F, -2.0F, 6.0F, 2.0F, 5.0F),
            PartPose.offset(0.0F, 24.0F, 1.0F)
        );

        body.addOrReplaceChild(
            RIGHT_ELYTRA,
            CubeListBuilder.create()
                .texOffs(0, 7)
                .addBox(-2.0F, 0.0F, 0.0F, 4.0F, 3.0F, 6.0F),
            PartPose.offset(-1.5F, -3.0F, -2.0F)
        );
        body.addOrReplaceChild(
            LEFT_ELYTRA,
            CubeListBuilder.create()
                .texOffs(21, 7)
                .addBox(-2.0F, 0.0F, 0.0F, 4.0F, 3.0F, 6.0F),
            PartPose.offset(1.5F, -3.0F, -2.0F)
        );
        body.addOrReplaceChild(
            RIGHT_WING,
            CubeListBuilder.create()
                .texOffs(17, 0)
                .addBox(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 5.0F),
            PartPose.offset(1.5F, -2.75F, -2.0F)
        );
        body.addOrReplaceChild(
            LEFT_WING,
            CubeListBuilder.create()
                .texOffs(23, 0)
                .addBox(-1.5F, 0.0F, 0.0F, 3.0F, 0.0F, 5.0F),
            PartPose.offset(-1.5F, -2.75F, -2.0F)
        );
        body.addOrReplaceChild(
            LEFT_FRONT_LEG,
            CubeListBuilder.create()
                .texOffs(1, 27)
                .addBox(-0.5F, 0.0F, -4.5F, 3.0F, 0.0F, 5.0F),
            PartPose.offset(4.0F, -0.1F, -2.5F)
        );
        body.addOrReplaceChild(
            RIGHT_FRONT_LEG,
            CubeListBuilder.create()
                .texOffs(-5, 27)
                .addBox(-2.5F, 0.0F, -4.5F, 3.0F, 0.0F, 5.0F),
            PartPose.offset(-4.0F, -0.1F, -2.5F)
        );
        body.addOrReplaceChild(
            LEFT_MIDDLE_LEG,
            CubeListBuilder.create()
                .texOffs(5, 33).
                addBox(0.0F, 0.0F, -0.5F, 4.0F, 0.0F, 3.0F),
            PartPose.offset(3.5F, -0.1F, -0.5F)
        );
        body.addOrReplaceChild(
            RIGHT_MIDDLE_LEG,
            CubeListBuilder.create()
                .texOffs(-3, 33)
                .addBox(-4.0F, 0.0F, -0.5F, 4.0F, 0.0F, 3.0F),
            PartPose.offset(-3.5F, -0.1F, -0.5F)
        );
        body.addOrReplaceChild(
            LEFT_BACK_LEG,
            CubeListBuilder.create()
                .texOffs(-1, 37)
                .addBox(0.0F, 0.0F, -0.5F, 2.0F, 0.0F, 5.0F),
            PartPose.offset(3.5F, -0.1F, 1.5F)
        );
        body.addOrReplaceChild(
            RIGHT_BACK_LEG,
            CubeListBuilder.create()
                .texOffs(-5, 37)
                .addBox(-2.0F, 0.0F, -0.5F, 2.0F, 0.0F, 5.0F),
            PartPose.offset(-3.5F, -0.1F, 1.5F)
        );

        return LayerDefinition.create(mesh, 41, 42);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        boolean isMoving = new Vec2((float) entity.getDeltaMovement().x, (float) entity.getDeltaMovement().z).length() > 0.01;
        boolean isInAir = !entity.onGround() && !entity.isInWaterOrBubble();

        if (isInAir) {
            MathAnimator.animate(this, animationProgress, LegacyScarabAnimations.FLYING);
        } else if (isMoving) {
            MathAnimator.animate(this, animationProgress, LegacyScarabAnimations.WALK);
        }
    }
}