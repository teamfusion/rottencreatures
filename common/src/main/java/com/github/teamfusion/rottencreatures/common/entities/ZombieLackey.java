package com.github.teamfusion.rottencreatures.common.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class ZombieLackey extends Zombie implements Lackey {
    private boolean hasLimitedLife;
    private int limitedLifeTicks;

    public ZombieLackey(EntityType<? extends Zombie> type, Level level) {
        super(type, level);
        this.xpReward = 5;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Zombie.createAttributes().add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.0D).add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.MOVEMENT_SPEED, 0.22D).add(Attributes.ATTACK_DAMAGE, 3.0D).add(Attributes.ARMOR, 0.0D);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.hasLimitedLife && --this.limitedLifeTicks <= 0) {
            this.limitedLifeTicks = 20;
            this.hurt(DamageSource.STARVE, 1.0F);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("LifeTicks")) this.setLimitedLife(tag.getInt("LifeTicks"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (this.hasLimitedLife) tag.putInt("LifeTicks", this.limitedLifeTicks);
    }

    @Override
    public void setLimitedLife(int ticks) {
        this.hasLimitedLife = true;
        this.limitedLifeTicks = ticks;
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        super.populateDefaultEquipmentSlots(random, difficulty);
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
    }

    @Override
    protected boolean isSunSensitive() {
        return false;
    }

    @Override
    public boolean isBaby() {
        return false;
    }
}