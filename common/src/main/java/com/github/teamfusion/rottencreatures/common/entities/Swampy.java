package com.github.teamfusion.rottencreatures.common.entities;

import com.github.teamfusion.rottencreatures.data.RCBiomeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Random;

public class Swampy extends Zombie {
    public Swampy(EntityType<? extends Zombie> type, Level level) {
        super(type, level);
        this.xpReward = 4;
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
    }

    /**
     * will leap towards the target when attacking
     */
    @Override
    protected void addBehaviourGoals() {
        super.addBehaviourGoals();
        this.goalSelector.addGoal(2, new LeapAtTargetGoal(this, 0.4F));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Zombie.createAttributes().add(Attributes.MAX_HEALTH, 16.0D).add(Attributes.MOVEMENT_SPEED, 0.23D).add(Attributes.ATTACK_DAMAGE, 3.0D);
    }

    /**
     * when attacking, there's different behavior depending on the age
     * when is a baby it will explode and spawn a lingering cloud of poison
     *
     * otherwise checks if the entity is empty-handed and applies blindness and poison
     * the duration of the effects depend on the difficulty
     */
    @Override
    public boolean doHurtTarget(Entity entity) {
        boolean hurt = super.doHurtTarget(entity);
        if (hurt && this.getMainHandItem().isEmpty() && entity instanceof LivingEntity living) {
            float modifier = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
            living.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 140 * (int)modifier), this);
            living.addEffect(new MobEffectInstance(MobEffects.POISON, 140 * (int)modifier), this);
        }

        if (this.isBaby()) {
            this.kill();
        }

        return hurt;
    }

    /**
     * checks if the swampy is in water to apply a speed boost
     */
    @Override
    public void travel(Vec3 vec3) {
        this.setSpeed((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) * (this.isInWater() ? 1.5F : 1.0F));
        super.travel(vec3);
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
                    Vec3 pos = this.getBoundingBox().getCenter();
                    for (int i = 0; i < 40; i++) this.level.addParticle(ParticleTypes.POOF, pos.x, pos.y, pos.z, this.random.nextGaussian() * 0.2D, this.random.nextGaussian() * 0.2D, this.random.nextGaussian() * 0.2D);
                }
            }
        }
    }

    public static boolean checkSwampySpawnRules(EntityType<Swampy> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, Random random) {
        return checkMonsterSpawnRules(type, level, spawnType, pos, random) && (spawnType == MobSpawnType.SPAWNER || level.canSeeSky(pos));
    }

    /**
     * spawns a poison cloud that lasts for 10 seconds
     * if the swampy has an active effect it will also be applied in the lingering cloud
     */
    private void spawnLingeringCloud() {
        Collection<MobEffectInstance> effects = this.getActiveEffects();
        AreaEffectCloud cloud = new AreaEffectCloud(this.level, this.getX(), this.getY(), this.getZ());
        cloud.setRadius(2.5F);
        cloud.setRadiusOnUse(-0.5F);
        cloud.setWaitTime(10);
        cloud.setDuration(cloud.getDuration() / 2);
        cloud.setRadiusPerTick(-cloud.getRadius() / (float)cloud.getDuration());
        cloud.addEffect(new MobEffectInstance(MobEffects.POISON, 200));

        if (!effects.isEmpty()) {
            for(MobEffectInstance effect : effects) {
                cloud.addEffect(new MobEffectInstance(effect));
            }
        }

        this.level.addFreshEntity(cloud);
    }
}