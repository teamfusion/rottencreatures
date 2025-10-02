package com.github.teamfusion.rottencreatures.common.registries;

import com.blackgear.platform.core.helper.BlockRegistry;
import com.github.teamfusion.rottencreatures.common.level.blocks.*;
import com.github.teamfusion.rottencreatures.common.level.items.TreasureChestBlockItem;
import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Supplier;

public class RCBlocks {
    public static final BlockRegistry BLOCKS = BlockRegistry.create(RottenCreatures.MOD_ID);

    public static final Supplier<Block> TNT_BARREL = BLOCKS.register(
        "tnt_barrel",
        TntBarrelBlock::new,
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .strength(2.0F, 5.0F)
            .sound(SoundType.WOOD)
            .ignitedByLava(),
        BlockItem::new,
        new Item.Properties()
    );

    public static final Supplier<Block> TREASURE_CHEST = BLOCKS.register(
        "treasure_chest",
        TreasureChestBlock::new,
        BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .strength(2.0F, 1200.0F)
            .sound(SoundType.WOOD),
        TreasureChestBlockItem::new,
        new Item.Properties()
            .rarity(Rarity.RARE)
            .stacksTo(1)
    );

    public static final Supplier<Block> BURNED_WALL_HEAD = BLOCKS.registerNoItem(
        "burned_wall_head",
        properties -> new ZombieWallSkullBlock(SkullTypes.BURNED, properties),
        BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.ZOMBIE).strength(1.0F).pushReaction(PushReaction.DESTROY)
    );
    public static final Supplier<Block> BURNED_HEAD = BLOCKS.register(
        "burned_head",
        properties -> new ZombieSkullBlock(SkullTypes.BURNED, properties),
        BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.ZOMBIE).strength(1.0F).pushReaction(PushReaction.DESTROY),
        "burned_head",
        (block, properties) -> new StandingAndWallBlockItem(block, BURNED_WALL_HEAD.get(), properties, Direction.DOWN),
        new Item.Properties().rarity(Rarity.UNCOMMON)
    );
    public static final Supplier<Block> FROSTBITTEN_WALL_HEAD = BLOCKS.registerNoItem(
        "frostbitten_wall_head",
        properties -> new ZombieWallSkullBlock(SkullTypes.FROSTBITTEN, properties),
        BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.ZOMBIE).strength(1.0F).pushReaction(PushReaction.DESTROY)
    );
    public static final Supplier<Block> FROSTBITTEN_HEAD = BLOCKS.register(
        "frostbitten_head",
        properties -> new ZombieSkullBlock(SkullTypes.FROSTBITTEN, properties),
        BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.ZOMBIE).strength(1.0F).pushReaction(PushReaction.DESTROY),
        "frostbitten_head",
        (block, properties) -> new StandingAndWallBlockItem(block, FROSTBITTEN_WALL_HEAD.get(), properties, Direction.DOWN),
        new Item.Properties().rarity(Rarity.UNCOMMON)
    );
    public static final Supplier<Block> SWAMPY_WALL_HEAD = BLOCKS.registerNoItem(
        "swampy_wall_head",
        properties -> new ZombieWallSkullBlock(SkullTypes.SWAMPY, properties),
        BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.ZOMBIE).strength(1.0F).pushReaction(PushReaction.DESTROY)
    );
    public static final Supplier<Block> SWAMPY_HEAD = BLOCKS.register(
        "swampy_head",
        properties -> new ZombieSkullBlock(SkullTypes.SWAMPY, properties),
        BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.ZOMBIE).strength(1.0F).pushReaction(PushReaction.DESTROY),
        "swampy_head",
        (block, properties) -> new StandingAndWallBlockItem(block, SWAMPY_WALL_HEAD.get(), properties, Direction.DOWN),
        new Item.Properties().rarity(Rarity.UNCOMMON)
    );
    public static final Supplier<Block> UNDEAD_MINER_WALL_HEAD = BLOCKS.registerNoItem(
        "undead_miner_wall_head",
        properties -> new ZombieWallSkullBlock(SkullTypes.UNDEAD_MINER, properties),
        BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.ZOMBIE).strength(1.0F).pushReaction(PushReaction.DESTROY)
    );
    public static final Supplier<Block> UNDEAD_MINER_HEAD = BLOCKS.register(
        "undead_miner_head",
        properties -> new ZombieSkullBlock(SkullTypes.UNDEAD_MINER, properties),
        BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.ZOMBIE).strength(1.0F).pushReaction(PushReaction.DESTROY),
        "undead_miner_head",
        (block, properties) -> new StandingAndWallBlockItem(block, UNDEAD_MINER_WALL_HEAD.get(), properties, Direction.DOWN),
        new Item.Properties().rarity(Rarity.UNCOMMON)
    );
    public static final Supplier<Block> MUMMY_WALL_HEAD = BLOCKS.registerNoItem(
        "mummy_wall_head",
        properties -> new ZombieWallSkullBlock(SkullTypes.MUMMY, properties),
        BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.ZOMBIE).strength(1.0F).pushReaction(PushReaction.DESTROY)
    );
    public static final Supplier<Block> MUMMY_HEAD = BLOCKS.register(
        "mummy_head",
        properties -> new ZombieSkullBlock(SkullTypes.MUMMY, properties),
        BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.ZOMBIE).strength(1.0F).pushReaction(PushReaction.DESTROY),
        "mummy_head",
        (block, properties) -> new StandingAndWallBlockItem(block, MUMMY_WALL_HEAD.get(), properties, Direction.DOWN),
        new Item.Properties().rarity(Rarity.UNCOMMON)
    );
    public static final Supplier<Block> GLACIAL_HUNTER_WALL_HEAD = BLOCKS.registerNoItem(
        "glacial_hunter_wall_head",
        properties -> new ZombieWallSkullBlock(SkullTypes.GLACIAL_HUNTER, properties),
        BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.ZOMBIE).strength(1.0F).pushReaction(PushReaction.DESTROY)
    );
    public static final Supplier<Block> GLACIAL_HUNTER_HEAD = BLOCKS.register(
        "glacial_hunter_head",
        properties -> new ZombieSkullBlock(SkullTypes.GLACIAL_HUNTER, properties),
        BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.ZOMBIE).strength(1.0F).pushReaction(PushReaction.DESTROY),
        "glacial_hunter_head",
        (block, properties) -> new StandingAndWallBlockItem(block, GLACIAL_HUNTER_WALL_HEAD.get(), properties, Direction.DOWN),
        new Item.Properties().rarity(Rarity.UNCOMMON)
    );
    public static final Supplier<Block> DEAD_BEARD_WALL_HEAD = BLOCKS.registerNoItem(
        "dead_beard_wall_head",
        properties -> new ZombieWallSkullBlock(SkullTypes.DEAD_BEARD, properties),
        BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.ZOMBIE).strength(1.0F).pushReaction(PushReaction.DESTROY)
    );
    public static final Supplier<Block> DEAD_BEARD_HEAD = BLOCKS.register(
        "dead_beard_head",
        properties -> new ZombieSkullBlock(SkullTypes.DEAD_BEARD, properties),
        BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.ZOMBIE).strength(1.0F).pushReaction(PushReaction.DESTROY),
        "dead_beard_head",
        (block, properties) -> new StandingAndWallBlockItem(block, DEAD_BEARD_WALL_HEAD.get(), properties, Direction.DOWN),
        new Item.Properties().rarity(Rarity.UNCOMMON)
    );
    public static final Supplier<Block> IMMORTAL_WALL_HEAD = BLOCKS.registerNoItem(
        "immortal_wall_head",
        properties -> new ZombieWallSkullBlock(SkullTypes.IMMORTAL, properties),
        BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.ZOMBIE).strength(1.0F).pushReaction(PushReaction.DESTROY)
    );
    public static final Supplier<Block> IMMORTAL_HEAD = BLOCKS.register(
        "immortal_head",
        properties -> new ZombieSkullBlock(SkullTypes.IMMORTAL, properties),
        BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.ZOMBIE).strength(1.0F).pushReaction(PushReaction.DESTROY),
        "immortal_head",
        (block, properties) -> new StandingAndWallBlockItem(block, IMMORTAL_WALL_HEAD.get(), properties, Direction.DOWN),
        new Item.Properties().rarity(Rarity.UNCOMMON)
    );
    public static final Supplier<Block> ZAP_WALL_HEAD = BLOCKS.registerNoItem(
        "zap_wall_head",
        properties -> new ZombieWallSkullBlock(SkullTypes.ZAP, properties),
        BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.ZOMBIE).strength(1.0F).pushReaction(PushReaction.DESTROY)
    );
    public static final Supplier<Block> ZAP_HEAD = BLOCKS.register(
        "zap_head",
        properties -> new ZombieSkullBlock(SkullTypes.ZAP, properties),
        BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.ZOMBIE).strength(1.0F).pushReaction(PushReaction.DESTROY),
        "zap_head",
        (block, properties) -> new StandingAndWallBlockItem(block, ZAP_WALL_HEAD.get(), properties, Direction.DOWN),
        new Item.Properties().rarity(Rarity.UNCOMMON)
    );
}