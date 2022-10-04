package com.github.teamfusion.platform.common.registry.forge;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.function.Supplier;

public class ItemRegistryImpl {
    private static final Object2IntMap<ItemLike> ENTRIES = new Object2IntOpenHashMap<>();

    static {
        MinecraftForge.EVENT_BUS.register(ItemRegistryImpl.class);
    }

    public static void registerFuel(ItemLike item, int ticks) {
        ENTRIES.put(item, ticks);
    }

    @SubscribeEvent
    public static void onEvent(FurnaceFuelBurnTimeEvent event) {
        if (event.getItemStack().isEmpty()) return;
        int ticks = ENTRIES.getOrDefault(event.getItemStack().getItem(), Integer.MIN_VALUE);
        if (ticks != Integer.MIN_VALUE) event.setBurnTime(ticks);
    }

    public static FoodProperties registerFood(int nutrition, float saturation, boolean meat, boolean alwaysEat, boolean fastFood, Supplier<MobEffectInstance> effect, float probability) {
        FoodProperties.Builder builder = new FoodProperties.Builder();
        builder.nutrition(nutrition);
        builder.saturationMod(saturation);
        if (meat) builder.meat();
        if (alwaysEat) builder.alwaysEat();
        if (fastFood) builder.fast();
        builder.effect(effect, probability);
        return builder.build();
    }
}