package com.github.teamfusion.rottencreatures.core.mixin.common;

import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Zombie.class)
public class ZombieMixin extends Monster {
    protected ZombieMixin(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void die(DamageSource source) {
        if (this.getType() == EntityType.ZOMBIE) {
            Difficulty difficulty = this.level.getDifficulty();
            if (difficulty == Difficulty.NORMAL || difficulty == Difficulty.HARD) {
                int chance = difficulty == Difficulty.NORMAL ? 4 : 2;
                if (this.random.nextInt(chance) == 0) {
                    if (this.isInLava()) {
                        this.tryToConvert(RCEntityTypes.BURNED.get());
                    }

                    if (this.isInPowderSnow || this.wasInPowderSnow) {
                        this.tryToConvert(RCEntityTypes.FROSTBITTEN.get());
                    }
                }
            }
        }

        super.die(source);
    }

    @Unique
    private void tryToConvert(EntityType<? extends Zombie> type) {
        // convert into the specified entity, keeping the current inventory
        this.convertTo(type, true);
        // send conversion sound to client.
        if (!this.isSilent()) {
            this.level.levelEvent(1026, this.blockPosition(), 0);
        }
    }
}