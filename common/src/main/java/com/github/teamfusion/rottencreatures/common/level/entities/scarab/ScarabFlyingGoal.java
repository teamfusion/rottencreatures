package com.github.teamfusion.rottencreatures.common.level.entities.scarab;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class ScarabFlyingGoal extends Goal {
    private final Scarab scarab;
    private int circlingTime = 0;
    private boolean clockwise = false;

    public ScarabFlyingGoal(Scarab scarab) {
        this.scarab = scarab;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return this.scarab.isFlying() && this.scarab.getTarget() != null;
    }

    @Override
    public boolean canContinueToUse() {
        return this.scarab.isFlying() && this.scarab.getTarget() != null && this.scarab.getTarget().isAlive();
    }

    @Override
    public void start() {
        this.circlingTime = 0;
        this.clockwise = this.scarab.getRandom().nextBoolean();
    }

    @Override
    public void tick() {
        LivingEntity target = this.scarab.getTarget();
        if (target == null) return;

        circlingTime++;

        // Every 60 ticks (3 seconds), decide if we should swoop in to attack
        if (circlingTime % 60 == 0 && this.scarab.getRandom().nextInt(4) == 0) {
            // Swoop attack
            this.scarab.getMoveControl().setWantedPosition(
                target.getX(),
                target.getY(),
                target.getZ(),
                1.5D
            );
        } else {
            // Circle around target
            double circleRadius = 4.0D;
            double angle = this.circlingTime * 0.1D * (clockwise ? 1 : -1);
            double offsetX = Math.cos(angle) * circleRadius;
            double offsetZ = Math.sin(angle) * circleRadius;

            Vec3 circlePos = new Vec3(
                target.getX() + offsetX,
                target.getY() + 2.0D + this.scarab.getRandom().nextDouble(),
                target.getZ() + offsetZ
            );

            this.scarab.getMoveControl().setWantedPosition(
                circlePos.x,
                circlePos.y,
                circlePos.z,
                1.0D
            );
        }
    }
}