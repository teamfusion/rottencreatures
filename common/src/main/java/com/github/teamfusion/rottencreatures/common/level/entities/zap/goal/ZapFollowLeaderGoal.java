package com.github.teamfusion.rottencreatures.common.level.entities.zap.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;

public class ZapFollowLeaderGoal extends Goal {
    private final PathfinderMob entity;
    private LivingEntity leader;
    private final Class<? extends LivingEntity> leaderType;
    private final double speedModifier;
    private int timeToRecalculatePath;

    public ZapFollowLeaderGoal(PathfinderMob entity, Class<? extends LivingEntity> leaderType, double speedModifier) {
        this.entity = entity;
        this.leaderType = leaderType;
        this.speedModifier = speedModifier;
    }

    @Override
    public boolean canUse() {
        List<? extends LivingEntity> entities = this.entity.level().getEntitiesOfClass(this.leaderType, this.entity.getBoundingBox().inflate(8.0, 4.0, 8.0));
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
        } else if (maxDistance < 9.0) {
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
            return !(distance < 9.0) && !(distance > 256.0);
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