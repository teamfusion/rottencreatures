package com.github.teamfusion.rottencreatures.common.level.blockentities;

import com.github.teamfusion.rottencreatures.common.registries.RCBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.UUID;

public class TreasureChestBlockEntity extends BlockEntity {
    private static final int MAX_ITEMS = 64;
    private final NonNullList<ItemStack> items = NonNullList.create();
    private int totalCount = 0;
    private UUID ownerUUID;
    public int maxStackSize = MAX_ITEMS;

    public TreasureChestBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(RCBlockEntityTypes.TREASURE_CHEST.get(), blockPos, blockState);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ListTag itemsList = new ListTag();

        for (ItemStack stack : items) {
            CompoundTag itemTag = new CompoundTag();
            stack.save(itemTag);
            itemsList.add(itemTag);
        }

        tag.put("Items", itemsList);
        tag.putInt("ItemCount", totalCount);
        tag.putInt("MaxStackSize", maxStackSize);

        if (ownerUUID != null) {
            tag.putUUID("Owner", ownerUUID);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        items.clear();
        totalCount = 0;

        if (tag.contains("Items", Tag.TAG_LIST)) {
            ListTag itemsList = tag.getList("Items", Tag.TAG_COMPOUND);
            for (int i = 0; i < itemsList.size(); i++) {
                CompoundTag itemTag = itemsList.getCompound(i);
                ItemStack stack = ItemStack.of(itemTag);
                if (!stack.isEmpty()) {
                    items.add(stack);
                    totalCount += stack.getCount();
                }
            }
        } else if (tag.contains("Item", Tag.TAG_COMPOUND)) {
            // Legacy support
            ItemStack stack = ItemStack.of(tag.getCompound("Item"));
            if (!stack.isEmpty()) {
                items.add(stack);
                totalCount = stack.getCount();
            }
        }

        // Load or set the max stack size
        if (tag.contains("MaxStackSize")) {
            maxStackSize = tag.getInt("MaxStackSize");
        } else if (!items.isEmpty()) {
            // If no saved max stack size, but we have items, use the first item's stack size
            maxStackSize = Math.min(items.get(0).getMaxStackSize(), MAX_ITEMS);
        } else {
            maxStackSize = MAX_ITEMS; // Default
        }

        if (tag.hasUUID("Owner")) {
            ownerUUID = tag.getUUID("Owner");
        }
    }

    public NonNullList<ItemStack> getItems() {
        return items;
    }

    public boolean hasContents() {
        return !items.isEmpty() && totalCount != 0;
    }

    /**
     * Try to add an item to the chest, potentially replacing old items if needed
     * @param stack The stack to add
     * @param count How many to try to add
     * @return The items that were replaced, or empty if no replacement occurred
     */
    public NonNullList<ItemStack> addItemsWithReplacement(ItemStack stack, int count) {
        if (stack.isEmpty() || count <= 0) {
            return NonNullList.create();
        }

        NonNullList<ItemStack> replacedItems = NonNullList.create();
        int itemMaxStackSize = stack.getMaxStackSize();

        // If the chest is empty, set the maxStackSize based on the first item
        if (items.isEmpty()) {
            maxStackSize = itemMaxStackSize;
            addItem(stack, Math.min(count, maxStackSize));
            return replacedItems; // Nothing replaced
        }

        // If we're trying to add an item with a different max stack size, replace all items
        if (items.get(0).getMaxStackSize() != itemMaxStackSize) {
            replacedItems.addAll(removeAllItems());
            maxStackSize = itemMaxStackSize;
            addItem(stack, Math.min(count, maxStackSize));
            return replacedItems;
        }

        int toAdd = Math.min(count, maxStackSize);
        int spaceLeft = maxStackSize - totalCount;

        // If there's space available, just add without replacing
        if (spaceLeft >= toAdd) {
            // Try to merge with existing stacks first
            for (int i = 0; i < items.size() && toAdd > 0; i++) {
                ItemStack existingStack = items.get(i);
                if (ItemStack.isSameItemSameTags(existingStack, stack)) {
                    int canAdd = Math.min(toAdd, existingStack.getMaxStackSize() - existingStack.getCount());
                    if (canAdd > 0) {
                        existingStack.grow(canAdd);
                        toAdd -= canAdd;
                        totalCount += canAdd;
                        this.setChanged();
                    }
                }
            }

            // Add as new stacks if needed
            if (toAdd > 0) {
                addItem(stack, toAdd);
            }
        }

        // No space available - need to replace items
        else {
            // First try to replace non-matching items (FIFO)
            int itemsReplaced = 0;
            int remainingToAdd = toAdd;

            // First pass: try to replace non-matching items
            for (int i = 0; i < items.size() && itemsReplaced < toAdd && remainingToAdd > 0; i++) {
                ItemStack oldStack = items.get(i);

                // Skip items that match what we're adding
                if (ItemStack.isSameItemSameTags(oldStack, stack)) {
                    continue;
                }

                int toRemove = Math.min(oldStack.getCount(), remainingToAdd);

                if (toRemove == oldStack.getCount()) {
                    // Remove the entire stack
                    ItemStack replacedStack = oldStack.copy();
                    items.remove(i);
                    i--; // Adjust index since we removed an item
                    replacedItems.add(replacedStack);
                    totalCount -= toRemove;
                } else {
                    // Remove part of the stack
                    ItemStack replacedStack = oldStack.copy();
                    replacedStack.setCount(toRemove);
                    replacedItems.add(replacedStack);
                    oldStack.shrink(toRemove);
                    totalCount -= toRemove;
                }

                itemsReplaced += toRemove;
                remainingToAdd -= toRemove;
            }

            // If we still need to replace more items, now replace matching items (FIFO)
            if (remainingToAdd > 0) {
                while (totalCount + remainingToAdd > maxStackSize && !items.isEmpty()) {
                    ItemStack oldStack = items.get(0);
                    int toRemove = Math.min(oldStack.getCount(), totalCount + remainingToAdd - maxStackSize);

                    if (toRemove == oldStack.getCount()) {
                        // Remove the entire stack
                        items.remove(0);
                        replacedItems.add(oldStack);
                        totalCount -= toRemove;
                    } else {
                        // Remove part of the stack
                        ItemStack replacedStack = oldStack.copy();
                        replacedStack.setCount(toRemove);
                        replacedItems.add(replacedStack);
                        oldStack.shrink(toRemove);
                        totalCount -= toRemove;
                    }

                    itemsReplaced += toRemove;
                    remainingToAdd -= toRemove;
                }
            }

            // Now add the new items - first try to merge with existing
            remainingToAdd = toAdd;
            for (int i = 0; i < items.size() && remainingToAdd > 0; i++) {
                ItemStack existingStack = items.get(i);
                if (ItemStack.isSameItemSameTags(existingStack, stack)) {
                    int canAdd = Math.min(remainingToAdd, existingStack.getMaxStackSize() - existingStack.getCount());
                    if (canAdd > 0) {
                        existingStack.grow(canAdd);
                        remainingToAdd -= canAdd;
                        totalCount += canAdd;
                        this.setChanged();
                    }
                }
            }

            // Add remaining as new stack if needed
            if (remainingToAdd > 0) {
                addItem(stack, remainingToAdd);
            }
        }

        // Clean up empty stacks
        items.removeIf(ItemStack::isEmpty);

        return replacedItems;
    }

    private void addItem(ItemStack stack, int count) {
        int itemMaxStackSize = stack.getMaxStackSize();
        int toAdd = count;

        // For items with maxStackSize=1, we need to add multiple separate stacks
        while (toAdd > 0) {
            ItemStack newStack = stack.copy();
            int stackSize = Math.min(toAdd, itemMaxStackSize);
            newStack.setCount(stackSize);
            items.add(newStack);
            totalCount += stackSize;
            toAdd -= stackSize;
        }
        this.setChanged();
    }

    public NonNullList<ItemStack> removeAllItems() {
        NonNullList<ItemStack> result = NonNullList.create();
        result.addAll(items);
        items.clear();
        totalCount = 0;
        maxStackSize = MAX_ITEMS; // Reset max stack size when emptied
        this.setChanged();
        return result;
    }

    public UUID getOwnerUUID() {
        return ownerUUID;
    }

    public void setOwnerUUID(UUID uuid) {
        this.ownerUUID = uuid;
        this.setChanged();
    }

    public boolean isOwner(UUID playerUUID) {
        return ownerUUID != null && ownerUUID.equals(playerUUID);
    }
}