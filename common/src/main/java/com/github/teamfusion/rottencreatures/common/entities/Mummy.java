package com.github.teamfusion.rottencreatures.common.entities;

import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class Mummy extends SpellcasterZombie {
    private static final EntityDataAccessor<Boolean> DATA_IS_ANCIENT = SynchedEntityData.defineId(Mummy.class, EntityDataSerializers.BOOLEAN);

    public Mummy(EntityType<? extends SpellcasterZombie> type, Level level) {
        super(type, level);
        this.xpReward = 10;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_IS_ANCIENT, false);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Zombie.createAttributes().add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.0D).add(Attributes.MAX_HEALTH, 26.0D).add(Attributes.MOVEMENT_SPEED, 0.18D).add(Attributes.ATTACK_DAMAGE, 3.0D).add(Attributes.ARMOR, 2.0D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new CastingSpellGoal());
        this.goalSelector.addGoal(4, new SummonScarabsGoal());
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (this.level.getDifficulty() == Difficulty.NORMAL || this.level.getDifficulty() == Difficulty.HARD) {
            if (this.getHealth() <= 18.0D) {
                this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200));
            } else if (this.getHealth() <= 10.0D) {
                this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 1));
            } else if (this.getHealth() <= 6.0D) {
                this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200));
            }
        }
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        boolean hurtTarget = super.doHurtTarget(entity);
        if (hurtTarget && this.getMainHandItem().isEmpty() && entity instanceof LivingEntity living) {
            float modifier = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
            living.addEffect(new MobEffectInstance(MobEffects.HUNGER, 140 * (int)modifier, 2), this);

            if (this.random.nextBoolean()) {
                this.summonScarabs(2);
            }
        }

        return hurtTarget;
    }

    @Override
    public void die(DamageSource damageSource) {
        super.die(damageSource);
        this.summonScarabs(3);
    }

    private void summonScarabs(int max) {
        if (this.level instanceof ServerLevel level) {
            for (int i = 0; i <= this.random.nextInt(max); i++) {
                BlockPos pos = this.blockPosition().offset(0, 1, 0);
                Scarab scarab = this.isAncient() ? RCEntityTypes.FLYING_SCARAB.get().create(this.level) : RCEntityTypes.SCARAB.get().create(this.level);
                scarab.moveTo(pos, 0.0F, 0.0F);
                scarab.finalizeSpawn(level, this.level.getCurrentDifficultyAt(pos), MobSpawnType.MOB_SUMMONED, null, null);
                level.addFreshEntity(scarab);
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("IsAncient", this.isAncient());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setAncient(tag.getBoolean("IsAncient"));
    }

    @Override
    public boolean isBaby() {
        return false;
    }

    @Override
    protected boolean isSunSensitive() {
        return false;
    }

    public boolean isAncient() {
        return this.getEntityData().get(DATA_IS_ANCIENT);
    }

    private void setAncient(boolean ancient) {
        this.getEntityData().set(DATA_IS_ANCIENT, ancient);
    }

    public static boolean checkMummySpawnRules(EntityType<Mummy> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return checkMonsterSpawnRules(type, level, spawnType, pos, random) && (spawnType == MobSpawnType.SPAWNER || level.canSeeSky(pos));
    }

    @Nullable @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData groupData, @Nullable CompoundTag tag) {
        if (this.random.nextFloat() <= 0.3F) this.setAncient(true);
        return super.finalizeSpawn(level, difficulty, spawnType, groupData, tag);
    }

    class SummonScarabsGoal extends UseSpellGoal {

        @Override
        protected void performSpellCasting() {
            Mummy.this.summonScarabs(5);
        }

        @Override
        protected int getCastingTime() {
            return 100;
        }

        @Override
        protected int getCastingInterval() {
            return 340;
        }
    }
}