package com.github.teamfusion.rottencreatures.common.entities;

import com.github.teamfusion.rottencreatures.client.registries.RCSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class Swampy extends Zombie {
    public Swampy(EntityType<? extends Zombie> type, Level level) {
        super(type, level);
        this.xpReward = 4;
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
    }

    @Override
    protected void addBehaviourGoals() {
        super.addBehaviourGoals();
        // Swampy Zombies will try to leap towards the target while attacking.
        this.goalSelector.addGoal(2, new LeapAtTargetGoal(this, 0.4F));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Zombie.createAttributes()
                .add(Attributes.MAX_HEALTH, 16.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.23D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D);
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        boolean hurt = super.doHurtTarget(entity);

        // Check if the zombie can hurt the target, and it's not holding any item.
        if (hurt && this.getMainHandItem().isEmpty()) {
            // Apply both Blindness and Poison effect to the entity, the duration may vary depending on the difficulty.
            if (entity instanceof LivingEntity living) {
                float modifier = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();

                living.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 140 * (int)modifier), this);
                living.addEffect(new MobEffectInstance(MobEffects.POISON, 140 * (int)modifier), this);
            }
        }

        // If a baby hits the target, it dies
        if (hurt && this.isBaby()) {
            this.kill();
        }

        return hurt;
    }

    /**
     * checks if the swampy is in water to apply a speed boost
     */
    @Override
    public void travel(Vec3 vec3) {
        if (this.isEffectiveAi() && this.isInWaterOrBubble()) {
            this.moveRelative(0.025F, vec3);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9F));
        } else {
            super.travel(vec3);
        }
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return RCSoundEvents.SWAMPY_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return RCSoundEvents.SWAMPY_HURT.get();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return RCSoundEvents.SWAMPY_AMBIENT.get();
    }

    /**
     * on death, it has a 50% chance to spawn a lingering cloud of poison
     */
    @Override
    public void die(DamageSource source) {
        super.die(source);
        if (!this.level.isClientSide) {
            if (this.random.nextFloat() <= 0.8F || this.isBaby()) {
                this.spawnLingeringCloud();
                if (this.isBaby()) {
                    this.level.broadcastEntityEvent(this, (byte)14);
                }
            }
        }
    }

    /**
     * spawns a poison cloud that lasts for 10 seconds
     * if the swampy has an active effect it will also be applied in the lingering cloud
     */
    private void spawnLingeringCloud() {
        AreaEffectCloud cloud = new AreaEffectCloud(this.level, this.getX(), this.getY(), this.getZ());
        cloud.setRadius(2.5F);
        cloud.setRadiusOnUse(-0.5F);
        cloud.setWaitTime(10);
        cloud.setDuration(cloud.getDuration() / 2);
        cloud.setRadiusPerTick(-cloud.getRadius() / (float)cloud.getDuration());
        cloud.addEffect(new MobEffectInstance(MobEffects.POISON, 200));

        this.level.addFreshEntity(cloud);
    }

    @Override
    public void handleEntityEvent(byte id) {
        super.handleEntityEvent(id);
        if (id == 14) {
            this.createPoisonExplosion();
        }
    }

    private void createPoisonExplosion() {
        double posX = this.getX();
        double posY = this.getY();
        double posZ = this.getZ();

        for (int i = 0; i < 10; i++) {
            // Calculate random values for motion in each direction
            double motionX = this.random.nextGaussian() * 0.2D;
            double motionY = this.random.nextGaussian() * 0.2D;
            double motionZ = this.random.nextGaussian() * 0.2D;

            // Spawn particles in a spherical pattern
            for (int j = 0; j < 20; j++) {
                double xzAngle = this.random.nextDouble() * Math.PI * 2.0D;
                double yAngle = this.random.nextDouble() * Math.PI / 2.0D;

                double motionXDir = Math.cos(xzAngle) * Math.sin(yAngle);
                double motionYDir = Math.cos(yAngle);
                double motionZDir = Math.sin(xzAngle) * Math.sin(yAngle);

                // TODO: create new particle for swampy explosion
                this.level.addParticle(
                        ParticleTypes.SQUID_INK,
                        posX,
                        posY,
                        posZ,
                        motionX + motionXDir * 0.1D,
                        motionY + motionYDir * 0.1D,
                        motionZ + motionZDir * 0.1D
                );
            }
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        // Spawn dripping particles.
        if (this.random.nextInt(10) == 0) {
            this.level.addParticle(
                    this.random.nextBoolean()
                            ? new BlockParticleOption(ParticleTypes.FALLING_DUST, Blocks.BROWN_CONCRETE_POWDER.defaultBlockState())
                            : new BlockParticleOption(ParticleTypes.FALLING_DUST, Blocks.GREEN_CONCRETE_POWDER.defaultBlockState()),
                    this.getRandomX(0.5D),
                    this.getRandomY() - 0.5D,
                    this.getRandomZ(0.5D),
                    0.0D,
                    0.0D,
                    0.0D
            );
        }
    }

    public static boolean checkSwampySpawnRules(EntityType<Swampy> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, Random random) {
        return checkMonsterSpawnRules(type, level, spawnType, pos, random) && (spawnType == MobSpawnType.SPAWNER || level.canSeeSky(pos));
    }
}