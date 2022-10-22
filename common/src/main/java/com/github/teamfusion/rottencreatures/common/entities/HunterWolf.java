package com.github.teamfusion.rottencreatures.common.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class HunterWolf extends Wolf {
    private LivingEntity owner;

    public HunterWolf(EntityType<? extends Wolf> entityType, Level level) {
        super(entityType, level);
    }

    public void setOwner(LivingEntity entity) {
        this.owner = entity;
    }

    @Nullable @Override
    public LivingEntity getOwner() {
        return this.owner != null ? this.owner : super.getOwner();
    }

    @Override
    public boolean isAngry() {
        return true;
    }

    @Override
    public boolean isAngryAtAllPlayers(Level level) {
        return true;
    }
}