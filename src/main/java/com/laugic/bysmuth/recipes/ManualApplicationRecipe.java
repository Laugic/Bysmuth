package com.laugic.bysmuth.recipes;

import java.util.List;
import java.util.Optional;

import com.laugic.bysmuth.items.IOnUseForRecipeItem;
import com.laugic.bysmuth.util.BlockHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
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

//From Create https://github.com/Creators-of-Create/Create
@EventBusSubscriber
public class ManualApplicationRecipe extends ItemApplicationRecipe {

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

        RecipeType<Recipe<RecipeWrapper>> type = ModRecipes.ITEM_APPLICATION.getType();
        Optional<Recipe<RecipeWrapper>> foundRecipe = level.getRecipeManager()
                .getAllRecipesFor(type)
                .stream()
                .filter(r -> {
                    ManualApplicationRecipe mar = (ManualApplicationRecipe) r;
                    return mar.testBlock(blockState) && mar.ingredients.get(0)
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

        //level.playSound(null, pos, SoundEvents.COPPER_BREAK, SoundSource.PLAYERS, 1, 1.45f);
        if(heldItem.getItem() instanceof IOnUseForRecipeItem item)
            item.onUse(pos, level);
        ManualApplicationRecipe recipe = (ManualApplicationRecipe) foundRecipe.get();
        level.destroyBlock(pos, false);

        BlockState transformedBlock = recipe.transformBlock(blockState);
        level.setBlock(pos, transformedBlock, 3);
        recipe.rollResults()
                .forEach(stack -> Block.popResource(level, pos, stack));

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

//        awardAdvancements(event.getPlayer(), transformedBlock);
    }
/*
    private static void awardAdvancements(Player player, BlockState placed) {
        CreateAdvancement advancement = null;

        if (AllBlocks.ANDESITE_CASING.has(placed))
            advancement = AllAdvancements.ANDESITE_CASING;
        else if (AllBlocks.BRASS_CASING.has(placed))
            advancement = AllAdvancements.BRASS_CASING;
        else if (AllBlocks.COPPER_CASING.has(placed))
            advancement = AllAdvancements.COPPER_CASING;
        else if (AllBlocks.RAILWAY_CASING.has(placed))
            advancement = AllAdvancements.TRAIN_CASING;
        else
            return;

        advancement.awardTo(player);
    }*/

    public ManualApplicationRecipe(ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        super(ModRecipes.ITEM_APPLICATION, params);
    }

    public boolean testBlock(BlockState in) {
        return ingredients.get(1)
                .test(new ItemStack(in.getBlock()
                        .asItem()));
    }

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
        ProcessingOutput mainOutput = results.get(0);
        if (mainOutput.getStack()
                .getItem() instanceof BlockItem)
            return results.subList(1, results.size());
        return results;
    }

}
