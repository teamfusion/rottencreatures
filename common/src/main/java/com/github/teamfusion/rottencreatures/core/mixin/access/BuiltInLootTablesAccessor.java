package com.github.teamfusion.rottencreatures.core.mixin.access;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BuiltInLootTables.class)
public interface BuiltInLootTablesAccessor {
    @Invoker
    static ResourceKey<LootTable> callRegister(ResourceKey<LootTable> key) {
        throw new UnsupportedOperationException();
    }
}
