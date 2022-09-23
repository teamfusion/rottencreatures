package com.github.teamfusion.rottencreatures.mixin.access;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BuiltInLootTables.class)
public interface BuiltInLootTablesAccessor {
    @Invoker
    static ResourceLocation callRegister(ResourceLocation resourceLocation) {
        throw new UnsupportedOperationException();
    }
}
