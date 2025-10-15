package com.laugic.bysmuth.items;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class ModTiers {
    public static final ForgeTier ROSE_GOLD = new ForgeTier(2, 750, 16.0f, 2, 24, BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(ModItems.Rose_Gold.get()));
}
