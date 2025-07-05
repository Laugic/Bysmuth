package com.laugic.bysmuth.recipes;

import com.laugic.bysmuth.items.IOnUseForRecipeItem;
import com.laugic.bysmuth.util.BlockHelper;
import net.minecraft.core.BlockPos;
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
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import java.util.List;
import java.util.Optional;

@EventBusSubscriber
public class AdvancedManualApplicationRecipe extends ItemApplicationRecipe {

    @SubscribeEvent
    public static void manualApplicationRecipesApplyInWorld(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getWorld();
        ItemStack heldItem = event.getItemStack();
        BlockPos pos = event.getPos();
        BlockState blockState = level.getBlockState(pos);

        if (heldItem.isEmpty())
            return;
        if (blockState.isAir())
            return;
        if (event.isCanceled())
            return;

        RecipeType<Recipe<RecipeWrapper>> type = ModRecipes.ADVANCED_ITEM_APPLICATION.getType();
        Optional<Recipe<RecipeWrapper>> foundRecipe = level.getRecipeManager()
                .getAllRecipesFor(type)
                .stream()
                .filter(r -> {
                    AdvancedManualApplicationRecipe mar = (AdvancedManualApplicationRecipe) r;
                    return mar.testBlocks(level, pos) && mar.ingredients.get(0)
                            .test(heldItem);
                })
                .findFirst();

        if (foundRecipe.isEmpty())
            return;

        event.setCancellationResult(InteractionResult.SUCCESS);
        event.setCanceled(true);

        if (level.isClientSide())
        {
            if(heldItem.getItem() instanceof IOnUseForRecipeItem item)
                item.onUseClient(pos, level);
            return;
        }

        AdvancedManualApplicationRecipe recipe = (AdvancedManualApplicationRecipe) foundRecipe.get();

        breakBlocks(recipe, level, pos);

        if(heldItem.getItem() instanceof IOnUseForRecipeItem item)
            item.onUse(pos, level);
/*
        BlockState transformedBlock = recipe.transformBlock(blockState);
        level.setBlock(pos, transformedBlock, 3);
        recipe.rollResults()
                .forEach(stack -> Block.popResource(level, pos, stack));
*/
        recipe.rollResults()
                .forEach(stack -> {
                    ItemEntity itementity = new ItemEntity(level,
                            pos.getX(), pos.getY(), pos.getZ(), stack);

                    level.addFreshEntity(itementity);
        });

        boolean creative = event.getPlayer() != null && event.getPlayer()
                .isCreative();
        boolean unbreakable = heldItem.hasTag() && heldItem.getTag()
                .getBoolean("Unbreakable");
        boolean keepHeld = recipe.shouldKeepHeldItem() || creative;

        if (!unbreakable && !keepHeld) {
            if (heldItem.isDamageableItem())
                heldItem.hurtAndBreak(1, event.getPlayer(), s -> s.broadcastBreakEvent(InteractionHand.MAIN_HAND));
            else
                heldItem.shrink(1);
        }
    }

    public AdvancedManualApplicationRecipe(ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        super(ModRecipes.ADVANCED_ITEM_APPLICATION, params);
    }

    public boolean testBlocks(Level level, BlockPos pos) {
        boolean test = ingredients.get(1)
                .test(new ItemStack(level.getBlockState(pos).getBlock()
                        .asItem()));
        if(ingredients.size() > 2 && test)
            test = ingredients.get(2)
                    .test(new ItemStack(level.getBlockState(pos.above()).getBlock()
                            .asItem()));
        if(ingredients.size() > 3 && test)
            test = ingredients.get(3)
                    .test(new ItemStack(level.getBlockState(pos.below()).getBlock()
                            .asItem()));
        if(ingredients.size() > 4 && test)
        {
            test = (ingredients.get(4)
                    .test(new ItemStack(level.getBlockState(pos.north()).getBlock()
                            .asItem()))
                    &&
                    ingredients.get(4)
                    .test(new ItemStack(level.getBlockState(pos.south()).getBlock()
                            .asItem()))
                    ) ||
                    (ingredients.get(4)
                    .test(new ItemStack(level.getBlockState(pos.east()).getBlock()
                            .asItem()))
                    &&
                    ingredients.get(4)
                    .test(new ItemStack(level.getBlockState(pos.west()).getBlock()
                            .asItem()))
                    );
        }
        return test;
    }

    public static void breakBlocks(AdvancedManualApplicationRecipe foundRecipe, Level level, BlockPos pos) {
        boolean test = foundRecipe.ingredients.get(1)
                .test(new ItemStack(level.getBlockState(pos).getBlock()
                        .asItem()));
        if(test)
            level.destroyBlock(pos, false);

        if(foundRecipe.ingredients.size() > 2)
            level.destroyBlock(pos.above(), false);

        if(foundRecipe.ingredients.size() > 3)
            level.destroyBlock(pos.below(), false);

        if(foundRecipe.ingredients.size() > 4)
        {
            boolean posTest = (foundRecipe.ingredients.get(4)
                    .test(new ItemStack(level.getBlockState(pos.north()).getBlock()
                            .asItem()))
                    &&
                    foundRecipe.ingredients.get(4)
                            .test(new ItemStack(level.getBlockState(pos.south()).getBlock()
                                    .asItem()))
                    );
            if(posTest)
            {
                level.destroyBlock(pos.north(), false);
                level.destroyBlock(pos.south(), false);
            }
            else
            {
                posTest = (foundRecipe.ingredients.get(4)
                        .test(new ItemStack(level.getBlockState(pos.east()).getBlock()
                                .asItem()))
                        &&
                        foundRecipe.ingredients.get(4)
                                .test(new ItemStack(level.getBlockState(pos.west()).getBlock()
                                        .asItem()))
                );
                if(posTest)
                {
                    level.destroyBlock(pos.east(), false);
                    level.destroyBlock(pos.west(), false);
                }
            }
        }
    }

//    public static void breakBlocks(AdvancedManualApplicationRecipe foundRecipe, Level level, BlockPos pos) {
//        boolean test = foundRecipe.ingredients.get(1)
//                .test(new ItemStack(level.getBlockState(pos).getBlock()
//                        .asItem()));
//        if(test)
//            level.destroyBlock(pos, false);
//
//        if(foundRecipe.ingredients.size() > 2)
//            test = foundRecipe.ingredients.get(2)
//                    .test(new ItemStack(level.getBlockState(pos.above()).getBlock()
//                            .asItem()));
//
//        if(test)
//            level.destroyBlock(pos.above(), false);
//
//        if(foundRecipe.ingredients.size() > 3 && test)
//            test = foundRecipe.ingredients.get(3)
//                    .test(new ItemStack(level.getBlockState(pos.below()).getBlock()
//                            .asItem()));
//
//        if(test)
//            level.destroyBlock(pos.below(), false);
//
//        if(foundRecipe.ingredients.size() > 4 && test)
//        {
//            boolean posTest = (foundRecipe.ingredients.get(4)
//                    .test(new ItemStack(level.getBlockState(pos.north()).getBlock()
//                            .asItem()))
//                    &&
//                    foundRecipe.ingredients.get(4)
//                            .test(new ItemStack(level.getBlockState(pos.south()).getBlock()
//                                    .asItem()))
//                    );
//            if(posTest)
//            {
//                level.destroyBlock(pos.north(), false);
//                level.destroyBlock(pos.south(), false);
//            }
//            else
//            {
//                posTest = (foundRecipe.ingredients.get(4)
//                        .test(new ItemStack(level.getBlockState(pos.east()).getBlock()
//                                .asItem()))
//                        &&
//                        foundRecipe.ingredients.get(4)
//                                .test(new ItemStack(level.getBlockState(pos.west()).getBlock()
//                                        .asItem()))
//                );
//                if(posTest)
//                {
//                    level.destroyBlock(pos.east(), false);
//                    level.destroyBlock(pos.west(), false);
//                }
//            }
//        }
//    }

    public BlockState transformBlock(BlockState in) {
        ProcessingOutput mainOutput = results.get(0);
        ItemStack output = mainOutput.rollOutput();
        if (output.getItem() instanceof BlockItem bi)
            return BlockHelper.copyProperties(in, bi.getBlock()
                    .defaultBlockState());
        return Blocks.AIR.defaultBlockState();
    }

    @Override
    public List<ItemStack> rollResults() {
        return rollResults(getRollableResultsExceptBlock());
    }

    public List<ProcessingOutput> getRollableResultsExceptBlock() {
//        ProcessingOutput mainOutput = results.get(0);
//        if (mainOutput.getStack()
//                .getItem() instanceof BlockItem)
//            return results.subList(1, results.size());
        return results;
    }

}
