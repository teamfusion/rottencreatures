package com.github.teamfusion.rottencreatures.common.registries;

import com.github.teamfusion.platform.CoreRegistry;
import com.github.teamfusion.rottencreatures.RottenCreatures;
import com.github.teamfusion.rottencreatures.common.entities.Burned;
import com.github.teamfusion.rottencreatures.common.entities.Frostbitten;
import com.github.teamfusion.rottencreatures.common.entities.PrimedTntBarrel;
import com.github.teamfusion.rottencreatures.common.entities.Swampy;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.function.Supplier;

public class RCEntityTypes {
    public static final CoreRegistry<EntityType<?>> ENTITIES = CoreRegistry.create(Registry.ENTITY_TYPE, RottenCreatures.MOD_ID);

    public static final Supplier<EntityType<Burned>> BURNED = create("burned", EntityType.Builder.of(Burned::new, MobCategory.MONSTER).fireImmune().sized(0.6F, 1.95F).clientTrackingRange(8));
    public static final Supplier<EntityType<Frostbitten>> FROSTBITTEN = create("frostbitten", EntityType.Builder.of(Frostbitten::new, MobCategory.MONSTER).sized(0.6F, 1.95F).clientTrackingRange(8));
    public static final Supplier<EntityType<Swampy>> SWAMPY = create("swampy", EntityType.Builder.of(Swampy::new, MobCategory.MONSTER).sized(0.6F, 1.95F).clientTrackingRange(8));
    public static final Supplier<EntityType<PrimedTntBarrel>> TNT_BARREL = create("tnt", EntityType.Builder.<PrimedTntBarrel>of(PrimedTntBarrel::new, MobCategory.MISC).fireImmune().sized(0.98F, 0.98F).clientTrackingRange(10).updateInterval(10));

    private static <T extends Entity> Supplier<EntityType<T>> create(String key, EntityType.Builder<T> builder) {
        return ENTITIES.register(key, () -> builder.build(key));
    }
}