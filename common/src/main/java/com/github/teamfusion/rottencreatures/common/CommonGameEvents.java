package com.github.teamfusion.rottencreatures.common;

import com.blackgear.platform.common.events.EntityEvents;
import com.blackgear.platform.core.util.event.CancellableResult;
import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;
import com.github.teamfusion.rottencreatures.core.data.tags.RCEntityTypeTags;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;

public class CommonGameEvents {
    private static final int NORMAL_DIFFICULTY_CHANCE = 4;
    private static final int HARD_DIFFICULTY_CHANCE = 2;

    public static void bootstrap() {
        registerConversionEvents();
    }

    private static void registerConversionEvents() {
        EntityEvents.ON_DEATH.register((entity, source) -> {
            if (entity instanceof Mob mob && rollConversionChance(mob)) {
                attemptEnvironmentalConversion(mob);
            }

            return CancellableResult.PASS;
        });
    }

    private static boolean rollConversionChance(Mob mob) {
        Difficulty difficulty = mob.level().getDifficulty();
        if (difficulty == Difficulty.NORMAL) {
            return mob.getRandom().nextInt(NORMAL_DIFFICULTY_CHANCE) == 0;
        } else if (difficulty == Difficulty.HARD) {
            return mob.getRandom().nextInt(HARD_DIFFICULTY_CHANCE) == 0;
        }
        return false;
    }

    private static void attemptEnvironmentalConversion(Mob mob) {
        // Check for lava conversion
        if (mob.isInLava() && mob.getType().is(RCEntityTypeTags.CAN_CONVERT_TO_BURNED)) {
            convertMob(mob, RCEntityTypes.BURNED.get());
            return;
        }

        // Check for snow conversion
        if ((mob.isInPowderSnow || mob.wasInPowderSnow) &&
            mob.getType().is(RCEntityTypeTags.CAN_CONVERT_TO_FROSTBITTEN)) {
            convertMob(mob, RCEntityTypes.FROSTBITTEN.get());
        }
    }

    private static void convertMob(Mob mob, EntityType<? extends Mob> type) {
        mob.convertTo(type, true);

        if (!mob.isSilent()) {
            mob.level().levelEvent(1026, mob.blockPosition(), 0);
        }
    }
}