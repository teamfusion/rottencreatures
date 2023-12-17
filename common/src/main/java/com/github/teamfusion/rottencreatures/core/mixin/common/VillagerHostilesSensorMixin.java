package com.github.teamfusion.rottencreatures.core.mixin.common;

import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;
import com.google.common.collect.ImmutableMap;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.sensing.VillagerHostilesSensor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(VillagerHostilesSensor.class)
public class VillagerHostilesSensorMixin {
    @Mutable @Shadow @Final private static ImmutableMap<EntityType<?>, Float> ACCEPTABLE_DISTANCE_FROM_HOSTILES;

    /*
      makes the villagers able to run away
      from all the zombies included in this mod
     */
    static {
        ACCEPTABLE_DISTANCE_FROM_HOSTILES = ImmutableMap.<EntityType<?>, Float>builder()
                .putAll(ACCEPTABLE_DISTANCE_FROM_HOSTILES)
                .put(RCEntityTypes.BURNED.get(), 10.0F)
                .put(RCEntityTypes.FROSTBITTEN.get(), 8.0F)
                .put(RCEntityTypes.SWAMPY.get(), 8.0F)
                .put(RCEntityTypes.UNDEAD_MINER.get(), 8.0F)
                .put(RCEntityTypes.MUMMY.get(), 8.0F)
                .put(RCEntityTypes.GLACIAL_HUNTER.get(), 8.0F)
                .put(RCEntityTypes.DEAD_BEARD.get(), 10.0F)
                .put(RCEntityTypes.ZOMBIE_LACKEY.get(), 8.0F)
                .put(RCEntityTypes.IMMORTAL.get(), 12.0F)
                .put(RCEntityTypes.ZAP.get(), 8.0F)
                .build();
    }
}