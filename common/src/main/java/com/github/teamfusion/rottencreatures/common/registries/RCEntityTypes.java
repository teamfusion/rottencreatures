package com.github.teamfusion.rottencreatures.common.registries;

import com.blackgear.platform.core.helper.EntityRegistry;
import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import com.github.teamfusion.rottencreatures.common.level.entities.living.deadbeard.DeadBeard;
import com.github.teamfusion.rottencreatures.common.level.entities.living.glacialhunter.GlacialHunter;
import com.github.teamfusion.rottencreatures.common.level.entities.living.glacialhunter.HunterWolf;
import com.github.teamfusion.rottencreatures.common.level.entities.living.immortal.Immortal;
import com.github.teamfusion.rottencreatures.common.level.entities.living.mummy.Mummy;
import com.github.teamfusion.rottencreatures.common.level.entities.living.burned.Burned;
import com.github.teamfusion.rottencreatures.common.level.entities.living.frostbitten.Frostbitten;
import com.github.teamfusion.rottencreatures.common.level.entities.PrimedTntBarrel;
import com.github.teamfusion.rottencreatures.common.level.entities.living.lackey.SkeletonLackey;
import com.github.teamfusion.rottencreatures.common.level.entities.living.scarab.Scarab;
import com.github.teamfusion.rottencreatures.common.level.entities.living.swampy.Swampy;
import com.github.teamfusion.rottencreatures.common.level.entities.living.undeadminer.UndeadMiner;
import com.github.teamfusion.rottencreatures.common.level.entities.living.zap.Zap;
import com.github.teamfusion.rottencreatures.common.level.entities.living.lackey.ZombieLackey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.function.Supplier;

public class RCEntityTypes {
    public static final EntityRegistry ENTITIES = EntityRegistry.create(RottenCreatures.MOD_ID);

    public static final Supplier<EntityType<Burned>> BURNED = ENTITIES.entity(
        "burned",
        EntityType.Builder.of(Burned::new, MobCategory.MONSTER)
            .sized(0.6F, 1.95F)
            .clientTrackingRange(8)
            .fireImmune()
    );
    public static final Supplier<EntityType<Frostbitten>> FROSTBITTEN = ENTITIES.entity(
        "frostbitten",
        EntityType.Builder.of(Frostbitten::new, MobCategory.MONSTER)
            .sized(0.6F, 1.95F)
            .clientTrackingRange(8)
    );
    public static final Supplier<EntityType<Swampy>> SWAMPY = ENTITIES.entity(
        "swampy",
        EntityType.Builder.of(Swampy::new, MobCategory.MONSTER)
            .sized(0.6F, 1.95F)
            .clientTrackingRange(8)
    );
    public static final Supplier<EntityType<UndeadMiner>> UNDEAD_MINER = ENTITIES.entity(
        "undead_miner",
        EntityType.Builder.of(UndeadMiner::new, MobCategory.MONSTER)
            .sized(0.6F, 1.95F)
            .clientTrackingRange(8)
    );
    public static final Supplier<EntityType<Mummy>> MUMMY = ENTITIES.entity(
        "mummy",
        EntityType.Builder.of(Mummy::new, MobCategory.MONSTER)
            .sized(0.6F, 2.25F)
            .clientTrackingRange(8)
    );
    public static final Supplier<EntityType<GlacialHunter>> GLACIAL_HUNTER = ENTITIES.entity(
        "glacial_hunter",
        EntityType.Builder.of(GlacialHunter::new, MobCategory.MONSTER)
            .sized(0.7F, 1.95F)
            .clientTrackingRange(8)
    );
    public static final Supplier<EntityType<HunterWolf>> HUNTER_WOLF = ENTITIES.entity(
        "hunter_wolf",
        EntityType.Builder.of(HunterWolf::new, MobCategory.CREATURE)
            .sized(0.7F, 1.0F)
            .clientTrackingRange(8)
    );
    public static final Supplier<EntityType<Scarab>> SCARAB = ENTITIES.entity(
        "scarab",
        EntityType.Builder.of(Scarab::new, MobCategory.MONSTER)
            .sized(0.6F, 0.5F)
            .clientTrackingRange(8)
    );
    public static final Supplier<EntityType<DeadBeard>> DEAD_BEARD = ENTITIES.entity(
        "dead_beard",
        EntityType.Builder.of(DeadBeard::new, MobCategory.MONSTER)
            .sized(1.0F, 2.05F)
            .clientTrackingRange(8)
    );
    public static final Supplier<EntityType<ZombieLackey>> ZOMBIE_LACKEY = ENTITIES.entity(
        "zombie_lackey",
        EntityType.Builder.of(ZombieLackey::new, MobCategory.MONSTER)
            .sized(0.6F, 1.85F)
            .clientTrackingRange(8)
    );
    public static final Supplier<EntityType<SkeletonLackey>> SKELETON_LACKEY = ENTITIES.entity(
        "skeleton_lackey",
        EntityType.Builder.of(SkeletonLackey::new, MobCategory.MONSTER)
            .sized(0.6F, 1.85F)
            .clientTrackingRange(8)
    );
    public static final Supplier<EntityType<Immortal>> IMMORTAL = ENTITIES.entity(
        "immortal",
        EntityType.Builder.of(Immortal::new, MobCategory.MONSTER)
            .sized(0.6F, 2.0F)
            .clientTrackingRange(8)
            .fireImmune()
    );
    public static final Supplier<EntityType<Zap>> ZAP = ENTITIES.entity(
        "zap",
        EntityType.Builder.of(Zap::new, MobCategory.MONSTER)
            .sized(0.6F, 1.8F)
            .clientTrackingRange(8)
    );

    public static final Supplier<EntityType<PrimedTntBarrel>> TNT_BARREL = ENTITIES.entity(
        "primed_tnt_barrel",
        EntityType.Builder.<PrimedTntBarrel>of(PrimedTntBarrel::new, MobCategory.MISC)
            .sized(0.98F, 0.98F)
            .clientTrackingRange(10)
            .updateInterval(10)
            .fireImmune()
    );
}