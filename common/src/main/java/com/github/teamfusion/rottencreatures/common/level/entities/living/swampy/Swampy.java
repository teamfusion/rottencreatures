package com.github.teamfusion.rottencreatures.common.level.entities.living.swampy;

import com.github.teamfusion.rottencreatures.client.registries.RCSoundEvents;
import com.github.teamfusion.rottencreatures.common.registries.RCBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

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
            .add(Attributes.MAX_HEALTH, 16.0)
            .add(Attributes.MOVEMENT_SPEED, 0.23)
            .add(Attributes.ATTACK_DAMAGE, 3.0);
    }

    /**
     * when attacking, there's different behavior depending on the age
     * when is a baby it will explode and spawn a lingering cloud of poison
     * <br>
     * otherwise checks if the entity is empty-handed and applies blindness and poison
     * the duration of the effects depend on the difficulty
     */
    @Override
    public boolean doHurtTarget(Entity entity) {
        boolean hurt = super.doHurtTarget(entity);

        // Check if the zombie can hurt the target, and it's not holding any item
        if (hurt && this.getMainHandItem().isEmpty()) {
            // Apply both Blindness and Poison effect to the entity, the duration may vary depending on the difficulty.
            if (entity instanceof LivingEntity living) {
                float modifier = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();

                living.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 140 * (int) modifier), this);
                living.addEffect(new MobEffectInstance(MobEffects.POISON, 140 * (int) modifier), this);
            }
        }

        // If the baby hits the target, it dies
        if (hurt && this.isBaby()) {
            this.kill();
        }

        return hurt;
    }

    /**
     * checks if the swampy is in water to apply a speed boost
     */
    @Override
    public void travel(Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWaterOrBubble()) {
            this.moveRelative(0.025F, travelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9F));
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
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
                    this.level.broadcastEntityEvent(this, (byte) 14);
                }
            }
        }
    }

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
            double motionX = this.random.nextGaussian() * 0.2;
            double motionY = this.random.nextGaussian() * 0.2;
            double motionZ = this.random.nextGaussian() * 0.2;

            // Spawn particles in a spherical pattern
            for (int j = 0; j < 20; j++) {
                double xzAngle = this.random.nextDouble() * Math.PI * 2.0;
                double yAngle = this.random.nextDouble() * Math.PI / 2.0;

                double motionXDir = Math.cos(xzAngle) * Math.sin(yAngle);
                double motionYDir = Math.cos(yAngle);
                double motionZDir = Math.sin(xzAngle) * Math.sin(yAngle);

                this.level.addParticle(
                    ParticleTypes.SQUID_INK,
                    posX,
                    posY,
                    posZ,
                    motionX + motionXDir * 0.1,
                    motionY + motionYDir * 0.1,
                    motionZ + motionZDir * 0.1
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

    @Override
    public boolean canBeAffected(MobEffectInstance instance) {
        return instance.getEffect() != MobEffects.POISON && super.canBeAffected(instance);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return RCSoundEvents.SWAMPY_IDLE.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return RCSoundEvents.SWAMPY_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return RCSoundEvents.SWAMPY_DEATH.get();
    }

    @Override
    protected ItemStack getSkull() {
        return new ItemStack(RCBlocks.SWAMPY_HEAD.get());
    }

    public static boolean checkSwampySpawnRules(EntityType<Swampy> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return checkMonsterSpawnRules(type, level, spawnType, pos, random) && (spawnType == MobSpawnType.SPAWNER || level.canSeeSky(pos));
    }
}