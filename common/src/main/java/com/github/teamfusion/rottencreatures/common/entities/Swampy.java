package com.github.teamfusion.rottencreatures.common.entities;

import com.github.teamfusion.rottencreatures.common.registries.RCMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

/**
 * - applies Blindness and Poison at the same time when he hits a player or entity (duration depends on the difficulty) //
 * - on death, he has a 80% chance to drop a small cloud effect area with poison //
 * - if a baby attacks an entity, he'll explode and create a small cloud effect with poison //
 * - can swim slightly faster in water //
 *
 * - if it's attacking an entity, it does small jumps like the spider //
 */
public class Swampy extends Zombie {
    public Swampy(EntityType<? extends Zombie> type, Level level) {
        super(type, level);
        this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
    }

    @Override
    protected void addBehaviourGoals() {
        super.addBehaviourGoals();
        this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Zombie.createAttributes().add(Attributes.MAX_HEALTH, 16.0D).add(Attributes.MOVEMENT_SPEED, 0.23D).add(Attributes.ATTACK_DAMAGE, 3.0D);
    }

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
            RCMobEffects.createAreaEffectCloud(this.level, this.getX(), this.getY(), this.getZ(), MobEffects.POISON, 2.5F, 3);
            this.level.addParticle(ParticleTypes.CLOUD, this.getX(), this.getRandomY(), this.getZ(), this.random.nextDouble(-0.15, 0.15), 0.0D, this.random.nextDouble(-0.15, 0.15));
        }

        return hurt;
    }

    @Override
    public void travel(Vec3 vec3) {
        this.setSpeed((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) * (this.isInWater() ? 1.5F : 1.0F));
        super.travel(vec3);
    }

    @Override
    public void die(DamageSource source) {
        super.die(source);
        if (!this.level.isClientSide && this.random.nextInt(10) < 8) {
            RCMobEffects.createAreaEffectCloud(this.level, this.getX(), this.getY(), this.getZ(), MobEffects.POISON, 2.5F, 6);
        }
    }

    public static boolean checkSwampySpawnRules(EntityType<Swampy> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, Random random) {
        return checkMonsterSpawnRules(type, level, spawnType, pos, random) && (spawnType == MobSpawnType.SPAWN_EGG || level.canSeeSky(pos));
    }
}