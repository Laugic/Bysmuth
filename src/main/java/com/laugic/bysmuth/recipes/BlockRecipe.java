package com.laugic.bysmuth.recipes;

import com.laugic.bysmuth.items.IOnUseForRecipeItem;
import com.laugic.bysmuth.util.BlockHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import java.util.List;
import java.util.Optional;

//From Create https://github.com/Creators-of-Create/Create
@EventBusSubscriber
public class BlockRecipe extends ItemApplicationRecipe {

    public BlockRecipe(ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        super(ModRecipes.BLOCK_RECIPE, params);
    }

    public boolean testBlocks(Level level, BlockPos self, BlockPos pos) {
        return ingredients.get(0)
                .test(new ItemStack(level.getBlockState(self).getBlock()
                        .asItem())) && ingredients.get(1).test(new ItemStack(level.getBlockState(pos).getBlock()
                .asItem()));
    }

    public void transformBlock(Level level, BlockPos self, BlockPos out) {
        ProcessingOutput output = results.get(0);
        if (output.stack.getItem() instanceof BlockItem blockItem)
        {
            level.destroyBlock(out, false);
            level.setBlock(out, blockItem.getBlock().defaultBlockState(), 3);
        }
    }

    @Override
    public List<ItemStack> rollResults() {
        return rollResults(getRollableResultsExceptBlock());
    }

    public List<ProcessingOutput> getRollableResultsExceptBlock() {
        ProcessingOutput mainOutput = results.get(0);
        if (mainOutput.getStack()
                .getItem() instanceof BlockItem)
            return results.subList(1, results.size());
        return results;
    }
}
