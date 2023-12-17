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
                // Set the chance for the zombie to convert upon death.
                // 20% chance on normal, 33.3% chance on hard.
                int chance = difficulty == Difficulty.NORMAL ? 4 : 2;
                // Apply a 20% chance of conversion if the difficulty is hard.
                if (this.random.nextInt(chance) == 0) {
                    // If the zombie dies in lava, convert it into a burned.
                    if (this.isInLava()) {
                        this.rc$tryToConvert(RCEntityTypes.BURNED.get());
                    }

                    // If the zombie dies in powder snow, convert it into a frostbitten.
                    if (this.isInPowderSnow || this.wasInPowderSnow) {
                        this.rc$tryToConvert(RCEntityTypes.FROSTBITTEN.get());
                    }
                }
            }
        }

        super.die(source);
    }

    @Unique
    private void rc$tryToConvert(EntityType<? extends Zombie> type) {
        // convert into the specified entity, keeping the current inventory
        this.convertTo(type, true);
        // send conversion sound to client.
        if (!this.isSilent()) {
            this.level.levelEvent(1026, this.blockPosition(), 0);
        }
    }
}