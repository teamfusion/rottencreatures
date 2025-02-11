package com.github.teamfusion.rottencreatures.common.worldgen;

import com.blackgear.platform.common.worldgen.modifier.BiomeManager;
import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.github.teamfusion.rottencreatures.common.registries.RCEntityTypes;
import com.github.teamfusion.rottencreatures.data.RCBiomeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;

public class WorldGeneration {
    public static void setup() {
        BiomeManager.add((writer, context) -> {
            if (context.is(RCBiomeTags.CAN_BURNED_SPAWN_ON)) {
                writer.addSpawn(
                    MobCategory.MONSTER,
                    new MobSpawnSettings.SpawnerData(RCEntityTypes.BURNED.get(), RottenCreatures.CONFIG.burnedWeight.get(), 4, 4)
                );
            }

            if (context.is(RCBiomeTags.CAN_FROSTBITTEN_SPAWN_ON)) {
                writer.addSpawn(
                    MobCategory.MONSTER,
                    new MobSpawnSettings.SpawnerData(RCEntityTypes.FROSTBITTEN.get(), RottenCreatures.CONFIG.frostbittenWeight.get(), 4, 4)
                );
            }

            if (context.is(RCBiomeTags.CAN_GLACIAL_HUNTER_SPAWN_ON)) {
                writer.addSpawn(
                    MobCategory.MONSTER,
                    new MobSpawnSettings.SpawnerData(RCEntityTypes.GLACIAL_HUNTER.get(), RottenCreatures.CONFIG.glacialHunterWeight.get(), 1, 3)
                );
            }

            if (context.is(RCBiomeTags.CAN_SWAMPY_SPAWN_ON)) {
                writer.addSpawn(
                    MobCategory.MONSTER,
                    new MobSpawnSettings.SpawnerData(RCEntityTypes.SWAMPY.get(), RottenCreatures.CONFIG.swampyWeight.get(), 4, 4)
                );
            }

            if (context.hasEntity(() -> EntityType.ZOMBIE) && context.is(RCBiomeTags.CAN_UNDEAD_MINER_SPAWN_ON)) {
                writer.addSpawn(
                    MobCategory.MONSTER,
                    new MobSpawnSettings.SpawnerData(RCEntityTypes.UNDEAD_MINER.get(), RottenCreatures.CONFIG.undeadMinerWeight.get(), 1, 4)
                );
            }

            if (context.is(RCBiomeTags.CAN_MUMMY_SPAWN_ON)) {
                writer.addSpawn(
                    MobCategory.MONSTER,
                    new MobSpawnSettings.SpawnerData(RCEntityTypes.MUMMY.get(), RottenCreatures.CONFIG.mummyWeight.get(), 1, 3)
                );
            }

            if (context.is(RCBiomeTags.CAN_DEAD_BEARD_SPAWN_ON)) {
                writer.addSpawn(
                    MobCategory.MONSTER,
                    new MobSpawnSettings.SpawnerData(RCEntityTypes.DEAD_BEARD.get(), RottenCreatures.CONFIG.deadBeardWeight.get(), 1, 1)
                );
            }
        });
    }
}