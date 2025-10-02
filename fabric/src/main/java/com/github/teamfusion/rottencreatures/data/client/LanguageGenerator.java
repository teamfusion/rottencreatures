package com.github.teamfusion.rottencreatures.data.client;

import com.github.teamfusion.rottencreatures.core.data.LangConstants;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class LanguageGenerator extends FabricLanguageProvider {
    public LanguageGenerator(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder builder) {
        // ADVANCEMENTS
        builder.add(LangConstants.ADVANCEMENT_ROOT_TITLE, "Rotten to the Core");
        builder.add(LangConstants.ADVANCEMENT_ROOT_DESCRIPTION, "Encounter the decaying creatures of this world");

        builder.add(LangConstants.ADVANCEMENT_KILL_ALL_ROT_TITLE, "Purification!");
        builder.add(LangConstants.ADVANCEMENT_KILL_ALL_ROT_DESCRIPTION, "Kill every type of Rotten Creature!");

        builder.add(LangConstants.ADVANCEMENT_HIGH_TENSION_TITLE, "High Tension");
        builder.add(LangConstants.ADVANCEMENT_HIGH_TENSION_DESCRIPTION, "Kill an immortal");

        builder.add(LangConstants.ADVANCEMENT_MR_FREEZE_TITLE, "Damn you, Batman, For forcing my hand");
        builder.add(LangConstants.ADVANCEMENT_MR_FREEZE_DESCRIPTION, "Brew a Freeze Potion");

        builder.add(LangConstants.ADVANCEMENT_HE_IS_A_PIRATE_TITLE, "He's a Pirate!");
        builder.add(LangConstants.ADVANCEMENT_HE_IS_A_PIRATE_DESCRIPTION, "Kill DeadBeard and take his Treasure!");

        // BLOCKS
        builder.add(LangConstants.BURNED_HEAD, "Burned Head");
        builder.add(LangConstants.FROSTBITTEN_HEAD, "Frostbitten Head");
        builder.add(LangConstants.SWAMPY_HEAD, "Swampy Head");
        builder.add(LangConstants.UNDEAD_MINER_HEAD, "Undead Miner Head");
        builder.add(LangConstants.MUMMY_HEAD, "Mummy Head");
        builder.add(LangConstants.GLACIAL_HUNTER_HEAD, "Glacial Hunter Head");
        builder.add(LangConstants.DEAD_BEARD_HEAD, "Dead Beard Head");
        builder.add(LangConstants.IMMORTAL_HEAD, "Immortal Head");
        builder.add(LangConstants.ZAP_HEAD, "Zap Head");

        builder.add(LangConstants.TNT_BARREL, "TNT Barrel");

        builder.add(LangConstants.TREASURE_CHEST, "Treasure Chest");
        builder.add(LangConstants.TREASURE_CHEST_LOCKED, "This treasure chest is locked!");
        builder.add(LangConstants.TREASURE_CHEST_CANNOT_OPEN, "You cannot open %s's treasure!");
        builder.add(LangConstants.TREASURE_CHEST_HIDDEN_CONTENTS, "The contents of this chest are hidden...");

        // EFFECTS
        builder.add(LangConstants.CHANNELED, "Channelled");
        builder.add(LangConstants.CHANNELED_DESCRIPTION, "Causes players to naturally attract lightning bolts");

        builder.add(LangConstants.FREEZE, "Freeze");
        builder.add(LangConstants.FREEZE_DESCRIPTION, "Freezes the target in place");

        // ENTITIES
        builder.add(LangConstants.BURNED, "Burned");
        builder.add(LangConstants.FROSTBITTEN, "Frostbitten");
        builder.add(LangConstants.SWAMPY, "Swampy");
        builder.add(LangConstants.UNDEAD_MINER, "Undead Miner");
        builder.add(LangConstants.MUMMY, "Mummy");
        builder.add(LangConstants.SCARAB, "Scarab");
        builder.add(LangConstants.FLYING_SCARAB, "Flying Scarab");
        builder.add(LangConstants.GLACIAL_HUNTER, "Glacial Hunter");
        builder.add(LangConstants.HUNTER_WOLF, "Hunter's Wolf");
        builder.add(LangConstants.DEAD_BEARD, "Dead Beard");
        builder.add(LangConstants.PRIMED_TNT_BARREL, "Primed TNT Barrel");
        builder.add(LangConstants.SKELETON_LACKEY, "Skeleton Lackey");
        builder.add(LangConstants.ZOMBIE_LACKEY, "Zombie Lackey");
        builder.add(LangConstants.IMMORTAL, "Immortal");
        builder.add(LangConstants.ZAP, "Zap");

        // POTIONS
        builder.add(LangConstants.CORRUPTED_LINGERING_POTION, "Corrupted Lingering Potion");
        builder.add(LangConstants.FREEZE_LINGERING_POTION, "Lingering Potion of Freeze");
        builder.add(LangConstants.LONG_FREEZE_LINGERING_POTION, "Lingering Potion of Freeze");
        builder.add(LangConstants.STRONG_FREEZE_LINGERING_POTION, "Lingering Potion of Freeze");

        builder.add(LangConstants.CORRUPTED_POTION, "Corrupted Potion");
        builder.add(LangConstants.FREEZE_POTION, "Potion of Freeze");
        builder.add(LangConstants.LONG_FREEZE_POTION, "Potion of Freeze");
        builder.add(LangConstants.STRONG_FREEZE_POTION, "Potion of Freeze");

        builder.add(LangConstants.CORRUPTED_SPLASH_POTION, "Corrupted Splash Potion");
        builder.add(LangConstants.FREEZE_SPLASH_POTION, "Splash Potion of Freeze");
        builder.add(LangConstants.LONG_FREEZE_SPLASH_POTION, "Splash Potion of Freeze");
        builder.add(LangConstants.STRONG_FREEZE_SPLASH_POTION, "Splash Potion of Freeze");

        builder.add(LangConstants.FREEZE_TIPPED_ARROW, "Arrow of Freeze");
        builder.add(LangConstants.LONG_FREEZE_TIPPED_ARROW, "Arrow of Freeze");
        builder.add(LangConstants.STRONG_FREEZE_TIPPED_ARROW, "Arrow of Freeze");

        // ITEMS
        builder.add(LangConstants.BURNED_SPAWN_EGG, "Burned Spawn Egg");
        builder.add(LangConstants.FROSTBITTEN_SPAWN_EGG, "Frostbitten Spawn Egg");
        builder.add(LangConstants.SWAMPY_SPAWN_EGG, "Swampy Spawn Egg");
        builder.add(LangConstants.UNDEAD_MINER_SPAWN_EGG, "Undead Miner Spawn Egg");
        builder.add(LangConstants.MUMMY_SPAWN_EGG, "Mummy Spawn Egg");
        builder.add(LangConstants.SCARAB_SPAWN_EGG, "Scarab Spawn Egg");
        builder.add(LangConstants.GLACIAL_HUNTER_SPAWN_EGG, "Glacial Hunter Spawn Egg");
        builder.add(LangConstants.HUNTER_WOLF_SPAWN_EGG, "Hunter's Wolf Spawn Egg");
        builder.add(LangConstants.DEAD_BEARD_SPAWN_EGG, "Dead Beard Spawn Egg");
        builder.add(LangConstants.SKELETON_LACKEY_SPAWN_EGG, "Skeleton Lackey Spawn Egg");
        builder.add(LangConstants.ZOMBIE_LACKEY_SPAWN_EGG, "Zombie Lackey Spawn Egg");
        builder.add(LangConstants.IMMORTAL_SPAWN_EGG, "Immortal Spawn Egg");
        builder.add(LangConstants.ZAP_SPAWN_EGG, "Zap Spawn Egg");

        builder.add(LangConstants.FROZEN_ROTTEN_FLESH, "Frozen Rotten Flesh");
        builder.add(LangConstants.MAGMA_ROTTEN_FLESH, "Magma Rotten Flesh");
        builder.add(LangConstants.CORRUPTED_WART, "Corrupted Wart");
        builder.add(LangConstants.SPEAR, "Spear");

        // SUBTITLES
        builder.add(LangConstants.BURNED_IDLE, "Burned groans");
        builder.add(LangConstants.BURNED_HURT, "Burned hurts");
        builder.add(LangConstants.BURNED_DEATH, "Burned dies");

        builder.add(LangConstants.FROSTBITTEN_IDLE, "Frostbitten groans");
        builder.add(LangConstants.FROSTBITTEN_HURT, "Frostbitten hurts");
        builder.add(LangConstants.FROSTBITTEN_DEATH, "Frostbitten dies");

        builder.add(LangConstants.SWAMPY_IDLE, "Swampy groans");
        builder.add(LangConstants.SWAMPY_HURT, "Swampy hurts");
        builder.add(LangConstants.SWAMPY_DEATH, "Swampy dies");

        builder.add(LangConstants.MUMMY_IDLE, "Mummy groans");
        builder.add(LangConstants.MUMMY_HURT, "Mummy hurts");
        builder.add(LangConstants.MUMMY_DEATH, "Mummy dies");

        builder.add(LangConstants.GLACIAL_HUNTER_IDLE, "Glacial Hunter groans");
        builder.add(LangConstants.GLACIAL_HUNTER_HURT, "Glacial Hunter hurts");
        builder.add(LangConstants.GLACIAL_HUNTER_DEATH, "Glacial Hunter dies");

        builder.add(LangConstants.DEAD_BEARD_CALL, "Dead Beard call out");
        builder.add(LangConstants.DEAD_BEARD_IDLE, "Dead Beard groans");
        builder.add(LangConstants.DEAD_BEARD_HURT, "Dead Beard hurts");
        builder.add(LangConstants.DEAD_BEARD_DEATH, "Dead Beard dies");

        builder.add(LangConstants.IMMORTAL_ANGRY, "Immortal roars");
        builder.add(LangConstants.IMMORTAL_HEAL, "Immortal heals");
        builder.add(LangConstants.IMMORTAL_ELECTROSHOCK, "Immortal strikes");
        builder.add(LangConstants.IMMORTAL_IDLE, "Immortal groans");
        builder.add(LangConstants.IMMORTAL_HURT, "Immortal hurts");
        builder.add(LangConstants.IMMORTAL_DEATH, "Immortal dies");

        // CREATIVE TAB
        builder.add(LangConstants.CREATIVE_TAB, "Rotten Creatures");
    }
}