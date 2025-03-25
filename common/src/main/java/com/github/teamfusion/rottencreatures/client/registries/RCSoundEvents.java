package com.github.teamfusion.rottencreatures.client.registries;

import com.blackgear.platform.core.helper.SoundRegistry;
import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public class RCSoundEvents {
    public static final SoundRegistry SOUNDS = SoundRegistry.create(RottenCreatures.MOD_ID);

    public static final Supplier<SoundEvent> BURNED_IDLE = SOUNDS.soundEvent("burned_idle");
    public static final Supplier<SoundEvent> BURNED_HURT = SOUNDS.soundEvent("burned_hurt");
    public static final Supplier<SoundEvent> BURNED_DEATH = SOUNDS.soundEvent("burned_death");

    public static final Supplier<SoundEvent> FROSTBITTEN_IDLE = SOUNDS.soundEvent("frostbitten_idle");
    public static final Supplier<SoundEvent> FROSTBITTEN_HURT = SOUNDS.soundEvent("frostbitten_hurt");
    public static final Supplier<SoundEvent> FROSTBITTEN_DEATH = SOUNDS.soundEvent("frostbitten_death");

    public static final Supplier<SoundEvent> SWAMPY_IDLE = SOUNDS.soundEvent("swampy_idle");
    public static final Supplier<SoundEvent> SWAMPY_HURT = SOUNDS.soundEvent("swampy_hurt");
    public static final Supplier<SoundEvent> SWAMPY_DEATH = SOUNDS.soundEvent("swampy_death");

    public static final Supplier<SoundEvent> MUMMY_IDLE = SOUNDS.soundEvent("mummy_idle");
    public static final Supplier<SoundEvent> MUMMY_HURT = SOUNDS.soundEvent("mummy_hurt");
    public static final Supplier<SoundEvent> MUMMY_DEATH = SOUNDS.soundEvent("mummy_death");

    public static final Supplier<SoundEvent> GLACIAL_HUNTER_IDLE = SOUNDS.soundEvent("glacial_hunter_idle");
    public static final Supplier<SoundEvent> GLACIAL_HUNTER_HURT = SOUNDS.soundEvent("glacial_hunter_hurt");
    public static final Supplier<SoundEvent> GLACIAL_HUNTER_DEATH = SOUNDS.soundEvent("glacial_hunter_death");

    public static final Supplier<SoundEvent> DEAD_BEARD_CALL = SOUNDS.soundEvent("dead_beard_call");
    public static final Supplier<SoundEvent> DEAD_BEARD_IDLE = SOUNDS.soundEvent("dead_beard_idle");
    public static final Supplier<SoundEvent> DEAD_BEARD_HURT = SOUNDS.soundEvent("dead_beard_hurt");
    public static final Supplier<SoundEvent> DEAD_BEARD_DEATH = SOUNDS.soundEvent("dead_beard_death");

    public static final Supplier<SoundEvent> IMMORTAL_ANGRY = SOUNDS.soundEvent("immortal_angry");
    public static final Supplier<SoundEvent> IMMORTAL_HEAL = SOUNDS.soundEvent("immortal_heal");
    public static final Supplier<SoundEvent> IMMORTAL_ELECTROSHOCK = SOUNDS.soundEvent("immortal_electroshock");
    public static final Supplier<SoundEvent> IMMORTAL_IDLE = SOUNDS.soundEvent("immortal_idle");
    public static final Supplier<SoundEvent> IMMORTAL_HURT = SOUNDS.soundEvent("immortal_hurt");
    public static final Supplier<SoundEvent> IMMORTAL_DEATH = SOUNDS.soundEvent("immortal_death");
}