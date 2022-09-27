package com.github.teamfusion.rottencreatures.common.entities;

import com.github.teamfusion.rottencreatures.common.registries.RCMobEffects;
import net.minecraft.core.BlockPos;
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

import java.util.Random;

/**
 * - applies Blindness and Poison at the same time when he hits a player or entity (duration depends on the difficulty) //
 * - on death, he has a 80% chance to drop a small cloud effect area with poison //
 * - if a baby attacks an entity, he'll explode and create a small cloud effect with poison //
 * - can swim slightly faster in water
 *
 * - if it's attacking an entity, it does small jumps like the spider //
 */
public class Swampy extends Zombie {
    public Swampy(EntityType<? extends Zombie> type, Level level) {
        super(type, level);
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
//            this.kill();
            RCMobEffects.createAreaEffectCloud(this.level, this.getX(), this.getY(), this.getZ(), MobEffects.POISON, 2.5F, 3);
        }

        return hurt;
    }

    @Override
    protected void tickDeath() {
        super.tickDeath();
        if (!this.level.isClientSide && this.deathTime == 20 && this.random.nextFloat() < 0.8F) {
            RCMobEffects.createAreaEffectCloud(this.level, this.getX(), this.getY(), this.getZ(), MobEffects.POISON, 2.5F, 6);
        }
    }

    public static boolean checkSwampySpawnRules(EntityType<Swampy> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, Random random) {
        return checkMonsterSpawnRules(type, level, spawnType, pos, random) && (spawnType == MobSpawnType.SPAWN_EGG || level.canSeeSky(pos));
    }
}