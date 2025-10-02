package com.github.teamfusion.rottencreatures.common.level.entities.glacialhunter;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.WolfVariant;
import net.minecraft.world.entity.animal.WolfVariants;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
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

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        Registry<WolfVariant> variants = level.registryAccess().registryOrThrow(Registries.WOLF_VARIANT);
        this.setVariant(variants.getHolder(WolfVariants.DEFAULT).orElseThrow());
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }
}