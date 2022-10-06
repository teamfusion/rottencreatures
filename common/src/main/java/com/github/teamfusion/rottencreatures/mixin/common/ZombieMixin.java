package com.github.teamfusion.rottencreatures.mixin.common;

import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Zombie.class)
public class ZombieMixin extends Monster {
    protected ZombieMixin(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * checks the difficulty and the environment of the zombie at the moment of dying
     * if a zombie dies in lava it will be transformed into a burned
     * if a zombie dies in powder snow will be transformed into a frostbitten
     *
     * each transformation has a different chance depending on the difficulty
     * if it's normal then it applies 50% of the time
     * if it's hard then it applies 100% of the time
     *
     * will also apply a conversion sound at the moment of transformation.
     */
    @Override
    public void die(DamageSource source) {
        if (this.getType() == EntityType.ZOMBIE && (this.level.getDifficulty() == Difficulty.NORMAL || this.level.getDifficulty() == Difficulty.HARD)) {
            if (this.level.getDifficulty() != Difficulty.HARD && this.level.random.nextBoolean()) return;

            if (this.isInLava()) {
                this.convertTo(RCEntityTypes.BURNED.get(), true);
                if (!this.isSilent()) {
                    this.level.levelEvent(1026, this.blockPosition(), 0);
                }
            }

            if (this.isInPowderSnow || this.wasInPowderSnow) {
                this.convertTo(RCEntityTypes.FROSTBITTEN.get(), true);
                if (!this.isSilent()) {
                    this.level.levelEvent(1026, this.blockPosition(), 0);
                }
            }
        }

        super.die(source);
    }
}