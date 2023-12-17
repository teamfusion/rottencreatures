package com.github.teamfusion.rottencreatures.common.registries;

import com.github.teamfusion.rottencreatures.core.platform.CoreRegistry;
import com.github.teamfusion.rottencreatures.core.RottenCreatures;
import com.github.teamfusion.rottencreatures.common.blocks.TntBarrelBlock;
import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

import java.util.function.Function;
import java.util.function.Supplier;

public class RCBlocks {
    public static final CoreRegistry<Block> BLOCKS = CoreRegistry.create(Registry.BLOCK, RottenCreatures.MOD_ID);

    // ========== BLOCKS ===============================================================================================

    public static final Supplier<Block> TNT_BARREL = create(
            "tnt_barrel",
            () -> new TntBarrelBlock(
                    BlockBehaviour.Properties.of(Material.WOOD)
                            .strength(2.0F, 5.0F)
                            .sound(SoundType.WOOD)
            ), RottenCreatures.TAB
    );

    // ========== REGISTRY METHODS =====================================================================================

    private static <T extends Block> Supplier<T> create(String key, Supplier<T> block, CreativeModeTab tab) {
        return create(key, block, entry -> new BlockItem(entry.get(), new Item.Properties().tab(tab)));
    }

    private static <T extends Block> Supplier<T> create(String key, Supplier<T> block, Function<Supplier<T>, Item> item) {
        Supplier<T> entry = create(key, block);
        RCItems.ITEMS.register(key, () -> item.apply(entry));
        return entry;
    }

    private static <T extends Block> Supplier<T> create(String key, Supplier<T> block) {
        return BLOCKS.register(key, block);
    }
}