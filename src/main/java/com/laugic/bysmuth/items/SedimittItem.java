package com.laugic.bysmuth.items;

import com.laugic.bysmuth.blocks.Sedimitt;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;

public class SedimittItem extends BlockItem {
    public SedimittItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Block belowBlock = context.getLevel().getBlockState(context.getClickedPos()).getBlock();

        if(!Sedimitt.BLOCK_VALUES.containsKey(belowBlock))
            return InteractionResult.FAIL;

        return super.useOn(context);
    }
}
