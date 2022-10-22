package com.github.teamfusion.rottencreatures.common.entities;

import com.github.teamfusion.rottencreatures.common.entities.goal.FollowLeaderGoal;
import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;
import com.github.teamfusion.rottencreatures.common.registries.RCMobEffects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;

public class Zap extends Zombie {
    public Zap(EntityType<? extends Zombie> type, Level level) {
        super(type, level);
        this.xpReward = 5;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Zombie.createAttributes().add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.0D).add(Attributes.MAX_HEALTH, 22.0D).add(Attributes.FOLLOW_RANGE, 50.0D).add(Attributes.ATTACK_DAMAGE, 3.0D).add(Attributes.ARMOR, 2.0D);
    }

    /**
     * They should follow immortals and turn any nearby zombies into Zaps
     */
    @Override
    protected void addBehaviourGoals() {
        super.addBehaviourGoals();
        this.goalSelector.addGoal(1, new FollowLeaderGoal(this, Immortal.class, 1.25D));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Zombie.class, true) {
            @Override
            public boolean canUse() { return super.canUse() && this.target.getType() != RCEntityTypes.ZAP.get() && this.target.getType() != RCEntityTypes.IMMORTAL.get(); }
        });
    }

    /**
     * when hitting an entity, they'll apply channelled effect and turn zombies into zaps
     */
    @Override
    public boolean doHurtTarget(Entity entity) {
        boolean hurt = super.doHurtTarget(entity);
        if (hurt && this.getMainHandItem().isEmpty() && entity instanceof LivingEntity living) {
            float modifier = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
            living.addEffect(new MobEffectInstance(RCMobEffects.CHANNELLED.get(), 140 * (int)modifier), this);
            convertToZap(this, living);
        }

        return hurt;
    }

    /**
     * zaps will convert other zombies on collision as well
     */
    @Override
    protected void doPush(Entity entity) {
        super.doPush(entity);
        if (entity instanceof LivingEntity living) convertToZap(this, living);
    }

    /**
     * they are immune to sunlight! but not fireproof
     */
    @Override
    protected boolean isSunSensitive() {
        return false;
    }

    /**
     * they have resistance to thunderbolts, if they are struck by one they will heal!
     */
    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source == DamageSource.LIGHTNING_BOLT) {
            this.setHealth(this.getMaxHealth());
            return false;
        } else {
            return super.hurt(source, amount);
        }
    }

    public static void convertToZap(LivingEntity entity, LivingEntity target) {
        if (entity.level instanceof ServerLevel level && target instanceof Zombie zombie && zombie.getType() != RCEntityTypes.ZAP.get() && zombie.getType() != RCEntityTypes.IMMORTAL.get() && zombie.getType() != RCEntityTypes.DEAD_BEARD.get()) {
            Zap zap = zombie.convertTo(RCEntityTypes.ZAP.get(), true);
            zap.finalizeSpawn(level, level.getCurrentDifficultyAt(zap.blockPosition()), MobSpawnType.CONVERSION, null, null);
            if (!entity.isSilent()) level.levelEvent(null, 1026, entity.blockPosition(), 0);
        }
    }
}