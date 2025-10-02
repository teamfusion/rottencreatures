package com.github.teamfusion.rottencreatures.common.level.items;

import com.github.teamfusion.rottencreatures.common.level.blockentities.TreasureChestBlockEntity;
import com.github.teamfusion.rottencreatures.core.data.LangConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TreasureChestBlockItem extends BlockItem {
    public TreasureChestBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public boolean canFitInsideContainerItems() {
        return false;
    }

    public void displayTreasureChestContents(ItemStack stack, TooltipContext context, @Nullable Player player, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        // Get custom data from the item stack
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        if (data == null || data.isEmpty()) {
            return;
        }

        CompoundTag tag = data.copyTag();

        // Check for owner UUID
        UUID ownerUUID = null;
        if (tag.hasUUID(TreasureChestBlockEntity.TAG_OWNER_UUID)) {
            ownerUUID = tag.getUUID(TreasureChestBlockEntity.TAG_OWNER_UUID);
        }

        // Only show contents to the owner
        boolean isOwner = player != null && ownerUUID != null && player.getUUID().equals(ownerUUID);
        if (isOwner) {
            if (tag.contains(TreasureChestBlockEntity.TAG_ITEMS, Tag.TAG_LIST)) {
                ListTag itemsList = tag.getList(TreasureChestBlockEntity.TAG_ITEMS, Tag.TAG_COMPOUND);

                // Display up to 5 item types
                int displayedTypes = 0;
                for (int i = 0; i < itemsList.size(); i++) {
                    CompoundTag itemTag = itemsList.getCompound(i);
                    if (!itemTag.contains("id", Tag.TAG_STRING)) {
                        continue;
                    }

                    try {
                        int count = itemTag.contains("Count", Tag.TAG_INT) ? itemTag.getInt("Count") : 1;

                        Optional<ItemStack> parse = ItemStack.parse(context.registries(), itemTag);
                        if (parse.isPresent()) {
                            ItemStack item = parse.get();
                            if (!item.isEmpty()) {
                                displayedTypes++;

                                // Create tooltip with proper item name and count
                                MutableComponent itemText = item.getHoverName().copy();
                                itemText.append(" x").append(String.valueOf(count));
                                tooltip.add(itemText);
                            }
                        }
                    } catch (Exception ignored) {
                        // Skip problematic items
                    }
                }

                // Count remaining types for the "more" message
                int remainingTypes = 0;
                for (int i = displayedTypes; i < itemsList.size(); i++) {
                    CompoundTag itemTag = itemsList.getCompound(i);
                    if (itemTag.contains("id", Tag.TAG_STRING)) {
                        remainingTypes++;
                    }
                }

                // Only show the "more" message if we have additional types
                if (remainingTypes > 0) {
                    tooltip.add(Component.translatable("container.shulkerBox.more", remainingTypes)
                        .withStyle(ChatFormatting.ITALIC));
                }
            }
        } else {
            tooltip.add(Component.translatable(LangConstants.TREASURE_CHEST_HIDDEN_CONTENTS)
                .withStyle(ChatFormatting.GRAY));
        }
    }
}