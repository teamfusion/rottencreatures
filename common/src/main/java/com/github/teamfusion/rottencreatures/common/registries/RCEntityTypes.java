package com.github.teamfusion.rottencreatures.common.registries;

import com.blackgear.platform.core.CoreRegistry;
import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.github.teamfusion.rottencreatures.common.entities.DeadBeard;
import com.github.teamfusion.rottencreatures.common.entities.FlyingScarab;
import com.github.teamfusion.rottencreatures.common.entities.GlacialHunter;
import com.github.teamfusion.rottencreatures.common.entities.HunterWolf;
import com.github.teamfusion.rottencreatures.common.entities.Immortal;
import com.github.teamfusion.rottencreatures.common.entities.Mummy;
import com.github.teamfusion.rottencreatures.common.entities.Scarab;
import com.github.teamfusion.rottencreatures.common.entities.Burned;
import com.github.teamfusion.rottencreatures.common.entities.Frostbitten;
import com.github.teamfusion.rottencreatures.common.entities.PrimedTntBarrel;
import com.github.teamfusion.rottencreatures.common.entities.SkeletonLackey;
import com.github.teamfusion.rottencreatures.common.entities.Swampy;
import com.github.teamfusion.rottencreatures.common.entities.TreasureChest;
import com.github.teamfusion.rottencreatures.common.entities.UndeadMiner;
import com.github.teamfusion.rottencreatures.common.entities.Zap;
import com.github.teamfusion.rottencreatures.common.entities.ZombieLackey;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.function.Supplier;

public class RCEntityTypes {
    public static final CoreRegistry<EntityType<?>> ENTITIES = CoreRegistry.create(Registry.ENTITY_TYPE, RottenCreatures.MOD_ID);

    public static final Supplier<EntityType<Burned>> BURNED = register(
        "burned",
        EntityType.Builder.of(Burned::new, MobCategory.MONSTER)
            .sized(0.6F, 1.95F)
            .clientTrackingRange(8)
            .fireImmune()
    );
    public static final Supplier<EntityType<Frostbitten>> FROSTBITTEN = register(
        "frostbitten",
        EntityType.Builder.of(Frostbitten::new, MobCategory.MONSTER)
            .sized(0.6F, 1.95F)
            .clientTrackingRange(8)
    );
    public static final Supplier<EntityType<Swampy>> SWAMPY = register(
        "swampy",
        EntityType.Builder.of(Swampy::new, MobCategory.MONSTER)
            .sized(0.6F, 1.95F)
            .clientTrackingRange(8)
    );
    public static final Supplier<EntityType<UndeadMiner>> UNDEAD_MINER = register(
        "undead_miner",
        EntityType.Builder.of(UndeadMiner::new, MobCategory.MONSTER)
            .sized(0.6F, 1.95F)
            .clientTrackingRange(8)
    );
    public static final Supplier<EntityType<Mummy>> MUMMY = register(
        "mummy",
        EntityType.Builder.of(Mummy::new, MobCategory.MONSTER)
            .sized(0.6F, 2.25F)
            .clientTrackingRange(8)
    );
    public static final Supplier<EntityType<GlacialHunter>> GLACIAL_HUNTER = register(
        "glacial_hunter",
        EntityType.Builder.of(GlacialHunter::new, MobCategory.MONSTER)
            .sized(0.7F, 1.95F)
            .clientTrackingRange(8)
    );
    public static final Supplier<EntityType<HunterWolf>> HUNTER_WOLF = register(
        "hunter_wolf",
        EntityType.Builder.of(HunterWolf::new, MobCategory.CREATURE)
            .sized(0.7F, 1.0F)
            .clientTrackingRange(8)
    );
    public static final Supplier<EntityType<Scarab>> SCARAB = register(
        "scarab",
        EntityType.Builder.of(Scarab::new, MobCategory.MONSTER)
            .sized(0.6F, 0.5F)
            .clientTrackingRange(8)
    );
    public static final Supplier<EntityType<FlyingScarab>> FLYING_SCARAB = register(
        "flying_scarab",
        EntityType.Builder.of(FlyingScarab::new, MobCategory.MONSTER)
            .sized(0.6F, 0.5F)
            .clientTrackingRange(8)
    );
    public static final Supplier<EntityType<DeadBeard>> DEAD_BEARD = register(
        "dead_beard",
        EntityType.Builder.of(DeadBeard::new, MobCategory.MONSTER)
            .sized(1.0F, 2.05F)
            .clientTrackingRange(8)
    );
    public static final Supplier<EntityType<ZombieLackey>> ZOMBIE_LACKEY = register(
        "zombie_lackey",
        EntityType.Builder.of(ZombieLackey::new, MobCategory.MONSTER)
            .sized(0.6F, 1.85F)
            .clientTrackingRange(8)
    );
    public static final Supplier<EntityType<SkeletonLackey>> SKELETON_LACKEY = register(
        "skeleton_lackey",
        EntityType.Builder.of(SkeletonLackey::new, MobCategory.MONSTER)
            .sized(0.6F, 1.85F)
            .clientTrackingRange(8)
    );
    public static final Supplier<EntityType<Immortal>> IMMORTAL = register(
        "immortal",
        EntityType.Builder.of(Immortal::new, MobCategory.MONSTER)
            .sized(0.6F, 2.0F)
            .clientTrackingRange(8)
            .fireImmune()
    );
    public static final Supplier<EntityType<Zap>> ZAP = register(
        "zap",
        EntityType.Builder.of(Zap::new, MobCategory.MONSTER)
            .sized(0.6F, 1.8F)
            .clientTrackingRange(8)
    );

    public static final Supplier<EntityType<PrimedTntBarrel>> TNT_BARREL = register(
        "primed_tnt_barrel",
        EntityType.Builder.<PrimedTntBarrel>of(PrimedTntBarrel::new, MobCategory.MISC)
            .sized(0.98F, 0.98F)
            .clientTrackingRange(10)
            .updateInterval(10)
            .fireImmune()
    );
    public static final Supplier<EntityType<TreasureChest>> TREASURE_CHEST = register(
        "treasure_chest",
        EntityType.Builder.of(TreasureChest::new, MobCategory.MISC)
            .sized(0.5F, 0.25F)
            .clientTrackingRange(10)
            .updateInterval(10)
    );

    private static <T extends Entity> Supplier<EntityType<T>> register(String key, EntityType.Builder<T> builder) {
        return ENTITIES.register(key, () -> builder.build(key));
    }
}