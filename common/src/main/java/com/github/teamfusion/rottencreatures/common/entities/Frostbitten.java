package com.github.teamfusion.rottencreatures.common.entities;

import com.github.teamfusion.rottencreatures.client.registries.RCSoundEvents;
import com.github.teamfusion.rottencreatures.common.registries.RCMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.enchantment.FrostWalkerEnchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

import java.util.List;
import java.util.Random;

public class Frostbitten extends Zombie {
    // Entity Events
    private static final byte SNOW_EXPLOSION_ID = 14;

    public Frostbitten(EntityType<? extends Zombie> type, Level level) {
        super(type, level);
        this.xpReward = 5;
        this.setPathfindingMalus(BlockPathTypes.POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_POWDER_SNOW, -1.0F);
    }

    @Override
    protected void addBehaviourGoals() {
        super.addBehaviourGoals();
        // Frostbitten Zombies and Burned Zombies are enemies and will attack each other if possible.
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Burned.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Zombie.createAttributes()
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.0D)
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.ARMOR, 2.0D);
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        boolean hurt = super.doHurtTarget(entity);

        // Check if the zombie can hurt the target, and it's not holding any item.
        if (hurt && this.getMainHandItem().isEmpty()) {
            // Check if the entity can freeze, then apply the freezing effect, the duration may vary depending on the difficulty.
            if (entity instanceof LivingEntity living && living.canFreeze()) {
                float modifier = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
                living.addEffect(new MobEffectInstance(RCMobEffects.FREEZE.get(), 140 * (int)modifier), this);
            }
        }

        return hurt;
    }

    @Override
    protected void onChangedBlock(BlockPos pos) {
        super.onChangedBlock(pos);
        // Once a Frostbitten walks nearby water, it freezes it temporally.
        FrostWalkerEnchantment.onEntityMoved(this, this.level, pos, 0);
    }

    @Override
    protected SoundEvent getDeathSound() {
        return RCSoundEvents.FROSTBITTEN_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return RCSoundEvents.FROSTBITTEN_HURT.get();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return RCSoundEvents.FROSTBITTEN_AMBIENT.get();
    }

    @Override
    public void die(DamageSource source) {
        if (this.isBaby()) {
            // Apply a Snow Explosion once a baby died.
            this.level.broadcastEntityEvent(this, SNOW_EXPLOSION_ID);

            if (!this.level.isClientSide) {
                // Check for any nearby entities in a radius of 5 blocks from the Frostbitten
                List<LivingEntity> entities = this.level.getEntitiesOfClass(
                        LivingEntity.class,
                        this.getBoundingBox().inflate(5.0D),
                        EntitySelector.NO_CREATIVE_OR_SPECTATOR
                );

                for (LivingEntity entity : entities) {
                    // Check if the entity can freeze, and apply the Freezing effect, the durability may vary depending on the difficulty.
                    if (entity.canFreeze()) {
                        float modifier = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
                        entity.addEffect(new MobEffectInstance(RCMobEffects.FREEZE.get(), 100 * (int)modifier), this);
                    }
                }
            }
        }

        super.die(source);
    }

    @Override
    public void handleEntityEvent(byte id) {
        super.handleEntityEvent(id);
        if (id == SNOW_EXPLOSION_ID) {
            this.createSnowExplosion();
        }
    }

    private void createSnowExplosion() {
        double posX = this.getX();
        double posY = this.getY();
        double posZ = this.getZ();

        for (int i = 0; i < 100; i++) {
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

                this.level.addParticle(
                        ParticleTypes.SNOWFLAKE,
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

    public static boolean checkFrostbittenSpawnRules(EntityType<Frostbitten> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, Random random) {
        return checkMonsterSpawnRules(type, level, spawnType, pos, random) && (spawnType == MobSpawnType.SPAWNER || level.canSeeSky(pos));
    }
}