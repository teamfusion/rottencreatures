package com.github.teamfusion.rottencreatures.common.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MagmaRottenFleshItem extends Item {
    public MagmaRottenFleshItem(Properties properties) {
        super(properties);
    }

    /**
     * applies a 50% chance for the effect application
     * it can either grant fire resistance for 15 seconds
     * or set the entity on fire for 5 seconds
     */
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        super.finishUsingItem(stack, level, entity);
        if (entity instanceof ServerPlayer player) {
            CriteriaTriggers.CONSUME_ITEM.trigger(player, stack);
            player.awardStat(Stats.ITEM_USED.get(this));
        }

        if (!level.isClientSide) {
            if (level.random.nextBoolean()) {
                entity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 300));
            } else {
                entity.setSecondsOnFire(5);
            }
        }

        return stack;
    }
}