package com.github.teamfusion.rottencreatures.common.level.blockentities;

import com.github.teamfusion.rottencreatures.common.registries.RCBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;
import java.util.UUID;

public class TreasureChestBlockEntity extends BlockEntity {
    public static final int MAX_ITEMS = 64;
    public static final String TAG_ITEMS = "Items";
    public static final String TAG_OWNER_UUID = "OwnerUUID";
    public static final String TAG_MAX_STACK_SIZE = "MaxStackSize";
    private NonNullList<ItemStack> items = NonNullList.create();
    private int totalCount = 0;
    private UUID ownerUUID;
    public int maxStackSize = MAX_ITEMS;

    public TreasureChestBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(RCBlockEntityTypes.TREASURE_CHEST.get(), blockPos, blockState);
    }

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveAdditional(tag, provider);

        // Save items directly as a list tag like in your original implementation
        if (!this.items.isEmpty()) {
            ListTag itemsList = new ListTag();
            for (ItemStack stack : this.items) {
                CompoundTag itemTag = new CompoundTag();
                stack.save(provider, itemTag);
                itemsList.add(itemTag);
            }
            tag.put(TAG_ITEMS, itemsList);
        }

        // Save other data
        if (this.ownerUUID != null) {
            tag.putUUID(TAG_OWNER_UUID, this.ownerUUID);
        }
        if (this.maxStackSize != MAX_ITEMS) {
            tag.putInt(TAG_MAX_STACK_SIZE, this.maxStackSize);
        }

        tag.putInt("ItemCount", totalCount); // Save the total count for backward compatibility
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.items = NonNullList.create();

        // Load items from list tag like in your original implementation
        if (tag.contains(TAG_ITEMS, Tag.TAG_LIST)) {
            ListTag itemsList = tag.getList(TAG_ITEMS, Tag.TAG_COMPOUND);
            for (int i = 0; i < itemsList.size(); i++) {
                CompoundTag itemTag = itemsList.getCompound(i);
                Optional<ItemStack> stack = ItemStack.parse(registries, itemTag);
                stack.ifPresent(itemStack -> this.items.add(itemStack));
            }
        }

        // Load other data
        if (tag.hasUUID(TAG_OWNER_UUID)) {
            this.ownerUUID = tag.getUUID(TAG_OWNER_UUID);
        }

        // Load max stack size with appropriate fallbacks
        if (tag.contains(TAG_MAX_STACK_SIZE, Tag.TAG_INT) && !this.items.isEmpty()) {
            this.maxStackSize = tag.getInt(TAG_MAX_STACK_SIZE);
        } else if (!this.items.isEmpty()) {
            this.maxStackSize = this.items.getFirst().getMaxStackSize();
        } else {
            this.maxStackSize = MAX_ITEMS;
        }

        this.recalculateTotalCount();
    }

    public void recalculateTotalCount() {
        this.totalCount = 0;
        for (ItemStack stack : this.items) {
            this.totalCount += stack.getCount();
        }
    }

    public NonNullList<ItemStack> getItems() {
        return items;
    }

    public boolean hasContents() {
        return !items.isEmpty() && totalCount != 0;
    }

    public NonNullList<ItemStack> addItemsWithReplacement(ItemStack stack, int count) {
        if (stack.isEmpty() || count <= 0) {
            return NonNullList.create();
        }

        NonNullList<ItemStack> replacedItems = NonNullList.create();
        int itemMaxStackSize = stack.getMaxStackSize();

        if (items.isEmpty()) {
            maxStackSize = itemMaxStackSize;
            addItem(stack, Math.min(count, maxStackSize));
            recalculateTotalCount();
            this.setChanged();
            return replacedItems;
        }

        if (items.get(0).getMaxStackSize() != itemMaxStackSize) {
            replacedItems.addAll(removeAllItems());
            maxStackSize = itemMaxStackSize;
            addItem(stack, Math.min(count, maxStackSize));
            recalculateTotalCount();
            this.setChanged();
            return replacedItems;
        }

        int toAdd = Math.min(count, maxStackSize);
        int spaceLeft = maxStackSize - totalCount;

        if (spaceLeft >= toAdd) {
            int added = 0;
            for (int i = 0; i < items.size() && toAdd > 0; i++) {
                ItemStack existingStack = items.get(i);
                if (ItemStack.isSameItemSameComponents(existingStack, stack)) {
                    int canAdd = Math.min(toAdd, existingStack.getMaxStackSize() - existingStack.getCount());
                    if (canAdd > 0) {
                        existingStack.grow(canAdd);
                        toAdd -= canAdd;
                        added += canAdd;
                    }
                }
            }
            totalCount += added;

            if (toAdd > 0) {
                addItem(stack, toAdd);
            }
            this.setChanged();
        } else {
            int itemsToReplaceCount = toAdd - spaceLeft;
            int currentReplacedCount = 0;
            NonNullList<ItemStack> actuallyReplaced = NonNullList.create();

            for (int i = 0; i < items.size() && currentReplacedCount < itemsToReplaceCount; ) {
                ItemStack oldStack = items.get(i);
                if (ItemStack.isSameItemSameComponents(oldStack, stack)) {
                    i++;
                    continue;
                }

                int canRemove = Math.min(oldStack.getCount(), itemsToReplaceCount - currentReplacedCount);
                ItemStack removedPortion = oldStack.split(canRemove);
                actuallyReplaced.add(removedPortion);
                currentReplacedCount += canRemove;
                totalCount -= canRemove;

                if (oldStack.isEmpty()) {
                    items.remove(i);
                } else {
                    i++;
                }
            }

            for (int i = 0; i < items.size() && currentReplacedCount < itemsToReplaceCount; ) {
                ItemStack oldStack = items.get(i);
                int canRemove = Math.min(oldStack.getCount(), itemsToReplaceCount - currentReplacedCount);
                ItemStack removedPortion = oldStack.split(canRemove);
                actuallyReplaced.add(removedPortion);
                currentReplacedCount += canRemove;
                totalCount -= canRemove;

                if (oldStack.isEmpty()) {
                    items.remove(i);
                } else {
                    i++;
                }
            }

            int added = 0;
            for (int i = 0; i < items.size() && toAdd > 0; i++) {
                ItemStack existingStack = items.get(i);
                if (ItemStack.isSameItemSameComponents(existingStack, stack)) {
                    int canAdd = Math.min(toAdd, existingStack.getMaxStackSize() - existingStack.getCount());
                    if (canAdd > 0) {
                        existingStack.grow(canAdd);
                        toAdd -= canAdd;
                        added += canAdd;
                    }
                }
            }
            totalCount += added;

            if (toAdd > 0) {
                addItem(stack, toAdd);
            }

            replacedItems.addAll(actuallyReplaced);
            this.setChanged();
        }

        items.removeIf(ItemStack::isEmpty);
        recalculateTotalCount();

        return replacedItems;
    }

    private void addItem(ItemStack stack, int count) {
        if (stack.isEmpty() || count <= 0) return;

        int itemMaxStackSize = stack.getMaxStackSize();
        int toAdd = count;

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
        maxStackSize = MAX_ITEMS;
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