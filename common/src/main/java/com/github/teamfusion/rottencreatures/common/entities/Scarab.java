package com.github.teamfusion.rottencreatures.common.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

public class Scarab extends Monster implements FlyingAnimal {
    private static final EntityDataAccessor<Boolean> DATA_IS_FLYING = SynchedEntityData.defineId(Scarab.class, EntityDataSerializers.BOOLEAN);

    public Scarab(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        if (this.isFlying()) this.moveControl = new FlyingMoveControl(this, 20, true);
        this.xpReward = 0;
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 16.0F);
        this.setPathfindingMalus(BlockPathTypes.COCOA, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.FENCE, -1.0F);
    }

//    @Override
//    public void addAdditionalSaveData(CompoundTag tag) {
//        super.addAdditionalSaveData(tag);
//        tag.putBoolean("IsFlying", this.isFlying());
//    }
//
//    @Override
//    public void readAdditionalSaveData(CompoundTag tag) {
//        super.readAdditionalSaveData(tag);
//        this.setFlying(tag.getBoolean("IsFlying"));
//    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 2.0).add(Attributes.MOVEMENT_SPEED, 0.22F).add(Attributes.FLYING_SPEED, 0.6F).add(Attributes.ATTACK_DAMAGE, 2.0D).add(Attributes.FOLLOW_RANGE, 40.0D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
//        this.entityData.define(DATA_IS_FLYING, false);
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return this.isFlying() ? level.getBlockState(pos).isAir() ? 10.0F : 0 : super.getWalkTargetValue(pos, level);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        if (this.isFlying()) {
            FlyingPathNavigation path = new FlyingPathNavigation(this, level) {
                @Override
                public boolean isStableDestination(BlockPos pos) {
                    return !this.level.getBlockState(pos.below()).isAir();
                }
            };
            path.setCanOpenDoors(false);
            path.setCanFloat(false);
            path.setCanPassDoors(true);
            return path;
        } else {
            return super.createNavigation(level);
        }
    }

    @Override
    public boolean isFlying() {
        return !this.onGround;
    }

    //    public boolean isFlying() {
//        return this.entityData.get(DATA_IS_FLYING);
//    }
//
//    public void setFlying(boolean flying) {
//        this.entityData.set(DATA_IS_FLYING, flying);
//    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    @Override
    protected int calculateFallDamage(float distance, float amount) {
        return super.calculateFallDamage(distance, amount) - 10;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return source != DamageSource.CACTUS && super.hurt(source, amount);
    }
}