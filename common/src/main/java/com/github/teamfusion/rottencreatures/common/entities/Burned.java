package com.github.teamfusion.rottencreatures.common.entities;

import com.github.teamfusion.rottencreatures.mixin.access.EntityAccessor;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Burned extends Zombie {
    private static final AttributeModifier CRAZY_MODIFIER = new AttributeModifier(UUID.fromString("a0ffa7a6-1210-466a-a9a1-31909417a99e"), "Crazy attribute boost", 1.0F, AttributeModifier.Operation.MULTIPLY_BASE);
    private static final AttributeModifier OBSIDIAN_MODIFIER = new AttributeModifier(UUID.fromString("cf2ce4af-4807-4896-aaad-1c077a87e9bf"), "Obsidian attribute boost", 0.75F, AttributeModifier.Operation.MULTIPLY_BASE);
    private static final EntityDataAccessor<Boolean> DATA_IS_OBSIDIAN = SynchedEntityData.defineId(Burned.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_IS_CRAZY = SynchedEntityData.defineId(Burned.class, EntityDataSerializers.BOOLEAN);

    public Burned(EntityType<? extends Zombie> type, Level level) {
        super(type, level);
        this.setPathfindingMalus(BlockPathTypes.LAVA, 8.0F);
    }

    //Todo: make them attack frostbittens
    @Override
    protected void addBehaviourGoals() {
        super.addBehaviourGoals();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Zombie.createAttributes().add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.0D).add(Attributes.MOVEMENT_SPEED, 0.22D).add(Attributes.ATTACK_DAMAGE, 4.0D);
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

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (this.getHealth() <= 10.0D) this.setCrazy(true);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.isCrazy() && !this.isObsidian()) this.level.addParticle(ParticleTypes.LAVA, this.getRandomX(0.1D), this.getRandomY(), this.getRandomZ(0.1D), 0.0D, 0.0D, 0.0D);
    }

    @Override
    public void tick() {
        if (!this.level.isClientSide && this.isAlive() && !this.isNoAi()) {
            if (this.isInWaterOrBubble() || this.isInPowderSnow) this.setObsidian(true);

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
        if (hurtTarget && this.getMainHandItem().isEmpty() && entity instanceof LivingEntity living && !this.isObsidian()) {
//            living.setSecondsOnFire(5);
        }

        return hurtTarget;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return (!(source.getDirectEntity() instanceof AbstractArrow) || !this.isObsidian()) && super.hurt(source, amount);
    }

    @Override
    public boolean isSensitiveToWater() {
        return !this.isObsidian() && !this.isInWaterOrBubble();
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

    public void setObsidian(boolean obsidian) {
        this.getEntityData().set(DATA_IS_OBSIDIAN, obsidian);
        if (!this.level.isClientSide) {
            List<AttributeInstance> instances = new ArrayList<>();
            instances.add(this.getAttribute(Attributes.ATTACK_DAMAGE));
            instances.add(this.getAttribute(Attributes.KNOCKBACK_RESISTANCE));
            for (AttributeInstance instance : instances) {
                instance.removeModifier(OBSIDIAN_MODIFIER);
                if (obsidian) instance.addTransientModifier(OBSIDIAN_MODIFIER);
            }
        }
    }

    public boolean isCrazy() {
        return this.getEntityData().get(DATA_IS_CRAZY);
    }

    public void setCrazy(boolean crazy) {
        this.getEntityData().set(DATA_IS_CRAZY, crazy);
        AttributeInstance instance = this.getAttribute(Attributes.ATTACK_DAMAGE);
        if (instance != null) {
            instance.removeModifier(CRAZY_MODIFIER);
            if (crazy) instance.addTransientModifier(CRAZY_MODIFIER);
        }
    }

    @Override
    public void travel(Vec3 vec3) {
        this.setSpeed(this.getMoveSpeed());
        super.travel(vec3);
    }

    public float getMoveSpeed() {
        return (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) * (this.isObsidian() ? 0.5F : this.isCrazy() ? 1.5F : 1.0F);
    }
}