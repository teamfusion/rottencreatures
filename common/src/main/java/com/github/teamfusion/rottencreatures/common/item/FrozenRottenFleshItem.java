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

public class FrozenRottenFleshItem extends Item {
    public FrozenRottenFleshItem(Properties properties) {
        super(properties);
    }

    /**
     * it has an 80% chance to apply 15 seconds of hunter to the entity.
     * it also checks if the entity is on fire to be able to extinguish it
     */
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        super.finishUsingItem(stack, level, entity);
        if (entity instanceof ServerPlayer player) {
            CriteriaTriggers.CONSUME_ITEM.trigger(player, stack);
            player.awardStat(Stats.ITEM_USED.get(this));
        }

        if (!level.isClientSide) {
            if (level.random.nextFloat() <= 0.8F) entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 300));
            if (entity.isOnFire()) entity.setSharedFlagOnFire(false);
        }

        return stack;
    }
}