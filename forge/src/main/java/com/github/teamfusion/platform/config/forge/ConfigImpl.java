package com.github.teamfusion.platform.config.forge;

import com.github.teamfusion.platform.config.Config;
import com.github.teamfusion.rottencreatures.ConfigEntries;
import com.github.teamfusion.rottencreatures.RottenCreatures;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod.EventBusSubscriber(modid = RottenCreatures.MOD_ID)
public class ConfigImpl {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec CONFIG = BUILDER.build();

    public static void bootstrap() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CONFIG);
    }

    public static void create(Config.Entry<?> entry) {
        BUILDER.comment(entry.comment()).define(entry.name(), entry.value());
    }

    static {
        BUILDER.push("Mob Spawns");
        create(ConfigEntries.BURNED_WEIGHT);
        create(ConfigEntries.FROSTBITTEN_WEIGHT);
        create(ConfigEntries.GLACIAL_HUNTER_WEIGHT);
        create(ConfigEntries.SWAMPY_WEIGHT);
        create(ConfigEntries.UNDEAD_MINER_WEIGHT);
        create(ConfigEntries.MUMMY_WEIGHT);
        create(ConfigEntries.DEAD_BEARD_WEIGHT);
        create(ConfigEntries.IMMORTAL_CHANCE);
        BUILDER.pop();
    }
}