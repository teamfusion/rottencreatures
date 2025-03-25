package com.github.teamfusion.rottencreatures.client.registries;

import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import net.minecraft.client.model.geom.ModelLayerLocation;

public class RCModelLayers {
    public static final ModelLayerLocation BURNED = register("burned");
    public static final ModelLayerLocation BURNED_HEAD = register("burned", "head");
    public static final ModelLayerLocation BURNED_INNER_ARMOR = register("burned", "inner_armor");
    public static final ModelLayerLocation BURNED_OUTER_ARMOR = register("burned", "outer_armor");

    public static final ModelLayerLocation FROSTBITTEN = register("frostbitten");
    public static final ModelLayerLocation FROSTBITTEN_HEAD = register("frostbitten", "head");
    public static final ModelLayerLocation FROSTBITTEN_INNER_ARMOR = register("frostbitten", "inner_armor");
    public static final ModelLayerLocation FROSTBITTEN_OUTER_ARMOR = register("frostbitten", "outer_armor");

    public static final ModelLayerLocation SWAMPY = register("swampy");
    public static final ModelLayerLocation SWAMPY_HEAD = register("swampy", "head");
    public static final ModelLayerLocation SWAMPY_INNER_ARMOR = register("swampy", "inner_armor");
    public static final ModelLayerLocation SWAMPY_OUTER_ARMOR = register("swampy", "outer_armor");

    public static final ModelLayerLocation UNDEAD_MINER = register("undead_miner");
    public static final ModelLayerLocation UNDEAD_MINER_HEAD = register("undead_miner", "head");
    public static final ModelLayerLocation UNDEAD_MINER_INNER_ARMOR = register("undead_miner", "inner_armor");
    public static final ModelLayerLocation UNDEAD_MINER_OUTER_ARMOR = register("undead_miner", "outer_armor");

    public static final ModelLayerLocation MUMMY = register("mummy");
    public static final ModelLayerLocation MUMMY_HEAD = register("mummy", "head");
    public static final ModelLayerLocation MUMMY_INNER_ARMOR = register("mummy", "inner_armor");
    public static final ModelLayerLocation MUMMY_OUTER_ARMOR = register("mummy", "outer_armor");

    public static final ModelLayerLocation GLACIAL_HUNTER = register("glacial_hunter");
    public static final ModelLayerLocation GLACIAL_HUNTER_HEAD = register("glacial_hunter", "head");
    public static final ModelLayerLocation HUNTER_WOLF = register("hunter_wolf");

    public static final ModelLayerLocation DEAD_BEARD = register("dead_beard");
    public static final ModelLayerLocation DEAD_BEARD_HEAD = register("dead_beard", "head");

    public static final ModelLayerLocation ZOMBIE_LACKEY = register("zombie_lackey");
    public static final ModelLayerLocation ZOMBIE_LACKEY_OUTER_LAYER = register("zombie_lackey", "outer_layer");
    public static final ModelLayerLocation ZOMBIE_LACKEY_INNER_ARMOR = register("zombie_lackey", "inner_armor");
    public static final ModelLayerLocation ZOMBIE_LACKEY_OUTER_ARMOR = register("zombie_lackey", "outer_armor");

    public static final ModelLayerLocation SKELETON_LACKEY = register("skeleton_lackey");
    public static final ModelLayerLocation SKELETON_LACKEY_INNER_ARMOR = register("skeleton_lackey", "inner_armor");
    public static final ModelLayerLocation SKELETON_LACKEY_OUTER_ARMOR = register("skeleton_lackey", "outer_armor");

    public static final ModelLayerLocation IMMORTAL = register("immortal");
    public static final ModelLayerLocation IMMORTAL_HEAD = register("immortal", "head");
    public static final ModelLayerLocation IMMORTAL_INNER_ARMOR = register("immortal", "inner_armor");
    public static final ModelLayerLocation IMMORTAL_OUTER_ARMOR = register("immortal", "outer_armor");
    public static final ModelLayerLocation IMMORTAL_OVERLAY = register("immortal", "overlay");
    public static final ModelLayerLocation IMMORTAL_DASH = register("immortal", "dash");

    public static final ModelLayerLocation ZAP = register("zap");
    public static final ModelLayerLocation ZAP_HEAD = register("zap", "head");
    public static final ModelLayerLocation ZAP_INNER_ARMOR = register("zap", "inner_armor");
    public static final ModelLayerLocation ZAP_OUTER_ARMOR = register("zap", "outer_armor");

    public static final ModelLayerLocation SCARAB = register("scarab");

    public static final ModelLayerLocation TREASURE_CHEST = register("treasure_chest");

    private static ModelLayerLocation register(String name) {
        return register(name, "main");
    }

    private static ModelLayerLocation register(String name, String layer) {
        return new ModelLayerLocation(RottenCreatures.resource(name), layer);
    }
}