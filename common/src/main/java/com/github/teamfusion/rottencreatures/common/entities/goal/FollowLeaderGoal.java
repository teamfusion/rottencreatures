package com.github.teamfusion.rottencreatures.common.entities.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;

public class FollowLeaderGoal extends Goal {
    private final PathfinderMob entity;
    private LivingEntity leader;
    private final Class<? extends LivingEntity> leaderType;
    private final double speedModifier;
    private int timeToRecalculatePath;

    public FollowLeaderGoal(PathfinderMob entity, Class<? extends LivingEntity> leaderType, double speedModifier) {
        this.entity = entity;
        this.leaderType = leaderType;
        this.speedModifier = speedModifier;
    }

    @Override
    public boolean canUse() {
        List<? extends LivingEntity> entities = this.entity.level.getEntitiesOfClass(this.leaderType, this.entity.getBoundingBox().inflate(8.0D, 4.0D, 8.0D));
        LivingEntity leader = null;
        double maxDistance = Double.MAX_VALUE;

        for (LivingEntity entity : entities) {
            double distance = this.entity.distanceToSqr(entity);
            if (distance <= maxDistance) {
                maxDistance = distance;
                leader = entity;
            }
        }

        if (leader == null) {
            return false;
        } else if (maxDistance < 9.0D) {
            return false;
        } else {
            this.leader = leader;
        }

        return false;
    }

    @Override
    public boolean canContinueToUse() {
        if (!this.leader.isAlive()) {
            return false;
        } else {
            double distance = this.entity.distanceTo(this.leader);
            return !(distance < 9.0D) && !(distance > 256.0D);
        }
    }

    @Override
    public void start() {
        this.timeToRecalculatePath = 0;
    }

    @Override
    public void stop() {
        this.leader = null;
    }

    @Override
    public void tick() {
        if (--this.timeToRecalculatePath <= 0) {
            this.timeToRecalculatePath = this.adjustedTickDelay(10);
            this.entity.getNavigation().moveTo(this.leader, this.speedModifier);
        }
    }
}