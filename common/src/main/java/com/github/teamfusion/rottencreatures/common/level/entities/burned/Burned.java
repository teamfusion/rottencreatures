package com.github.teamfusion.rottencreatures.common.level.entities.burned;

import com.github.teamfusion.rottencreatures.client.registries.RCSoundEvents;
import com.github.teamfusion.rottencreatures.common.level.entities.frostbitten.Frostbitten;
import com.github.teamfusion.rottencreatures.common.registries.RCBlocks;
import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import com.github.teamfusion.rottencreatures.core.data.loot.RCLootTables;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class Burned extends Zombie {
    private static final AttributeModifier CRAZY_MODIFIER = new AttributeModifier(
        RottenCreatures.resource("crazy_attribute_boost"),
        0.5F,
        AttributeModifier.Operation.ADD_MULTIPLIED_BASE
    );
    private static final AttributeModifier OBSIDIAN_MODIFIER = new AttributeModifier(
        RottenCreatures.resource("obsidian_attribute_boost"),
        1.0F,
        AttributeModifier.Operation.ADD_MULTIPLIED_BASE
    );
    private static final EntityDataAccessor<Boolean> DATA_IS_OBSIDIAN = SynchedEntityData.defineId(Burned.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_IS_CRAZY = SynchedEntityData.defineId(Burned.class, EntityDataSerializers.BOOLEAN);

    public Burned(EntityType<? extends Zombie> type, Level level) {
        super(type, level);
        this.xpReward = 5;
        this.setPathfindingMalus(PathType.LAVA, 8.0F);
    }

    @Override
    protected void addBehaviourGoals() {
        super.addBehaviourGoals();
        // Burned Zombies and Frostbitten Zombies are enemies and will attack each other if possible.
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Frostbitten.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Zombie.createAttributes()
            .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.0)
            .add(Attributes.MAX_HEALTH, 22.0)
            .add(Attributes.MOVEMENT_SPEED, 0.2)
            .add(Attributes.ATTACK_DAMAGE, 4.0)
            .add(Attributes.ARMOR, 4.0);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_IS_OBSIDIAN, false);
        builder.define(DATA_IS_CRAZY, false);
    }

    @Override
    protected ResourceKey<LootTable> getDefaultLootTable() {
        return this.isObsidian()
            ? RCLootTables.BURNED_OBSIDIAN
            : super.getDefaultLootTable();
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
        if (this.random.nextInt(10) == 0) {
            this.level().addParticle(
                this.isObsidian() ? ParticleTypes.FALLING_OBSIDIAN_TEAR : ParticleTypes.FALLING_LAVA,
                this.getRandomX(0.5),
                this.getRandomY(),
                this.getRandomZ(0.5),
                0.0, 0.0, 0.0
            );
        }

        if (this.isCrazy()) {
            if (this.random.nextInt(10) == 0) {
                this.level().addParticle(
                    this.isObsidian()
                        ? (this.random.nextBoolean() ? ParticleTypes.SMOKE : ParticleTypes.LARGE_SMOKE)
                        : ParticleTypes.LAVA,
                    this.getRandomX(0.5),
                    this.getRandomY(),
                    this.getRandomZ(0.5),
                    0.0, 0.0, 0.0
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
        return RCSoundEvents.BURNED_IDLE.get();
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
                float modifier = this.level().getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
                living.igniteForSeconds(3 * (int)modifier);
            }
        }

        return hurt;
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

        if (!this.level().isClientSide) {
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
            this.level().levelEvent(1501, this.blockPosition(), 0);
        }
    }

    public boolean isCrazy() {
        return this.getEntityData().get(DATA_IS_CRAZY);
    }

    public void setCrazy(boolean crazy) {
        this.getEntityData().set(DATA_IS_CRAZY, crazy);

        if (!this.level().isClientSide) {
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
    public void travel(Vec3 vector) {
        this.setSpeed(this.getMoveSpeed());
        super.travel(vector);
    }

    public float getMoveSpeed() {
        // Apply speed modifiers depending on the state.
        // if Obsidian then change the speed to 50%, if Crazy then change the speed to 150%
        float speed = (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED);
        if (this.isObsidian()) {
            return speed * 0.5F;
        } else if (this.isCrazy()) {
            if (this.isBaby()) {
                return speed * 1.5F;
            }

            return speed * 2.5F;
        } else {
            return speed;
        }
    }

    @Override
    protected ItemStack getSkull() {
        return new ItemStack(RCBlocks.BURNED_HEAD.get());
    }

    @Override
    protected int calculateFallDamage(float distance, float amount) {
        return super.calculateFallDamage(distance, amount) - (this.isObsidian() ? 10 : 0);
    }

    public static boolean checkBurnedSpawnRules(EntityType<Burned> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return !level.getBlockState(pos.below()).is(Blocks.NETHER_WART_BLOCK);
    }

    @Override @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData groupData) {
        if (this.random.nextFloat() <= 0.05F) {
            this.setCrazy(true);
        }

        return super.finalizeSpawn(level, difficulty, spawnType, groupData);
    }
}