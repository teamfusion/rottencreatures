package com.github.teamfusion.rottencreatures.common.registries;

import com.blackgear.platform.core.helper.BlockEntityRegistry;
import com.blackgear.platform.core.helper.BlockEntityTypeBuilder;
import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import com.github.teamfusion.rottencreatures.common.level.blockentities.ZombieSkullBlockEntity;
import com.github.teamfusion.rottencreatures.common.level.blockentities.TreasureChestBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class RCBlockEntityTypes {
    public static final BlockEntityRegistry BLOCK_ENTITIES = BlockEntityRegistry.create(RottenCreatures.MOD_ID);

    public static final Supplier<BlockEntityType<TreasureChestBlockEntity>> TREASURE_CHEST = BLOCK_ENTITIES.register(
        "treasure_chest",
        BlockEntityTypeBuilder.create(
            TreasureChestBlockEntity::new,
            RCBlocks.TREASURE_CHEST
        )
    );

    public static final Supplier<BlockEntityType<ZombieSkullBlockEntity>> SKULL = BLOCK_ENTITIES.register(
        "zombie_skull",
        BlockEntityTypeBuilder.create(
            ZombieSkullBlockEntity::new,
            RCBlocks.BURNED_HEAD, RCBlocks.BURNED_WALL_HEAD,
            RCBlocks.FROSTBITTEN_HEAD, RCBlocks.FROSTBITTEN_WALL_HEAD,
            RCBlocks.SWAMPY_HEAD, RCBlocks.SWAMPY_WALL_HEAD,
            RCBlocks.UNDEAD_MINER_HEAD, RCBlocks.UNDEAD_MINER_WALL_HEAD,
            RCBlocks.MUMMY_HEAD, RCBlocks.MUMMY_WALL_HEAD,
            RCBlocks.GLACIAL_HUNTER_HEAD, RCBlocks.GLACIAL_HUNTER_WALL_HEAD,
            RCBlocks.DEAD_BEARD_HEAD, RCBlocks.DEAD_BEARD_WALL_HEAD,
            RCBlocks.IMMORTAL_HEAD, RCBlocks.IMMORTAL_WALL_HEAD,
            RCBlocks.ZAP_HEAD, RCBlocks.ZAP_WALL_HEAD
        )
    );
}