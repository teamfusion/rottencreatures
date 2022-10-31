package com.github.teamfusion.rottencreatures.common.entities;

import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;
import com.github.teamfusion.rottencreatures.common.registries.RCMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

public class Immortal extends SpellcasterZombie {
    public static final EntityDataAccessor<Boolean> DASH = SynchedEntityData.defineId(Immortal.class, EntityDataSerializers.BOOLEAN);

    public Immortal(EntityType<? extends SpellcasterZombie> type, Level level) {
        super(type, level);
        this.xpReward = 30;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Zombie.createAttributes().add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.0D).add(Attributes.MAX_HEALTH, 25.0D).add(Attributes.MOVEMENT_SPEED, 0.23D).add(Attributes.FOLLOW_RANGE, 50.0D).add(Attributes.ATTACK_DAMAGE, 10.0D).add(Attributes.ARMOR, 2.0D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DASH, false);
    }

    public boolean isDashing() {
        return this.getEntityData().get(DASH);
    }

    public void setDashing(boolean dashing) {
        this.getEntityData().set(DASH, dashing);
    }

    /**
     * there are no plans for baby immortal
     */
    @Override
    public boolean isBaby() {
        return false;
    }

    /**
     * if there are any zombies nearby, he'll target and infect them to become Zaps
     */
    @Override
    protected void addBehaviourGoals() {
        super.addBehaviourGoals();
        this.goalSelector.addGoal(1, new CastingSpellGoal());
        this.goalSelector.addGoal(5, new SummonLightningGoal());
        this.goalSelector.addGoal(7, new DashingGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Zombie.class, true) {
            @Override public boolean canUse() {
                return super.canUse() && this.target.getType() != RCEntityTypes.ZAP.get() && this.target.getType() != RCEntityTypes.IMMORTAL.get() && this.target.getType() != EntityType.ZOMBIE_VILLAGER;
            }
        });
    }

    /**
     * will check if there are any zaps nearby and will increase his strength one level per each zap.
     */
    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if ((this.tickCount + this.getId()) % 1200 == 0) {
            MobEffect effect = MobEffects.DAMAGE_BOOST;
            List<Zap> zaps = this.level.getEntitiesOfClass(Zap.class, this.getBoundingBox().inflate(16.0D, 4.0D, 16.0D));
            int size = zaps.size() - 1 == -1 ? 0 : zaps.size() - 1;
            if (this.hasPower() && !zaps.isEmpty() && (!this.hasEffect(effect) || this.getEffect(effect).getAmplifier() < size || this.getEffect(effect).getDuration() < 600)) {
                this.addEffect(new MobEffectInstance(effect, 1200, size));
            }
        }
    }

    /**
     * when in contact with water source, he'll disappear with a lightning bolt
     */
    @Override
    public void tick() {
        super.tick();
        if (!this.level.isClientSide && this.isAlive() && !this.isNoAi()) {
            if (this.isInWaterOrBubble()) {
                LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(this.level);
                bolt.moveTo(Vec3.atBottomCenterOf(this.blockPosition().offset(0, 1, 0)));
                this.level.addFreshEntity(bolt);
                this.discard();
            }
        }

        if (this.isDashing()) {
            this.checkAutoSpinAttack(this.getBoundingBox(), this.getBoundingBox());
        }
    }


    /**
     * it will apply the Channeled effect while hitting a target, the durability depends on the difficulty.
     * if it's hitting a zombie, it will turn it into a zap.
     */
    @Override
    public boolean doHurtTarget(Entity entity) {
        boolean hurt = super.doHurtTarget(entity);
        if (hurt && (this.getMainHandItem().isEmpty() || this.isDashing()) && entity instanceof LivingEntity living) {
            float modifier = this.level.getCurrentDifficultyAt(this.blockPosition()).getEffectiveDifficulty();
            living.addEffect(new MobEffectInstance(RCMobEffects.CHANNELLED.get(), 140 * (int)modifier), this);
            Zap.convertToZap(this, living);

            if (this.isDashing()) entity.hurt(DamageSource.mobAttack(this), 16.0F);
        }

        return hurt;
    }

    /**
     * on collision with a zombie, it will infect them into a zap.
     * while dashing it will cause 16 points of damage and add the channeled effect
     */
    @Override
    protected void doPush(Entity entity) {
        super.doPush(entity);
        if (entity instanceof LivingEntity living) Zap.convertToZap(this, living);
        if (this.isDashing()) this.doHurtTarget(entity);
    }

    /**
     * checks for the damage source:
     * - if is a lightning bolt, then it will restore his health
     * - if it's cactus or an arrow then it will cancel the damage
     *
     * when hurt it gets Movement Speed for 5 seconds and receives Resistance III for 3 seconds
     */
    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source == DamageSource.LIGHTNING_BOLT) {
            this.restoreHealth();
            return false;
        } else if (source == DamageSource.CACTUS || Objects.equals(source.getMsgId(), "arrow")) {
            return false;
        } else {
            if (this.hasPower()) {
                this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100));
                this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 60, 2));
            }
            return super.hurt(source, amount);
        }
    }

    /**
     * if it's thundering there's a 100% chance to drop a trident with either channeling, riptide or none
     * and if it's not thundering then there's only a 10% chance to drop the trident with no enchantments
     */
    @Override
    protected void dropCustomDeathLoot(DamageSource source, int lootingMultiplier, boolean allowDrops) {
        super.dropCustomDeathLoot(source, lootingMultiplier, allowDrops);

        if (allowDrops) {
            ItemStack stack = new ItemStack(Items.TRIDENT);
            stack.setDamageValue(stack.getMaxDamage() - this.random.nextInt(1 + this.random.nextInt(Math.max(stack.getMaxDamage() - 3, 1))));
            if (this.hasPower()) {
                if (this.random.nextFloat() <= 0.75F) stack.enchant(this.random.nextBoolean() ? Enchantments.CHANNELING : Enchantments.RIPTIDE, 1);
                this.spawnAtLocation(stack);
            }

            if (!this.hasPower() && this.random.nextFloat() <= 0.1F) this.spawnAtLocation(stack);
        }
    }

    public boolean hasPower() {
        return this.level.isThundering();
    }

    public void restoreHealth() {
        this.setHealth(this.getMaxHealth());
        this.level.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), 0.0D, 0.0D, 0.0D);
    }

    /**
     * when his health is below 80%, lightning bolts will strike around him, if it's below 40% the chances will increase
     */
    class SummonLightningGoal extends UseSpellGoal {
        @Override
        protected void performSpellCasting() {
            ServerLevel level = (ServerLevel)Immortal.this.level;

            if (Immortal.this.random.nextBoolean()) {
                for (int i = 0; i <= Immortal.this.random.nextInt(2); i++) {
                    BlockPos pos = Immortal.this.blockPosition().offset(-4 + Immortal.this.random.nextInt(9), 0, -4 + Immortal.this.random.nextInt(9));
                    LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(Immortal.this.level);
                    bolt.moveTo(Vec3.atBottomCenterOf(pos));
                    level.addFreshEntity(bolt);
                }
            }
        }

        @Override
        public boolean canUse() {
            return Immortal.this.getHealth() <= Immortal.this.getMaxHealth() / 100 * 80 && Immortal.this.hasPower() && Immortal.this.level.canSeeSky(Immortal.this.blockPosition()) && Immortal.this.random.nextBoolean();
        }

        @Override
        protected int getCastingTime() {
            return 0;
        }

        @Override
        protected int getCastingInterval() {
            return Immortal.this.getHealth() <= Immortal.this.getMaxHealth() / 100 * 40 ? 800 : 1600;
        }
    }

    /**
     * if the target is too far from the immortal, he'll dash towards the target
     * once the dash attack has stopped, he'll restore his life
     */
    class DashingGoal extends UseSpellGoal {
        private final Immortal immortal;

        DashingGoal(Immortal immortal) {
            this.immortal = immortal;
            this.setFlags(EnumSet.of(Flag.TARGET));
        }

        @Override
        public void start() {
            super.start();
            this.immortal.setDashing(true);
        }

        @Override
        protected void performSpellCasting() {
            float zRot = this.immortal.getYHeadRot();
            float xRot = this.immortal.getXRot();
            float modifier = 0.017453292F;
            float x = -Mth.sin(zRot * modifier) * Mth.cos(xRot * modifier);
            float y = -Mth.sin(xRot * modifier);
            float z = Mth.cos(zRot * modifier) * Mth.cos(xRot * modifier);
            float range = Mth.sqrt(x * x + y * y + z * z);
            float power = 4.0F;
            x *= power / range;
            y *= power / range;
            z *= power / range;
            this.immortal.push(x, y, z);
            this.immortal.autoSpinAttackTicks = 30;
            if (!this.immortal.level.isClientSide) this.immortal.setLivingEntityFlag(4, true);
            if (this.immortal.isOnGround()) this.immortal.move(MoverType.SELF, new Vec3(0.0D, 1.1999999F, 0.0F));
            this.immortal.level.playSound(null, this.immortal, SoundEvents.TRIDENT_RIPTIDE_3, SoundSource.PLAYERS, 1.0F, 1.0F);
            this.immortal.level.playSound(null, this.immortal, SoundEvents.TRIDENT_RETURN, SoundSource.PLAYERS, 1.0F, 1.0F);
        }

        @Override
        public void tick() {
            super.tick();
            Immortal.this.checkAutoSpinAttack(Immortal.this.getBoundingBox(), Immortal.this.getBoundingBox());
        }

        @Override
        public void stop() {
            super.stop();
            this.immortal.setDashing(false);
            this.immortal.restoreHealth();
        }

        @Override
        public boolean canUse() {
            LivingEntity target = this.immortal.getTarget() != null ? this.immortal.getTarget() : null;
            return target != null && this.immortal.distanceTo(target) > 8 && super.canUse() && this.immortal.hasPower();
        }

        @Override
        protected int getCastingTime() {
            return 20;
        }

        @Override
        protected int getCastingInterval() {
            return 640;
        }
    }
}