package com.github.teamfusion.rottencreatures.common.level.entities.scarab;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class Scarab extends Monster {
    private static final EntityDataAccessor<Boolean> DATA_IS_FLYING = SynchedEntityData.defineId(Scarab.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_IS_LANDING = SynchedEntityData.defineId(Scarab.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_VARIANT = SynchedEntityData.defineId(Scarab.class, EntityDataSerializers.INT);

    private GroundPathNavigation groundNavigation;
    private FlyingPathNavigation flyingNavigation;
    private int flyingTime = 0;
    private int jumpDelay = 0;
    private int landingTime = 0;
    private static final int LANDING_DURATION = 10;
    private static final int EMBELLISHED_FLIGHT_CHANCE = 15; // 15% chance per tick to try flying when embellished
    private static final int DEFAULT_FLIGHT_CHANCE = 1; // 1% chance for regular beetles

    public Scarab(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new BeetleMoveControl(this);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 10.0)
            .add(Attributes.MOVEMENT_SPEED, 0.25)
            .add(Attributes.FLYING_SPEED, 0.4)
            .add(Attributes.ATTACK_DAMAGE, 2.0)
            .add(Attributes.FOLLOW_RANGE, 16.0);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_IS_FLYING, false);
        builder.define(DATA_IS_LANDING, false);
        builder.define(DATA_VARIANT, 0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new BeetleMeleeAttackGoal(this, 1.25D, false));
        this.goalSelector.addGoal(2, new ScarabFlyingGoal(this));
        this.goalSelector.addGoal(3, new BeetleRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        if (this.isFlying()) {
            if (this.flyingNavigation == null) {
                this.flyingNavigation = new FlyingPathNavigation(this, level);
                this.flyingNavigation.setCanOpenDoors(false);
                this.flyingNavigation.setCanFloat(true);
                this.flyingNavigation.setCanPassDoors(true);
            }
            return this.flyingNavigation;
        } else {
            if (this.groundNavigation == null) {
                this.groundNavigation = new GroundPathNavigation(this, level);
                this.groundNavigation.setCanOpenDoors(false);
                this.groundNavigation.setCanFloat(true);
                this.groundNavigation.setCanPassDoors(true);
            }
            return this.groundNavigation;
        }
    }

    @Override
    public void tick() {
        super.tick();

        // Handle landing animation state
        if (this.isLanding()) {
            landingTime++;
            if (landingTime >= LANDING_DURATION) {
                this.setLanding(false);
                landingTime = 0;
            }
        }

        // Force ground state if on ground for some ticks
        if (this.isFlying() && this.onGround() && !this.isLanding()) {
            setLanding(true);
            setFlying(false);
        }

        // Flying logic
        if (this.isFlying()) {
            this.flyingTime++;

            // Land if been flying too long (embellished beetles fly longer)
            int maxFlyingTime = this.isEmbellished() ? 400 : 200;
            if (flyingTime > maxFlyingTime && this.random.nextInt(100) < 10 && !this.isAggressive()) {
                this.setLanding(true);
                this.setFlying(false);
                this.flyingTime = 0;
            }

            // Apply slight upward force to prevent falling
            if (!this.onGround() && this.getDeltaMovement().y < 0.0) {
                this.setDeltaMovement(this.getDeltaMovement().multiply(1.0, 0.6, 1.0));
            }
        } else {
            // Not flying - check if we should start flying
            if (!this.isLanding() && this.onGround()) {
                // Embellished beetles are more likely to fly
                int flightChance = this.isEmbellished() ? EMBELLISHED_FLIGHT_CHANCE : DEFAULT_FLIGHT_CHANCE;

                // Random flight initiation when not aggressive
                if (--jumpDelay <= 0 && !this.isAggressive() && this.random.nextInt(100) < flightChance) {
                    this.setFlying(true);
                    this.jumpDelay = this.isEmbellished() ? 100 + this.random.nextInt(200) : 300 + this.random.nextInt(300);
                    this.push(0, 0.4, 0);
                }

                // Take flight when targeting (embellished beetles do this more aggressively)
                if (this.getTarget() != null) {
                    int combatFlightChance = this.isEmbellished() ? 20 : 3;
                    if (this.random.nextInt(100) < combatFlightChance) {
                        this.setFlying(true);
                    }
                }
            }
        }
    }

    public boolean isLanding() {
        return this.entityData.get(DATA_IS_LANDING);
    }

    public void setLanding(boolean landing) {
        this.entityData.set(DATA_IS_LANDING, landing);
    }

    public boolean isEmbellished() {
        return this.entityData.get(DATA_VARIANT) == 1;
    }

    public void setVariant(int variant) {
        this.entityData.set(DATA_VARIANT, variant);
    }

    public boolean isFlying() {
        return this.entityData.get(DATA_IS_FLYING);
    }

    public void setFlying(boolean flying) {
        this.entityData.set(DATA_IS_FLYING, flying);
        this.navigation = this.createNavigation(this.level());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("IsFlying", this.isFlying());
        tag.putBoolean("IsLanding", this.isLanding());
        tag.putInt("Variant", this.entityData.get(DATA_VARIANT));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setFlying(tag.getBoolean("IsFlying"));
        this.setLanding(tag.getBoolean("IsLanding"));
        this.setVariant(tag.contains("Variant") ? tag.getInt("Variant") : 0);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.isFlying() ? SoundEvents.BEE_LOOP : SoundEvents.SILVERFISH_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.SILVERFISH_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SILVERFISH_DEATH;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return false;
    }

    // Enhanced move control for beetles
    static class BeetleMoveControl extends MoveControl {
        private final Scarab scarab;

        public BeetleMoveControl(Scarab scarab) {
            super(scarab);
            this.scarab = scarab;
        }

        @Override
        public void tick() {
            // Don't try active flying movement during landing animation
            if (this.scarab.isLanding()) {
                // Just apply gravity during landing
                super.tick();
                return;
            }

            if (this.scarab.isFlying()) {
                // Flying movement control
                if (this.operation == Operation.MOVE_TO) {
                    this.operation = Operation.WAIT;
                    double dx = this.wantedX - this.scarab.getX();
                    double dy = this.wantedY - this.scarab.getY();
                    double dz = this.wantedZ - this.scarab.getZ();
                    double distSq = dx * dx + dy * dy + dz * dz;

                    if (distSq < 2.5E-7) {
                        this.mob.setZza(0.0F);
                        return;
                    }

                    float speed = (float)(this.speedModifier * this.mob.getAttributeValue(Attributes.FLYING_SPEED));
                    double dist = Math.sqrt(distSq);

                    // Normalize movement vector
                    this.mob.setDeltaMovement(
                        this.mob.getDeltaMovement().add(
                            dx / dist * 0.1 * speed,
                            dy / dist * 0.1 * speed + 0.05, // Add a small upward force
                            dz / dist * 0.1 * speed
                        )
                    );

                    // Look in movement direction
                    this.mob.setYRot((float)(Math.atan2(dz, dx) * (180F / Math.PI)) - 90F);
                    this.mob.setYBodyRot(this.mob.getYRot());

                    // Cap the velocity
                    Vec3 movement = this.mob.getDeltaMovement();
                    double maxSpeed = speed * 0.5;
                    if (movement.horizontalDistanceSqr() > maxSpeed * maxSpeed) {
                        Vec3 capped = movement.normalize().scale(maxSpeed);
                        this.mob.setDeltaMovement(capped.x, movement.y, capped.z);
                    }
                } else {
                    // Not actively moving, apply some drag
                    this.mob.setZza(0.0F);
                    Vec3 motion = this.scarab.getDeltaMovement();
                    this.scarab.setDeltaMovement(motion.multiply(0.9, 0.9, 0.9));
                }
            } else {
                // Use default ground movement
                super.tick();
            }
        }
    }

    // Custom attack goal that implements variant-specific behaviors
    static class BeetleMeleeAttackGoal extends MeleeAttackGoal {
        private final Scarab scarab;
        private int ramCooldown = 0;
        private int flyAttemptCooldown = 0;

        public BeetleMeleeAttackGoal(Scarab scarab, double speedModifier, boolean followingTargetEvenIfNotSeen) {
            super(scarab, speedModifier, followingTargetEvenIfNotSeen);
            this.scarab = scarab;
        }

        @Override
        public void tick() {
            super.tick();

            if (this.scarab.getTarget() != null) {
                // Death beetle ram attack when on ground
                if (!this.scarab.isEmbellished() && !this.scarab.isFlying() && --ramCooldown <= 0) {
                    if (this.scarab.distanceToSqr(this.scarab.getTarget()) < 10 && this.scarab.random.nextInt(20) == 0) {
                        // Ram attack - burst of speed toward target
                        Vec3 toTarget = this.scarab.getTarget().position().subtract(this.scarab.position()).normalize();
                        this.scarab.setDeltaMovement(toTarget.scale(0.8));
                        this.ramCooldown = 60;
                    }
                }

                // Embellished beetle should prefer flying during combat
                if (this.scarab.isEmbellished() && !this.scarab.isFlying() && !this.scarab.isLanding()) {
                    if (--flyAttemptCooldown <= 0) {
                        // Higher chance to take flight during combat if embellished
                        if (this.scarab.random.nextInt(100) < 20) {
                            this.scarab.setFlying(true);
                        }
                        flyAttemptCooldown = 20; // Check every second
                    }
                }
            }
        }
    }

    static class BeetleRandomStrollGoal extends WaterAvoidingRandomStrollGoal {
        protected final Scarab scarab;

        public BeetleRandomStrollGoal(Scarab scarab, double speedModifier) {
            super(scarab, speedModifier);
            this.scarab = scarab;
        }

        @Override
        @Nullable
        protected Vec3 getPosition() {
            if (this.scarab.isFlying()) {
                // When flying, try to find a position in 3D space
                Vec3 forward = this.mob.getViewVector(0.0F);
                Vec3 upward = new Vec3(0.0, 1.0, 0.0);
                Vec3 right = forward.cross(upward).normalize();

                // Add some randomness to direction
                forward = forward.scale(this.mob.getRandom().nextDouble() * 8.0);
                upward = upward.scale(this.mob.getRandom().nextDouble() * 2.0 - 1.0);
                right = right.scale(this.mob.getRandom().nextDouble() * 4.0 - 2.0);

                // Combine directions
                Vec3 target = this.mob.position().add(forward).add(upward).add(right);

                // Make sure we're not flying too low
                int minY = this.mob.level().getMinBuildHeight() + 5;
                if (target.y < minY) {
                    target = new Vec3(target.x, minY, target.z);
                }

                return target;
            } else {
                // Use standard ground-based movement when not flying
                if (this.mob.isInWaterOrBubble()) {
                    Vec3 vec3 = LandRandomPos.getPos(this.mob, 15, 7);
                    return vec3 == null ? super.getPosition() : vec3;
                } else {
                    // Regular random strolling with ground awareness
                    return this.mob.getRandom().nextFloat() >= this.probability ?
                        LandRandomPos.getPos(this.mob, 10, 7) : super.getPosition();
                }
            }
        }

        @Override
        public boolean canUse() {
            // Allow strolling for both ground and flying
            if (!this.forceTrigger) {
                if (this.mob.getNoActionTime() >= 100) {
                    return false;
                }
                if (this.mob.getRandom().nextInt(reducedTickDelay(this.interval)) != 0) {
                    return false;
                }
            }

            Vec3 vec3 = this.getPosition();
            if (vec3 == null) {
                return false;
            } else {
                this.wantedX = vec3.x;
                this.wantedY = vec3.y;
                this.wantedZ = vec3.z;
                this.forceTrigger = false;
                return true;
            }
        }

        @Override
        public boolean canContinueToUse() {
            // Allow longer wandering when flying
            if (this.scarab.isFlying()) {
                return this.mob.getNavigation().isInProgress() &&
                    this.mob.distanceToSqr(this.wantedX, this.wantedY, this.wantedZ) > 2.0;
            }
            return super.canContinueToUse();
        }
    }
}