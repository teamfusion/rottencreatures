package com.github.teamfusion.rottencreatures.common.level.blocks;

import com.github.teamfusion.rottencreatures.common.level.blockentities.TreasureChestBlockEntity;
import com.github.teamfusion.rottencreatures.core.data.LangConstants;
import com.mojang.authlib.GameProfile;
import com.mojang.serialization.MapCodec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
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
import net.minecraft.world.level.storage.loot.LootParams;
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

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(TreasureChestBlock::new);
    }

    public TreasureChestBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(
            this.getStateDefinition().any()
                .setValue(ROTATION, 0)
                .setValue(OPEN, false)
        );
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
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
        if (level.getBlockEntity(pos) instanceof TreasureChestBlockEntity chest) {
            // Set owner if placer is a player
            if (placer instanceof Player player) {
                chest.setOwnerUUID(player.getUUID());
            }

            // Get custom data
            CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
            if (customData == null || customData.isEmpty()) {
                return;
            }

            CompoundTag tag = customData.copyTag();

            // Load items
            if (tag.contains(TreasureChestBlockEntity.TAG_ITEMS, Tag.TAG_LIST)) {
                ListTag itemsList = tag.getList(TreasureChestBlockEntity.TAG_ITEMS, Tag.TAG_COMPOUND);

                for (int i = 0; i < itemsList.size(); i++) {
                    CompoundTag itemTag = itemsList.getCompound(i);

                    if (!itemTag.contains("id", Tag.TAG_STRING)) {
                        continue;
                    }

                    try {
                        String id = itemTag.getString("id");
                        int count = itemTag.contains("Count", Tag.TAG_INT) ? itemTag.getInt("Count") : 1;

                        Item item = BuiltInRegistries.ITEM.get(ResourceLocation.parse(id));
                        ItemStack itemStack = new ItemStack(item, count);

                        // Apply custom data if present
                        if (itemTag.contains("tag", Tag.TAG_COMPOUND)) {
                            itemStack.set(DataComponents.CUSTOM_DATA, CustomData.of(itemTag.getCompound("tag")));
                        }

                        if (!itemStack.isEmpty()) {
                            chest.getItems().add(itemStack);
                        }
                    } catch (Exception ignored) {
                        // Silently skip problematic items
                    }
                }

                chest.recalculateTotalCount();
            }

            // Load owner UUID if not set by placer
            if (chest.getOwnerUUID() == null && tag.hasUUID(TreasureChestBlockEntity.TAG_OWNER_UUID)) {
                chest.setOwnerUUID(tag.getUUID(TreasureChestBlockEntity.TAG_OWNER_UUID));
            }

            chest.setChanged();
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
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return ItemInteractionResult.SUCCESS;
        }

        if (!(level.getBlockEntity(pos) instanceof TreasureChestBlockEntity chest)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        // Check if player is owner, if not display message
        if (!chest.isOwner(player.getUUID())) {
            UUID ownerUUID = chest.getOwnerUUID();
            if (ownerUUID != null && level.getServer() != null) {
                // Try to find the owner's name from the server
                String ownerName = level.getServer().getProfileCache().get(ownerUUID)
                    .map(GameProfile::getName)
                    .orElse("someone");

                player.displayClientMessage(Component.translatable(LangConstants.TREASURE_CHEST_CANNOT_OPEN, Component.literal(ownerName).withStyle(ChatFormatting.GOLD)), true);
            } else {
                player.displayClientMessage(Component.translatable(LangConstants.TREASURE_CHEST_LOCKED), true);
            }

            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        boolean isOpen = state.getValue(OPEN);

        // If chest is closed and player is opening it
        if (!isOpen) {
            // First open the chest
            level.setBlock(pos, state.setValue(OPEN, true), 3);
            level.playSound(null, pos, SoundEvents.CHEST_OPEN, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);

            // If it contains items, spit them out
            if (chest.hasContents()) {
                NonNullList<ItemStack> storedItems = chest.removeAllItems();
                for (ItemStack items : storedItems) {
                    spawnItemEntity(level, pos, items);
                }
            }
        }
        // If chest is already open
        else {
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof BlockItem block && !block.canFitInsideContainerItems()) {
                    return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
                }

                int amountToAdd = addItemsToContainer(chest, stack);

                // Get the replaced items when adding this amount
                NonNullList<ItemStack> replacedItems = chest.addItemsWithReplacement(stack.copy(), amountToAdd);

                // Spawn any replaced items
                for (ItemStack replacedItem : replacedItems) {
                    spawnItemEntity(level, pos, replacedItem);
                }

                // Always shrink by the amount we tried to add
                stack.shrink(amountToAdd);
                level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);

                return ItemInteractionResult.CONSUME;
            }
            // If player clicks with empty hand, close the chest
            else {
                level.setBlock(pos, state.setValue(OPEN, false), 3);
                level.playSound(null, pos, SoundEvents.CHEST_CLOSE, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
            }
        }

        player.awardStat(Stats.OPEN_CHEST);
        return ItemInteractionResult.CONSUME;
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
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        BlockEntity blockEntity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (blockEntity instanceof TreasureChestBlockEntity chest) {
            if (chest.hasContents()) {
                ItemStack drop = new ItemStack(this);
                CompoundTag customTag = new CompoundTag();
                ListTag itemsList = new ListTag();

                for (ItemStack stack : chest.getItems()) {
                    if (!stack.isEmpty()) {
                        CompoundTag itemTag = new CompoundTag();
                        itemTag.putString("id", BuiltInRegistries.ITEM.getKey(stack.getItem()).toString());
                        itemTag.putInt("Count", stack.getCount());

                        // Save custom data if present
                        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
                        if (data != null && !data.isEmpty()) {
                            itemTag.put("tag", data.copyTag());
                        }

                        itemsList.add(itemTag);
                    }
                }

                if (!itemsList.isEmpty()) {
                    customTag.put(TreasureChestBlockEntity.TAG_ITEMS, itemsList);
                }

                // Save owner UUID
                if (chest.getOwnerUUID() != null) {
                    customTag.putUUID(TreasureChestBlockEntity.TAG_OWNER_UUID, chest.getOwnerUUID());
                }

                // Set custom data
                if (!customTag.isEmpty()) {
                    drop.set(DataComponents.CUSTOM_DATA, CustomData.of(customTag));
                }

                return Collections.singletonList(drop);
            }
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
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof TreasureChestBlockEntity chest) {
            if ((player.isCreative() || chest.isOwner(player.getUUID())) && chest.hasContents()) {
                ItemStack itemStack = new ItemStack(this);
                CompoundTag customTag = new CompoundTag();

                // Handle the chest contents
                if (!chest.getItems().isEmpty()) {
                    ListTag itemsList = new ListTag();

                    for (ItemStack stack : chest.getItems()) {
                        if (!stack.isEmpty()) {
                            CompoundTag itemTag = new CompoundTag();
                            // Use registry key to get the proper item ID format
                            itemTag.putString("id", BuiltInRegistries.ITEM.getKey(stack.getItem()).toString());
                            itemTag.putInt("Count", stack.getCount());

                            // Save custom data if present
                            CustomData data = stack.get(DataComponents.CUSTOM_DATA);
                            if (data != null && !data.isEmpty()) {
                                itemTag.put("tag", data.copyTag());
                            }

                            itemsList.add(itemTag);
                        }
                    }

                    if (!itemsList.isEmpty()) {
                        customTag.put(TreasureChestBlockEntity.TAG_ITEMS, itemsList);
                    }
                }

                // Save owner UUID
                if (chest.getOwnerUUID() != null) {
                    customTag.putUUID(TreasureChestBlockEntity.TAG_OWNER_UUID, chest.getOwnerUUID());
                }

                // Set custom data on item stack
                if (!customTag.isEmpty()) {
                    itemStack.set(DataComponents.CUSTOM_DATA, CustomData.of(customTag));
                }

                // Drop the item
                ItemEntity itemEntity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, itemStack);
                itemEntity.setDefaultPickUpDelay();
                level.addFreshEntity(itemEntity);

                // Clear the chest
                chest.removeAllItems();
            }
        }
        return super.playerWillDestroy(level, pos, state, player);
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