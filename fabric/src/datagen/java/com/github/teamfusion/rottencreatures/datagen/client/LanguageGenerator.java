package com.github.teamfusion.rottencreatures.datagen.client;

import com.github.teamfusion.rottencreatures.core.data.LangConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;

public final class LanguageGenerator implements DataProvider {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private final Map<String, String> data = new TreeMap<>();
    private final DataGenerator generator;

    public LanguageGenerator(DataGenerator generator) {
        this.generator = generator;
    }

    @Override
    public void run(CachedOutput cache) throws IOException {
        this.addTranslations();
        Path path = this.generator.getOutputFolder().resolve("assets/rottencreatures/lang/en_us.json");
        DataProvider.saveStable(cache, GSON.toJsonTree(this.data), path);
    }

    @Override
    public String getName() {
        return "Language: en_us";
    }

    private void addTranslations() {
        // ADVANCEMENTS
        this.add(LangConstants.ADVANCEMENT_ROOT_TITLE, "Rotten to the Core");
        this.add(LangConstants.ADVANCEMENT_ROOT_DESCRIPTION, "Encounter the decaying creatures of this world");

        this.add(LangConstants.ADVANCEMENT_KILL_ALL_ROT_TITLE, "Purification!");
        this.add(LangConstants.ADVANCEMENT_KILL_ALL_ROT_DESCRIPTION, "Kill every type of Rotten Creature!");

        this.add(LangConstants.ADVANCEMENT_HIGH_TENSION_TITLE, "High Tension");
        this.add(LangConstants.ADVANCEMENT_HIGH_TENSION_DESCRIPTION, "Kill an immortal");

        this.add(LangConstants.ADVANCEMENT_MR_FREEZE_TITLE, "Damn you, Batman, For forcing my hand");
        this.add(LangConstants.ADVANCEMENT_MR_FREEZE_DESCRIPTION, "Brew a Freeze Potion");

        this.add(LangConstants.ADVANCEMENT_HE_IS_A_PIRATE_TITLE, "He's a Pirate!");
        this.add(LangConstants.ADVANCEMENT_HE_IS_A_PIRATE_DESCRIPTION, "Kill DeadBeard and take his Treasure!");

        // BLOCKS
        this.add(LangConstants.BURNED_HEAD, "Burned Head");
        this.add(LangConstants.FROSTBITTEN_HEAD, "Frostbitten Head");
        this.add(LangConstants.SWAMPY_HEAD, "Swampy Head");
        this.add(LangConstants.UNDEAD_MINER_HEAD, "Undead Miner Head");
        this.add(LangConstants.MUMMY_HEAD, "Mummy Head");
        this.add(LangConstants.GLACIAL_HUNTER_HEAD, "Glacial Hunter Head");
        this.add(LangConstants.DEAD_BEARD_HEAD, "Dead Beard Head");
        this.add(LangConstants.IMMORTAL_HEAD, "Immortal Head");
        this.add(LangConstants.ZAP_HEAD, "Zap Head");

        this.add(LangConstants.TNT_BARREL, "TNT Barrel");

        this.add(LangConstants.TREASURE_CHEST, "Treasure Chest");
        this.add(LangConstants.TREASURE_CHEST_LOCKED, "This treasure chest is locked!");
        this.add(LangConstants.TREASURE_CHEST_CANNOT_OPEN, "You cannot open %s treasure!");
        this.add(LangConstants.TREASURE_CHEST_HIDDEN_CONTENTS, "The contents of this chest are hidden...");

        // EFFECTS
        this.add(LangConstants.CHANNELED, "Channelled");
        this.add(LangConstants.CHANNELED_DESCRIPTION, "Causes players to naturally attract lightning bolts");

        this.add(LangConstants.FREEZE, "Freeze");
        this.add(LangConstants.FREEZE_DESCRIPTION, "Freezes the target in place");

        // ENTITIES
        this.add(LangConstants.BURNED, "Burned");
        this.add(LangConstants.FROSTBITTEN, "Frostbitten");
        this.add(LangConstants.SWAMPY, "Swampy");
        this.add(LangConstants.UNDEAD_MINER, "Undead Miner");
        this.add(LangConstants.MUMMY, "Mummy");
        this.add(LangConstants.SCARAB, "Scarab");
        this.add(LangConstants.FLYING_SCARAB, "Flying Scarab");
        this.add(LangConstants.GLACIAL_HUNTER, "Glacial Hunter");
        this.add(LangConstants.HUNTER_WOLF, "Hunter's Wolf");
        this.add(LangConstants.DEAD_BEARD, "Dead Beard");
        this.add(LangConstants.PRIMED_TNT_BARREL, "Primed TNT Barrel");
        this.add(LangConstants.SKELETON_LACKEY, "Skeleton Lackey");
        this.add(LangConstants.ZOMBIE_LACKEY, "Zombie Lackey");
        this.add(LangConstants.IMMORTAL, "Immortal");
        this.add(LangConstants.ZAP, "Zap");

        // POTIONS
        this.add(LangConstants.CORRUPTED_LINGERING_POTION, "Corrupted Lingering Potion");
        this.add(LangConstants.FREEZE_LINGERING_POTION, "Lingering Potion of Freeze");
        this.add(LangConstants.LONG_FREEZE_LINGERING_POTION, "Lingering Potion of Freeze");
        this.add(LangConstants.STRONG_FREEZE_LINGERING_POTION, "Lingering Potion of Freeze");

        this.add(LangConstants.CORRUPTED_POTION, "Corrupted Potion");
        this.add(LangConstants.FREEZE_POTION, "Potion of Freeze");
        this.add(LangConstants.LONG_FREEZE_POTION, "Potion of Freeze");
        this.add(LangConstants.STRONG_FREEZE_POTION, "Potion of Freeze");

        this.add(LangConstants.CORRUPTED_SPLASH_POTION, "Corrupted Splash Potion");
        this.add(LangConstants.FREEZE_SPLASH_POTION, "Splash Potion of Freeze");
        this.add(LangConstants.LONG_FREEZE_SPLASH_POTION, "Splash Potion of Freeze");
        this.add(LangConstants.STRONG_FREEZE_SPLASH_POTION, "Splash Potion of Freeze");

        this.add(LangConstants.FREEZE_TIPPED_ARROW, "Arrow of Freeze");
        this.add(LangConstants.LONG_FREEZE_TIPPED_ARROW, "Arrow of Freeze");
        this.add(LangConstants.STRONG_FREEZE_TIPPED_ARROW, "Arrow of Freeze");

        // ITEMS
        this.add(LangConstants.BURNED_SPAWN_EGG, "Burned Spawn Egg");
        this.add(LangConstants.FROSTBITTEN_SPAWN_EGG, "Frostbitten Spawn Egg");
        this.add(LangConstants.SWAMPY_SPAWN_EGG, "Swampy Spawn Egg");
        this.add(LangConstants.UNDEAD_MINER_SPAWN_EGG, "Undead Miner Spawn Egg");
        this.add(LangConstants.MUMMY_SPAWN_EGG, "Mummy Spawn Egg");
        this.add(LangConstants.SCARAB_SPAWN_EGG, "Scarab Spawn Egg");
        this.add(LangConstants.GLACIAL_HUNTER_SPAWN_EGG, "Glacial Hunter Spawn Egg");
        this.add(LangConstants.HUNTER_WOLF_SPAWN_EGG, "Hunter's Wolf Spawn Egg");
        this.add(LangConstants.DEAD_BEARD_SPAWN_EGG, "Dead Beard Spawn Egg");
        this.add(LangConstants.SKELETON_LACKEY_SPAWN_EGG, "Skeleton Lackey Spawn Egg");
        this.add(LangConstants.ZOMBIE_LACKEY_SPAWN_EGG, "Zombie Lackey Spawn Egg");
        this.add(LangConstants.IMMORTAL_SPAWN_EGG, "Immortal Spawn Egg");
        this.add(LangConstants.ZAP_SPAWN_EGG, "Zap Spawn Egg");

        this.add(LangConstants.FROZEN_ROTTEN_FLESH, "Frozen Rotten Flesh");
        this.add(LangConstants.MAGMA_ROTTEN_FLESH, "Magma Rotten Flesh");
        this.add(LangConstants.CORRUPTED_WART, "Corrupted Wart");
        this.add(LangConstants.SPEAR, "Spear");

        // SUBTITLES
        this.add(LangConstants.BURNED_IDLE, "Burned groans");
        this.add(LangConstants.BURNED_HURT, "Burned hurts");
        this.add(LangConstants.BURNED_DEATH, "Burned dies");

        this.add(LangConstants.FROSTBITTEN_IDLE, "Frostbitten groans");
        this.add(LangConstants.FROSTBITTEN_HURT, "Frostbitten hurts");
        this.add(LangConstants.FROSTBITTEN_DEATH, "Frostbitten dies");

        this.add(LangConstants.SWAMPY_IDLE, "Swampy groans");
        this.add(LangConstants.SWAMPY_HURT, "Swampy hurts");
        this.add(LangConstants.SWAMPY_DEATH, "Swampy dies");

        this.add(LangConstants.MUMMY_IDLE, "Mummy groans");
        this.add(LangConstants.MUMMY_HURT, "Mummy hurts");
        this.add(LangConstants.MUMMY_DEATH, "Mummy dies");

        this.add(LangConstants.GLACIAL_HUNTER_IDLE, "Glacial Hunter groans");
        this.add(LangConstants.GLACIAL_HUNTER_HURT, "Glacial Hunter hurts");
        this.add(LangConstants.GLACIAL_HUNTER_DEATH, "Glacial Hunter dies");

        this.add(LangConstants.DEAD_BEARD_CALL, "Dead Beard call out");
        this.add(LangConstants.DEAD_BEARD_IDLE, "Dead Beard groans");
        this.add(LangConstants.DEAD_BEARD_HURT, "Dead Beard hurts");
        this.add(LangConstants.DEAD_BEARD_DEATH, "Dead Beard dies");

        this.add(LangConstants.IMMORTAL_ANGRY, "Immortal roars");
        this.add(LangConstants.IMMORTAL_HEAL, "Immortal heals");
        this.add(LangConstants.IMMORTAL_ELECTROSHOCK, "Immortal strikes");
        this.add(LangConstants.IMMORTAL_IDLE, "Immortal groans");
        this.add(LangConstants.IMMORTAL_HURT, "Immortal hurts");
        this.add(LangConstants.IMMORTAL_DEATH, "Immortal dies");

        // CREATIVE TAB
        this.add(LangConstants.CREATIVE_TAB, "Rotten Creatures");
    }

    private void add(String key, String value) {
        if (this.data.put(key, value) != null) {
            throw new IllegalStateException("Duplicate translation key " + key);
        }
    }
}