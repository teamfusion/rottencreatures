package com.github.teamfusion.rottencreatures.client.registries;

import com.github.teamfusion.rottencreatures.core.platform.CoreRegistry;
import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public class RCSoundEvents {
    public static final CoreRegistry<SoundEvent> SOUNDS = CoreRegistry.create(Registry.SOUND_EVENT, RottenCreatures.MOD_ID);

    // ========== BEETLE SOUNDS ========================================================================================
    public static final Supplier<SoundEvent> BEETLE_DEATH = create("entity.beetle.death");

    // ========== BURNED SOUNDS ========================================================================================
    public static final Supplier<SoundEvent> BURNED_DEATH = create("entity.burned.death");
    public static final Supplier<SoundEvent> BURNED_HURT = create("entity.burned.hurt");
    public static final Supplier<SoundEvent> BURNED_AMBIENT = create("entity.burned.ambient");

    // ========== DEAD BEARD SOUNDS ====================================================================================
    public static final Supplier<SoundEvent> DEAD_BEARD_CALL = create("entity.deadbeard.call");
    public static final Supplier<SoundEvent> DEAD_BEARD_DEATH = create("entity.deadbeard.death");
    public static final Supplier<SoundEvent> DEAD_BEARD_HURT = create("entity.deadbeard.hurt");
    public static final Supplier<SoundEvent> DEAD_BEARD_AMBIENT = create("entity.deadbeard.ambient");

    // ========== FROSTBITTEN SOUNDS ===================================================================================
    public static final Supplier<SoundEvent> FROSTBITTEN_DEATH = create("entity.frostbitten.death");
    public static final Supplier<SoundEvent> FROSTBITTEN_HURT = create("entity.frostbitten.hurt");
    public static final Supplier<SoundEvent> FROSTBITTEN_AMBIENT = create("entity.frostbitten.ambient");

    // ========== GLACIAL HUNTER SOUNDS ================================================================================
    public static final Supplier<SoundEvent> GLACIAL_HUNTER_DEATH = create("entity.glacial_hunter.death");
    public static final Supplier<SoundEvent> GLACIAL_HUNTER_HURT = create("entity.glacial_hunter.hurt");
    public static final Supplier<SoundEvent> GLACIAL_HUNTER_AMBIENT = create("entity.glacial_hunter.ambient");

    // ========== IMMORTAL SOUNDS ======================================================================================
    public static final Supplier<SoundEvent> IMMORTAL_ANGRY = create("entity.immortal.angry");
    public static final Supplier<SoundEvent> IMMORTAL_CURED = create("entity.immortal.cured");
    public static final Supplier<SoundEvent> IMMORTAL_DEATH = create("entity.immortal.death");
    public static final Supplier<SoundEvent> IMMORTAL_ELECTROSHOCK = create("entity.immortal.electroshock");
    public static final Supplier<SoundEvent> IMMORTAL_HURT = create("entity.immortal.hurt");
    public static final Supplier<SoundEvent> IMMORTAL_AMBIENT = create("entity.immortal.ambient");

    // ========== MUMMY SOUNDS =========================================================================================
    public static final Supplier<SoundEvent> MUMMY_DEATH = create("entity.mummy.death");
    public static final Supplier<SoundEvent> MUMMY_HURT = create("entity.mummy.hurt");
    public static final Supplier<SoundEvent> MUMMY_AMBIENT = create("entity.mummy.ambient");

    // ========== SWAMPY SOUNDS ========================================================================================
    public static final Supplier<SoundEvent> SWAMPY_DEATH = create("entity.swampy.death");
    public static final Supplier<SoundEvent> SWAMPY_HURT = create("entity.swampy.hurt");
    public static final Supplier<SoundEvent> SWAMPY_AMBIENT = create("entity.swampy.ambient");

    // ========== REGISTRY METHODS =====================================================================================
    private static Supplier<SoundEvent> create(String key) {
        return SOUNDS.register(key, () -> new SoundEvent(new ResourceLocation(RottenCreatures.MOD_ID, key)));
    }
}