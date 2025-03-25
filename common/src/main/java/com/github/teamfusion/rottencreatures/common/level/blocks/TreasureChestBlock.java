package com.github.teamfusion.rottencreatures.common.level.blocks;

import com.github.teamfusion.rottencreatures.common.level.blockentities.TreasureChestBlockEntity;
import com.github.teamfusion.rottencreatures.core.data.LangConstants;
import com.mojang.authlib.GameProfile;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class TreasureChestBlock extends BaseEntityBlock {
    private static final int MAX = 15;
    private static final int ROTATIONS = 16;
    public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    private static final VoxelShape SHAPE = TreasureChestBlock.box(3.0, 0.0, 3.0, 13.0, 7.0, 13.0);

    public TreasureChestBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(
            this.getStateDefinition().any()
                .setValue(ROTATION, 0)
                .setValue(OPEN, false)
        );
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        return false;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (placer instanceof Player player && blockEntity instanceof TreasureChestBlockEntity chest) {
            chest.setOwnerUUID(player.getUUID());

            // Transfer stored items from ItemStack if it has NBT data
            CompoundTag compoundTag = stack.getTagElement("BlockEntityTag");
            if (compoundTag != null && compoundTag.contains("Item")) {
                chest.load(compoundTag);
            }
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return canSupportCenter(level, pos.below(), Direction.UP);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        return direction == Direction.DOWN && !state.canSurvive(level, currentPos)
            ? Blocks.AIR.defaultBlockState()
            : super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        if (!canSupportCenter(context.getLevel(), context.getClickedPos().below(), Direction.UP)) {
            return null;
        }

        return this.defaultBlockState()
            .setValue(ROTATION, Mth.floor((double) (context.getRotation() * ROTATIONS / 360.0F) + 0.5) & MAX)
            .setValue(OPEN, false);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        if (!(level.getBlockEntity(pos) instanceof TreasureChestBlockEntity chest)) {
            return InteractionResult.PASS;
        }

        // Check if player is owner, if not display message
        if (!chest.isOwner(player.getUUID())) {
            UUID ownerUUID = chest.getOwnerUUID();
            if (ownerUUID != null && level.getServer() != null) {
                // Try to find the owner's name from the server
                String ownerName = level.getServer().getProfileCache().get(ownerUUID)
                    .map(GameProfile::getName)
                    .orElse("someone");

                player.displayClientMessage(Component.translatable(LangConstants.TREASURE_CHEST_CANNOT_OPEN, Component.literal(ownerName + "'s").withStyle(ChatFormatting.GOLD)), true);
            } else {
                player.displayClientMessage(Component.translatable(LangConstants.TREASURE_CHEST_LOCKED), true);
            }

            return InteractionResult.PASS;
        }

        boolean isOpen = state.getValue(OPEN);
        ItemStack heldItem = player.getItemInHand(hand);

        // If chest is closed and player is opening it
        if (!isOpen) {
            // First open the chest
            level.setBlock(pos, state.setValue(OPEN, true), 3);
            level.playSound(null, pos, SoundEvents.CHEST_OPEN, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);

            // If it contains items, spit them out
            if (chest.hasContents()) {
                NonNullList<ItemStack> storedItems = chest.removeAllItems();
                for (ItemStack stack : storedItems) {
                    spawnItemEntity(level, pos, stack);
                }
            }
        }
        // If chest is already open
        else {
            // If player is holding an item, try to store it
            if (!heldItem.isEmpty()) {
                if (heldItem.getItem() instanceof BlockItem block && !block.canFitInsideContainerItems()) {
                    return InteractionResult.PASS;
                }

                int amountToAdd = addItemsToContainer(chest, heldItem);

                // Get the replaced items when adding this amount
                NonNullList<ItemStack> replacedItems = chest.addItemsWithReplacement(heldItem.copy(), amountToAdd);

                // Spawn any replaced items
                for (ItemStack replacedItem : replacedItems) {
                    spawnItemEntity(level, pos, replacedItem);
                }

                // Always shrink by the amount we tried to add
                heldItem.shrink(amountToAdd);
                level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);

                return InteractionResult.CONSUME;
            }
            // If player clicks with empty hand, close the chest
            else {
                level.setBlock(pos, state.setValue(OPEN, false), 3);
                level.playSound(null, pos, SoundEvents.CHEST_CLOSE, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
            }
        }

        player.awardStat(Stats.OPEN_CHEST);
        return InteractionResult.CONSUME;
    }

    private static int addItemsToContainer(TreasureChestBlockEntity chest, ItemStack heldItem) {
        NonNullList<ItemStack> items = chest.getItems();
        int totalCount = 0;
        for (ItemStack item : items) {
            totalCount += item.getCount();
        }

        int maxStackSize = chest.maxStackSize;
        int spaceLeft = maxStackSize - totalCount;

        // Determine how many items we can add
        int amountToAdd;
        if (spaceLeft <= 0) {
            // Chest is full, replace everything
            amountToAdd = heldItem.getCount();
        } else {
            // Chest has space, only add what will fit
            amountToAdd = Math.min(spaceLeft, heldItem.getCount());
        }

        return amountToAdd;
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        BlockEntity blockEntity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (blockEntity instanceof TreasureChestBlockEntity chest) {
            ItemStack drop = new ItemStack(this);
            if (chest.hasContents()) {
                CompoundTag nbt = new CompoundTag();
                CompoundTag blockEntityNbt = new CompoundTag();
                chest.saveAdditional(blockEntityNbt);
                nbt.put("BlockEntityTag", blockEntityNbt);
                drop.setTag(nbt);
            }
            return Collections.singletonList(drop);
        }

        return super.getDrops(state, builder);
    }

    private void spawnItemEntity(Level level, BlockPos pos, ItemStack stack) {
        if (!stack.isEmpty()) {
            BlockState state = level.getBlockState(pos);
            int rotation = state.getValue(ROTATION);

            // Calculate direction vector based on rotation
            // Add 180 degrees to make items come out from the front of the chest
            double angle = Math.toRadians((rotation * 22.5F + 180) % 360);
            double offsetX = -Math.sin(angle) * 0.7;
            double offsetZ = Math.cos(angle) * 0.7;

            // Position items to come from inside the chest
            double x = pos.getX() + 0.5;
            double y = pos.getY() + 0.5;
            double z = pos.getZ() + 0.5;

            // Create the item entity
            ItemEntity itemEntity = new ItemEntity(level, x, y, z, stack);

            // Apply velocity in the direction the chest is facing
            double speed = 0.1 + level.random.nextDouble() * 0.05;
            itemEntity.setDeltaMovement(
                offsetX * speed,
                0.2 + level.random.nextDouble() * 0.1,
                offsetZ * speed
            );

            level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
            itemEntity.setDefaultPickUpDelay();
            level.addFreshEntity(itemEntity);
        }
    }

    @Override
    public float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof TreasureChestBlockEntity chest) {
            if (chest.isOwner(player.getUUID())) {
                return super.getDestroyProgress(state, player, level, pos);
            }
        }

        return 0.0F;
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof TreasureChestBlockEntity chest) {
            if (!level.isClientSide && player.isCreative() && chest.hasContents()) {
                ItemStack itemStack = new ItemStack(this);
                CompoundTag nbt = new CompoundTag();
                CompoundTag blockEntityNbt = new CompoundTag();
                chest.saveAdditional(blockEntityNbt);
                nbt.put("BlockEntityTag", blockEntityNbt);
                itemStack.setTag(nbt);

                ItemEntity itemEntity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, itemStack);
                itemEntity.setDefaultPickUpDelay();
                level.addFreshEntity(itemEntity);
            }
        }

        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(ROTATION, rotation.rotate(state.getValue(ROTATION), ROTATIONS));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(ROTATION, mirror.mirror(state.getValue(ROTATION), ROTATIONS));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ROTATION, OPEN);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos origin, BlockState state) {
        return new TreasureChestBlockEntity(origin, state);
    }
}