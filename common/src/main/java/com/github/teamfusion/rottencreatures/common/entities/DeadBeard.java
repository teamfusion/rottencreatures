package com.github.teamfusion.rottencreatures.common.entities;

import com.github.teamfusion.rottencreatures.common.registries.RCBlocks;
import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import java.util.Random;

public class DeadBeard extends SpellcasterZombie {
    private static final EntityDataAccessor<Integer> DATA_FUSE_ID = SynchedEntityData.defineId(DeadBeard.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_IGNITED = SynchedEntityData.defineId(DeadBeard.class, EntityDataSerializers.BOOLEAN);

    public DeadBeard(EntityType<? extends SpellcasterZombie> type, Level level) {
        super(type, level);
        this.xpReward = 20;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Zombie.createAttributes().add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.0D).add(Attributes.MAX_HEALTH, 70.0D).add(Attributes.MOVEMENT_SPEED, 0.275D).add(Attributes.ATTACK_DAMAGE, 5.0D).add(Attributes.ARMOR, 2.0D).add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new CastingSpellGoal());
        this.goalSelector.addGoal(4, new SummonLackeysGoal());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_FUSE_ID, 100);
        this.getEntityData().define(DATA_IGNITED, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putShort("Fuse", (short) this.getFuse());
        tag.putBoolean("IsIgnited", this.isIgnited());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setFuse(tag.getShort("Fuse"));
        this.setIgnited(tag.getBoolean("IsIgnited"));
    }

    public void setFuse(int ticks) {
        this.getEntityData().set(DATA_FUSE_ID, ticks);
    }

    public int getFuse() {
        return this.getEntityData().get(DATA_FUSE_ID);
    }

    public void setIgnited(boolean ignited) {
        this.getEntityData().set(DATA_IGNITED, ignited);
    }

    public boolean isIgnited() {
        return this.getEntityData().get(DATA_IGNITED);
    }

    @Override
    protected void populateDefaultEquipmentSlots(DifficultyInstance difficulty) {
        super.populateDefaultEquipmentSlots(difficulty);
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
    }

    /**
     * the captain's hat protects him from sunlight!
     */
    @Override
    protected boolean isSunSensitive() {
        return false;
    }

    @Override
    public boolean isBaby() {
        return false;
    }

    private void explode() {
        this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 4.0F, this.level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) ? Explosion.BlockInteraction.BREAK : Explosion.BlockInteraction.NONE);
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int lootingMultiplier, boolean allowDrops) {
        super.dropCustomDeathLoot(source, lootingMultiplier, allowDrops);

        if (allowDrops && this.isIgnited()) {
            this.spawnAtLocation(new ItemStack(RCBlocks.TNT_BARREL.get()));
        }
    }

    /**
     * checks for the health of dead beard to see if it should ignite the TNT Barrel
     */
    @Override
    public void tick() {
        super.tick();

        if (!this.isIgnited() && this.getHealth() <= 10.0D && !this.isDeadOrDying()) {
            this.setIgnited(true);
        }

        if (this.isIgnited() && !this.isDeadOrDying()) {
            if (!this.level.isClientSide && this.getFuse() == 100) {
                if (!this.isSilent()) this.level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
            }

            int cooldown = this.getFuse() - 1;
            this.setFuse(cooldown);
            if (cooldown <= 0) {
                this.discard();
                this.explode();
            }
        }
    }

    @Override
    public boolean canAttack(LivingEntity livingEntity) {
        return !this.isIgnited() && super.canAttack(livingEntity);
    }

    /**
     * prevents dead beard to turn into a drowned if it lays underwater for too long
     */
    @Override
    protected boolean convertsInWater() {
        return false;
    }

    public static boolean checkDeadBeardSpawnRules(EntityType<DeadBeard> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, Random random) {
        return checkAnyLightMonsterSpawnRules(type, level, spawnType, pos, random) && (spawnType == MobSpawnType.SPAWNER || level.canSeeSky(pos));
    }

    class SummonLackeysGoal extends UseSpellGoal {
        private final TargetingConditions lackeyCountTargeting = TargetingConditions.forNonCombat().range(16.0D).ignoreLineOfSight().ignoreInvisibilityTesting();

        /**
         * checks if there are still any lackeys around before summoning another bunch
         */
        @Override
        public boolean canUse() {
            if (!super.canUse()) {
                return false;
            } else {
                int zombies = DeadBeard.this.level.getNearbyEntities(ZombieLackey.class, this.lackeyCountTargeting, DeadBeard.this, DeadBeard.this.getBoundingBox().inflate(16.0D)).size();
                int skeletons = DeadBeard.this.level.getNearbyEntities(SkeletonLackey.class, this.lackeyCountTargeting, DeadBeard.this, DeadBeard.this.getBoundingBox().inflate(16.0D)).size();
                return DeadBeard.this.random.nextInt(4) + 1 > (zombies + skeletons);
            }
        }

        /**
         * summons up to 4 lackeys to defend him, also applies resistance II to himself for 5 seconds
         */
        @Override
        protected void performSpellCasting() {
            ServerLevel level = (ServerLevel)DeadBeard.this.level;

            for (int i = 0; i <= DeadBeard.this.random.nextInt(4); i++) {
                BlockPos pos = DeadBeard.this.blockPosition().offset(-2 + DeadBeard.this.random.nextInt(5), -0.8D, -2 + DeadBeard.this.random.nextInt(5));
                Monster lackey = DeadBeard.this.random.nextBoolean() ? RCEntityTypes.ZOMBIE_LACKEY.get().create(DeadBeard.this.level) : RCEntityTypes.SKELETON_LACKEY.get().create(DeadBeard.this.level);
                if (lackey instanceof Lackey lackeyIn) {
                    lackey.moveTo(pos, 0.0F, 0.0F);
                    lackey.setDeltaMovement(0.0D, 0.5D, 0.0D);
                    DeadBeard.this.level.playSound(null, lackey.blockPosition(), SoundEvents.GRAVEL_BREAK, SoundSource.NEUTRAL, 1.0F, 1.0F);
                    lackey.finalizeSpawn(level, DeadBeard.this.level.getCurrentDifficultyAt(pos), MobSpawnType.MOB_SUMMONED, null, null);
                    lackeyIn.setLimitedLife(500);
                    level.addFreshEntity(lackey);
                }
            }

            DeadBeard.this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 1));
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