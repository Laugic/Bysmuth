package com.laugic.bysmuth.blocks;

import com.laugic.bysmuth.recipes.AdvancedManualApplicationRecipe;
import com.laugic.bysmuth.recipes.BlockRecipe;
import com.laugic.bysmuth.recipes.ModRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import java.util.Optional;
import java.util.Random;

public interface BlockRecipeBlock  {

    default void checkBlock(ServerLevel level, BlockPos self, BlockPos out){
        RecipeType<Recipe<RecipeWrapper>> TYPE = ModRecipes.BLOCK_RECIPE.getType();
        Optional<Recipe<RecipeWrapper>> foundRecipe = level.getRecipeManager()
                .getAllRecipesFor(TYPE)
                .stream()
                .filter(r -> {
                    BlockRecipe br = (BlockRecipe) r;
                    return br.testBlocks(level, self, out);
                })
                .findFirst();

        if (foundRecipe.isPresent())
            ((BlockRecipe) foundRecipe.get()).transformBlock(level, self, out);
    }
}
