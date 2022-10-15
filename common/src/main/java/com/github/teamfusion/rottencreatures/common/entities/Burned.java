package com.github.teamfusion.rottencreatures.common.entities;

import com.github.teamfusion.rottencreatures.common.LootBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Burned extends Zombie {
    private static final AttributeModifier CRAZY_MODIFIER = new AttributeModifier(UUID.fromString("a0ffa7a6-1210-466a-a9a1-31909417a99e"), "Crazy attribute boost", 0.5F, AttributeModifier.Operation.MULTIPLY_BASE);
    private static final AttributeModifier OBSIDIAN_MODIFIER = new AttributeModifier(UUID.fromString("cf2ce4af-4807-4896-aaad-1c077a87e9bf"), "Obsidian attribute boost", 1.0F, AttributeModifier.Operation.MULTIPLY_BASE);
    private static final EntityDataAccessor<Boolean> DATA_IS_OBSIDIAN = SynchedEntityData.defineId(Burned.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_IS_CRAZY = SynchedEntityData.defineId(Burned.class, EntityDataSerializers.BOOLEAN);

    public static final ResourceLocation OBSIDIAN_LOOT = LootBuilder.of("burned").build("obsidian");

    public Burned(EntityType<? extends Zombie> type, Level level) {
        super(type, level);
        this.setPathfindingMalus(BlockPathTypes.LAVA, 8.0F);
    }

    @Override
    protected void addBehaviourGoals() {
        super.addBehaviourGoals();
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Frostbitten.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Zombie.createAttributes().add(Attributes.MAX_HEALTH, 22.0D).add(Attributes.MOVEMENT_SPEED, 0.2D).add(Attributes.ATTACK_DAMAGE, 4.0D).add(Attributes.ARMOR, 4.0D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_IS_OBSIDIAN, false);
        this.getEntityData().define(DATA_IS_CRAZY, false);
    }

    @Override
    protected ResourceLocation getDefaultLootTable() {
        return this.isObsidian() ? OBSIDIAN_LOOT : super.getDefaultLootTable();
    }

    @Override
    protected boolean convertsInWater() {
        return false;
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (this.getHealth() <= 10.0D && !this.isCrazy()) this.setCrazy(true);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.isCrazy()) {
            if (this.random.nextInt(10) == 0) {
                this.level.addParticle(this.isObsidian() ? (this.random.nextBoolean() ? ParticleTypes.SMOKE : ParticleTypes.LARGE_SMOKE) : ParticleTypes.LAVA, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public void tick() {
        if (!this.level.isClientSide && this.isAlive() && !this.isNoAi()) {
            if (this.isInWaterOrBubble() && !this.isObsidian()) this.setObsidian(true);
            if (this.isInLava() && this.isObsidian()) this.setObsidian(false);
        }

        super.tick();
    }

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
        boolean hurt = super.doHurtTarget(entity);
        if (hurt && this.getMainHandItem().isEmpty() && entity instanceof LivingEntity living && !this.isObsidian()) {
            float modifier = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
            living.setSecondsOnFire(3 * (int)modifier);
        }

        return hurt;
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

        if (obsidian) this.level.levelEvent(1501, this.blockPosition(), 0);
    }

    public boolean isCrazy() {
        return this.getEntityData().get(DATA_IS_CRAZY);
    }

    public void setCrazy(boolean crazy) {
        this.getEntityData().set(DATA_IS_CRAZY, crazy);
        if (!this.level.isClientSide) {
            List<AttributeInstance> instances = new ArrayList<>();
            instances.add(this.getAttribute(Attributes.ATTACK_DAMAGE));
            instances.add(this.getAttribute(Attributes.KNOCKBACK_RESISTANCE));
            for (AttributeInstance instance : instances) {
                instance.removeModifier(CRAZY_MODIFIER);
                if (crazy) instance.addTransientModifier(CRAZY_MODIFIER);
            }
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

    @Override
    protected int calculateFallDamage(float distance, float amount) {
        return super.calculateFallDamage(distance, amount) - (this.isObsidian() ? 10 : 0);
    }

    public static boolean checkBurnedSpawnRules(EntityType<Burned> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, Random random) {
        return !level.getBlockState(pos.below()).is(Blocks.NETHER_WART_BLOCK);
    }

    @Override @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData groupData, @Nullable CompoundTag tag) {
        if (this.random.nextFloat() < 0.05F) this.setCrazy(true);
        return super.finalizeSpawn(level, difficulty, spawnType, groupData, tag);
    }
}