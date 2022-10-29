package com.github.teamfusion.rottencreatures.common.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class TreasureChest extends PathfinderMob {
    public static final EntityDataAccessor<Boolean> DATA_OPENED = SynchedEntityData.defineId(TreasureChest.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> DATA_COOLDOWN = SynchedEntityData.defineId(TreasureChest.class, EntityDataSerializers.INT);

    public TreasureChest(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 0;
        this.setNoGravity(true);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_OPENED, false);
        this.entityData.define(DATA_COOLDOWN, 240);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("IsOpen", this.isOpen());
        tag.putShort("Cooldown", (short)this.getCooldown());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setOpen(tag.getBoolean("IsOpen"));
        this.setCooldown(tag.getShort("Cooldown"));
    }

    @Override
    public void tick() {
        int cooldown = this.getCooldown() - 1;
        this.setCooldown(cooldown);
        if (this.getCooldown() <= 200 && !this.isOpen()) {
            this.setOpen(true);
        }

        this.setCooldown(cooldown);
        if (cooldown <= 0) {
            this.discard();
        } else if (this.isOpen()) {
            this.spawnAtLocation(this.getTreasure(), 0.25F);
        }
    }

    private ItemStack getTreasure() {
        Item entry = null;
        if (this.random.nextFloat() <= 0.006F) {
            entry = Items.TOTEM_OF_UNDYING;
        } else if (this.random.nextFloat() <= 0.025F) {
            entry = Items.DIAMOND;
        } else if (this.random.nextFloat() <= 0.02F) {
            entry = Items.EMERALD;
        } else if (this.random.nextFloat() <= 0.075F) {
            entry = Items.GOLD_INGOT;
        } else if (this.random.nextFloat() <= 0.15F) {
            entry = Items.GOLD_NUGGET;
        } else if (this.random.nextFloat() <= 0.35F) {
            entry = Items.IRON_NUGGET;
        }

        if (entry != null) this.playSound(SoundEvents.ITEM_PICKUP, 1.0F, 1.0F);
        return entry != null ? new ItemStack(entry) : ItemStack.EMPTY;
    }

    public void setOpen(boolean open) {
        this.entityData.set(DATA_OPENED, open);
    }

    public boolean isOpen() {
        return this.entityData.get(DATA_OPENED);
    }

    public void setCooldown(int cooldown) {
        this.entityData.set(DATA_COOLDOWN, cooldown);
    }

    public int getCooldown() {
        return this.entityData.get(DATA_COOLDOWN);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return source == DamageSource.OUT_OF_WORLD;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean canCollideWith(Entity entity) {
        return true;
    }

    @Override
    public boolean isPushable() {
        return false;
    }
}