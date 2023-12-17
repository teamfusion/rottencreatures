package com.github.teamfusion.rottencreatures.core.mixin.access;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(PotionBrewing.class)
public interface PotionBrewingAccessor {
    @Invoker
    static void callAddContainerRecipe(Item item, Item item2, Item item3) {
        throw new UnsupportedOperationException();
    }

    @Invoker
    static void callAddContainer(Item item) {
        throw new UnsupportedOperationException();
    }

    @Invoker
    static void callAddMix(Potion potion, Item item, Potion potion2) {
        throw new UnsupportedOperationException();
    }
}
