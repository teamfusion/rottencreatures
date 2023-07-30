package com.github.teamfusion.rottencreatures.common.entities;

import com.github.teamfusion.rottencreatures.client.registries.RCSoundEvents;
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

import java.util.Random;
import java.util.UUID;

public class Burned extends Zombie {
    // Entity Attribute Modifiers
    private static final AttributeModifier CRAZY_MODIFIER = new AttributeModifier(UUID.fromString("a0ffa7a6-1210-466a-a9a1-31909417a99e"), "Crazy attribute boost", 0.5F, AttributeModifier.Operation.MULTIPLY_BASE);
    private static final AttributeModifier OBSIDIAN_MODIFIER = new AttributeModifier(UUID.fromString("cf2ce4af-4807-4896-aaad-1c077a87e9bf"), "Obsidian attribute boost", 1.0F, AttributeModifier.Operation.MULTIPLY_BASE);

    // Entity Data Serializers
    private static final EntityDataAccessor<Boolean> DATA_IS_OBSIDIAN = SynchedEntityData.defineId(Burned.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_IS_CRAZY = SynchedEntityData.defineId(Burned.class, EntityDataSerializers.BOOLEAN);

    // Entity Loot Builder
    public static final ResourceLocation OBSIDIAN_LOOT = LootBuilder.of("burned").build("obsidian");

    public Burned(EntityType<? extends Zombie> type, Level level) {
        super(type, level);
        this.xpReward = 5;
        this.setPathfindingMalus(BlockPathTypes.LAVA, 8.0F);
    }

    @Override
    protected void addBehaviourGoals() {
        super.addBehaviourGoals();
        // Burned Zombies and Frostbitten Zombies are enemies and will attack each other if possible.
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Frostbitten.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Zombie.createAttributes()
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.0D)
                .add(Attributes.MAX_HEALTH, 22.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.ATTACK_DAMAGE, 4.0D)
                .add(Attributes.ARMOR, 4.0D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_IS_OBSIDIAN, false);
        this.getEntityData().define(DATA_IS_CRAZY, false);
    }

    @Override
    protected ResourceLocation getDefaultLootTable() {
        // Drop separate loot tables if the zombie has the Obsidian state.
        return this.isObsidian() ? OBSIDIAN_LOOT : super.getDefaultLootTable();
    }

    @Override
    protected boolean convertsInWater() {
        return false;
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        // Check if the zombie is below half it's health and not crazy to change its state.
        if (this.getHealth() <= this.getMaxHealth() * 0.5D && !this.isCrazy()) {
            this.setCrazy(true);
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        // Spawn dripping particles.
        if (this.random.nextInt(10) == 0) {
            this.level.addParticle(
                    // Change the particle type if it's Obsidian.
                    this.isObsidian() ? ParticleTypes.FALLING_OBSIDIAN_TEAR : ParticleTypes.FALLING_LAVA,
                    this.getRandomX(0.5D),
                    this.getRandomY(),
                    this.getRandomZ(0.5D),
                    0.0D,
                    0.0D,
                    0.0D
            );
        }

        // Spawn particles on Crazy state.
        if (this.isCrazy()) {
            if (this.random.nextInt(10) == 0) {
                this.level.addParticle(
                        // Change the particle type if it's Obsidian.
                        // If Obsidian, it will iterate between Smoke and Large Smoke, otherwise it will spawn Lava pops.
                        this.isObsidian() ? (this.random.nextBoolean() ? ParticleTypes.SMOKE : ParticleTypes.LARGE_SMOKE) : ParticleTypes.LAVA,
                        this.getRandomX(0.5D),
                        this.getRandomY(),
                        this.getRandomZ(0.5D),
                        0.0D,
                        0.0D,
                        0.0D
                );
            }
        }
    }

    @Override
    public void tick() {
        if (this.isAlive()) {
            // Check if the zombie is in water and if it's not Obsidian, then convert into Obsidian.
            if (this.isInWaterOrBubble() && !this.isObsidian()) {
                this.setObsidian(true);
            }

            // Check if the zombie is in lava and if it's Obsidian, then convert into Normal or Crazy.
            if (this.isInLava() && this.isObsidian()) {
                this.setObsidian(false);
            }
        }

        super.tick();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return RCSoundEvents.BURNED_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return RCSoundEvents.BURNED_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return RCSoundEvents.BURNED_DEATH.get();
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        boolean hurt = super.doHurtTarget(entity);

        // Check if the zombie can hurt the target, is not holding any item, and it's not Obsidian.
        if (hurt && this.getMainHandItem().isEmpty() && !this.isObsidian()) {
            // Set the entity on fire, the duration of it may vary depending on the difficulty.
            if (entity instanceof LivingEntity living) {
                float modifier = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
                living.setSecondsOnFire(3 * (int)modifier);
            }
        }

        return hurt;
    }

    @Override
    public boolean isSensitiveToWater() {
        return !this.isObsidian() && !this.isInWaterOrBubble();
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        // Prevent receiving any damage from projectiles if it's Obsidian.
        if (this.isObsidian()) {
            if (source.getDirectEntity() instanceof AbstractArrow) {
                return false;
            }
        }

        return super.hurt(source, amount);
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
            AttributeInstance attackDamage = this.getAttribute(Attributes.ATTACK_DAMAGE);
            AttributeInstance knockbackResistance = this.getAttribute(Attributes.KNOCKBACK_RESISTANCE);

            // Apply attribute modifiers if the zombie it's Obsidian.
            if (attackDamage != null && knockbackResistance != null) {
                attackDamage.removeModifier(OBSIDIAN_MODIFIER);
                knockbackResistance.removeModifier(OBSIDIAN_MODIFIER);

                if (obsidian) {
                    attackDamage.addTransientModifier(OBSIDIAN_MODIFIER);
                    knockbackResistance.addTransientModifier(OBSIDIAN_MODIFIER);
                }
            }
        }

        // Play the Lava Extinguishing sound and add Smoke particles once converted into Obsidian.
        if (obsidian) {
            this.level.levelEvent(1501, this.blockPosition(), 0);
        }
    }

    public boolean isCrazy() {
        return this.getEntityData().get(DATA_IS_CRAZY);
    }

    public void setCrazy(boolean crazy) {
        this.getEntityData().set(DATA_IS_CRAZY, crazy);

        if (!this.level.isClientSide) {
            AttributeInstance attackDamage = this.getAttribute(Attributes.ATTACK_DAMAGE);
            AttributeInstance knockbackResistance = this.getAttribute(Attributes.KNOCKBACK_RESISTANCE);

            // Apply attribute modifiers if the zombie it's Crazy.
            if (attackDamage != null && knockbackResistance != null) {
                attackDamage.removeModifier(CRAZY_MODIFIER);
                knockbackResistance.removeModifier(CRAZY_MODIFIER);

                if (crazy) {
                    attackDamage.addTransientModifier(CRAZY_MODIFIER);
                    knockbackResistance.addTransientModifier(CRAZY_MODIFIER);
                }
            }
        }
    }

    @Override
    public void travel(Vec3 vec3) {
        this.setSpeed(this.getMoveSpeed());
        super.travel(vec3);
    }

    public float getMoveSpeed() {
        // Apply speed modifiers depending on the state.
        // if Obsidian then change the speed to 50%, if Crazy then change the speed to 150%
        return (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) * (this.isObsidian() ? 0.5F : this.isCrazy() ? 1.5F : 1.0F);
    }

    @Override
    protected int calculateFallDamage(float distance, float amount) {
        // Apply fall resistance if zombie is Obsidian.
        return super.calculateFallDamage(distance, amount) - (this.isObsidian() ? 10 : 0);
    }

    public static boolean checkBurnedSpawnRules(EntityType<Burned> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, Random random) {
        // Burned Zombies cannot spawn on top of Nether Wart blocks.
        return !level.getBlockState(pos.below()).is(Blocks.NETHER_WART_BLOCK);
    }

    @Nullable @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData groupData, @Nullable CompoundTag tag) {
        // Set Burned zombies to have a 5% chance to spawn as Crazy.
        if (this.random.nextFloat() <= 0.05F) {
            this.setCrazy(true);
        }

        return super.finalizeSpawn(level, difficulty, spawnType, groupData, tag);
    }
}