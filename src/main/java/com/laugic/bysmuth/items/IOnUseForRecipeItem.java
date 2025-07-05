package com.laugic.bysmuth.items;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public interface IOnUseForRecipeItem {
    public abstract void onUse(BlockPos pos, Level level);
    public abstract void onUseClient(BlockPos pos, Level level);
}
