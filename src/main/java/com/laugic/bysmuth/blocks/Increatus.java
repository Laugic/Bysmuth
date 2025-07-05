package com.laugic.bysmuth.blocks;

import com.laugic.bysmuth.recipes.BlockRecipe;
import com.laugic.bysmuth.recipes.ModRecipes;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import java.util.*;

public class Increatus extends Block {

    private static final Random random = new Random();
    public Increatus(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos,
                                Block blockFrom, BlockPos fromPos, boolean isMoving) {
        if (!level.isClientSide) {
            boolean isPowered = level.hasNeighborSignal(pos);
            if (isPowered) {
                BlockPos blockPos = pos.above();
                checkBlock(level, blockPos);
            }
        }
    }

    private void checkBlock(Level level, BlockPos pos){
        if(level.getBlockState(pos).getBlock() instanceof AnvilBlock)
            return;

        Optional<CraftingRecipe> foundRecipe = level.getRecipeManager()
                .getAllRecipesFor(RecipeType.CRAFTING)
                .stream()
                .filter(r -> r.getResultItem().is((new ItemStack(level.getBlockState(pos).getBlock())).getItem()))
                .findFirst();

        Optional<StonecutterRecipe> stone = level.getRecipeManager()
                .getAllRecipesFor(RecipeType.STONECUTTING)
                .stream()
                .filter(r -> r.getResultItem().is((new ItemStack(level.getBlockState(pos).getBlock())).getItem()))
                .findFirst();

        if (foundRecipe.isPresent() && !stone.isPresent())
        {
            int max = foundRecipe.get().getResultItem().getCount();
            for (Ingredient ingredient: foundRecipe.get().getIngredients()) {
                //for (ItemStack itemStack: ingredient.getItems()) {
                    if(random.nextInt(max) == 0 && random.nextBoolean())
                    {
                        ItemEntity itementity = new ItemEntity(level,
                                pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ingredient.getItems()[0].getItem()));
                        level.addFreshEntity(itementity);
                    }
                //}
            }
            level.destroyBlock(pos, false);
        }
    }
}
