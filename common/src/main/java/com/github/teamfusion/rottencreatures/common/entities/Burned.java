package com.github.teamfusion.rottencreatures.common.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

public class Burned extends Zombie {
    private static final EntityDataAccessor<Boolean> DATA_IS_OBSIDIAN = SynchedEntityData.defineId(Burned.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_IS_CRAZY = SynchedEntityData.defineId(Burned.class, EntityDataSerializers.BOOLEAN);

    public Burned(EntityType<? extends Zombie> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(BlockPathTypes.LAVA, 8.0F);
    }

    //Todo: make them attack frostbittens
    @Override
    protected void addBehaviourGoals() {
        super.addBehaviourGoals();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Zombie.createAttributes().add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.0D).add(Attributes.MOVEMENT_SPEED, 0.22F).add(Attributes.ATTACK_DAMAGE, 4.0D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_IS_OBSIDIAN, false);
        this.getEntityData().define(DATA_IS_CRAZY, false);
    }

    @Override
    protected boolean convertsInWater() {
        return false;
    }

    //TODO: check if low health and apply:
    // - Knockback Resistance
    // - Extra Movement Speed
    // - Extra Attack Damage
    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
    }

    @Override
    public void tick() {
        if (!this.level.isClientSide && this.isAlive() && !this.isNoAi()) {
            if (this.isInWaterOrBubble()) this.setObsidian(true);

            if (this.isInLava() && this.isObsidian()) this.setObsidian(false);
        }
        super.tick();
    }

    //Todo: apply custom sounds
    @Override
    protected SoundEvent getAmbientSound() {
        return super.getAmbientSound();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return super.getHurtSound(damageSource);
    }

    @Override
    protected SoundEvent getDeathSound() {
        return super.getDeathSound();
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        boolean hurtTarget = super.doHurtTarget(entity);
        if (hurtTarget && this.getMainHandItem().isEmpty() && entity instanceof LivingEntity living) {
            living.setSecondsOnFire(5);
        }

        return hurtTarget;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("IsObsidian", this.isObsidian());
        tag.putBoolean("IsCrazy", this.isCrazy());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setObsidian(tag.getBoolean("IsObsidian"));
        this.setCrazy(tag.getBoolean("IsCrazy"));
    }

    public boolean isObsidian() {
        return this.getEntityData().get(DATA_IS_OBSIDIAN);
    }

    //Todo: apply attribute modifications when set to obsidian
    public void setObsidian(boolean obsidian) {
        this.getEntityData().set(DATA_IS_OBSIDIAN, obsidian);
    }

    public boolean isCrazy() {
        return this.getEntityData().get(DATA_IS_CRAZY);
    }

    //Todo: apply attribute modifications when set to crazy
    public void setCrazy(boolean crazy) {
        this.getEntityData().set(DATA_IS_CRAZY, crazy);
    }
}