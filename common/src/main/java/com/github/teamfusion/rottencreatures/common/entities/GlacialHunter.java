package com.github.teamfusion.rottencreatures.common.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/**
 * Behavior:
 * Will only spawn near igloos or snowy structures.
 *
 * Concepts:
 * May throw snowballs
 * If it has 10HP or lower, he'll attack with a spear if he doesn't have one
 * If the snowball hit the entity, it applies slowness
 *
 * TODO: set wolves as hostile
 */
public class GlacialHunter extends Zombie {
    public GlacialHunter(EntityType<? extends Zombie> type, Level level) {
        super(type, level);
        this.xpReward = 6;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Zombie.createAttributes().add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.0D).add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.MOVEMENT_SPEED, 0.2D).add(Attributes.ATTACK_DAMAGE, 3.0D).add(Attributes.ARMOR, 4.0D);
    }

    @Override
    protected void addBehaviourGoals() {
        super.addBehaviourGoals();
        this.targetSelector.addGoal(1, new ZombieAttackGoal(this, 1.0D, false) {
            @Override
            protected double getAttackReachSqr(LivingEntity living) {
                double reach = this.mob.getBbWidth() * 3.0F * this.mob.getBbWidth() * 3.0F + living.getBbWidth();
                return GlacialHunter.this.hasSpear() ? reach : super.getAttackReachSqr(living);
            }
        });
    }

    /**
     * increase damage range
     */
    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (this.hasSpear()) {
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(6.0D);
            this.getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue(1.5D);
        }
    }

    @Override
    protected void populateDefaultEquipmentSlots(DifficultyInstance difficulty) {
        super.populateDefaultEquipmentSlots(difficulty);
        if (this.level.getRandom().nextFloat() <= 0.4F) {
            this.setSpear();
        }
    }

    private boolean hasSpear() {
        return this.getItemBySlot(EquipmentSlot.MAINHAND).is(Items.TRIDENT);
    }

    private void setSpear() {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.TRIDENT));
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        boolean hurtTarget = super.doHurtTarget(entity);
        if (hurtTarget && this.getMainHandItem().isEmpty() && entity instanceof LivingEntity living) {
            float modifier = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
            living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 140 * (int)modifier), this);
        }

        return hurtTarget;
    }

    @Override
    public boolean isBaby() {
        return false;
    }

    @Override
    public void travel(Vec3 vec3) {
        this.setSpeed((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) * (this.hasSpear() ? 1.5F : 1.0F));
        super.travel(vec3);
    }

    @Nullable @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData groupData, @Nullable CompoundTag tag) {
        Wolf wolf = EntityType.WOLF.create(this.level);
        if (level.getRandom().nextFloat() <= 0.3F && wolf != null) {
            wolf.moveTo(this.blockPosition().offset(-2 + this.level.random.nextInt(3), 1, -2 + this.level.random.nextInt(3)), 0.0F, 0.0F);
            wolf.setRemainingPersistentAngerTime(900);
            wolf.setOwnerUUID(this.getUUID());
            wolf.isAlliedTo(this);
            level.addFreshEntity(wolf);
        }


        return super.finalizeSpawn(level, difficulty, spawnType, groupData, tag);
    }
}