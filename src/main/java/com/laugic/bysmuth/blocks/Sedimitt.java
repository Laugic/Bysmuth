package com.laugic.bysmuth.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.*;

public class Sedimitt extends Block {

    public Sedimitt(Properties pProperties) {
        super(pProperties);
    }
    public static final Map<Block, Integer> BLOCK_VALUES = new HashMap<>();

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos,
                                Block blockFrom, BlockPos fromPos, boolean isMoving) {
        if (!level.isClientSide) {
            BlockPos blockPos = pos.below();
            Block belowBlock = level.getBlockState(blockPos).getBlock();

            if(!Sedimitt.BLOCK_VALUES.containsKey(belowBlock))
                level.destroyBlock(pos, false);
        }
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        BlockPos pos = new BlockPos(builder.getParameter(LootContextParams.ORIGIN));
        Block block = builder.getLevel().getBlockState(pos.below()).getBlock();
        if(BLOCK_VALUES.containsKey(block) && !builder.getLevel().isClientSide)
            return Collections.singletonList(new ItemStack(block, BLOCK_VALUES.get(block) % 10));
        return super.getDrops(state, builder);
    }

    static {
        // Add entries to the map
        BLOCK_VALUES.put(Blocks.STONE, 14);
        BLOCK_VALUES.put(Blocks.GRANITE, 14);
        BLOCK_VALUES.put(Blocks.DIORITE, 14);
        BLOCK_VALUES.put(Blocks.ANDESITE, 14);
        BLOCK_VALUES.put(Blocks.DEEPSLATE, 14);
        BLOCK_VALUES.put(Blocks.CALCITE, 14);
        BLOCK_VALUES.put(Blocks.TUFF, 14);
        BLOCK_VALUES.put(Blocks.DRIPSTONE_BLOCK, 14);
        BLOCK_VALUES.put(Blocks.DIRT, 14);
        BLOCK_VALUES.put(Blocks.COARSE_DIRT, 14);
        BLOCK_VALUES.put(Blocks.ROOTED_DIRT, 14);
        BLOCK_VALUES.put(Blocks.SAND, 14);
        BLOCK_VALUES.put(Blocks.RED_SAND, 14);
        BLOCK_VALUES.put(Blocks.GRAVEL, 14);
        BLOCK_VALUES.put(Blocks.COAL_ORE, 21);
        BLOCK_VALUES.put(Blocks.IRON_ORE, 41);
        BLOCK_VALUES.put(Blocks.COPPER_ORE, 21);
        BLOCK_VALUES.put(Blocks.GOLD_ORE, 41);
        BLOCK_VALUES.put(Blocks.REDSTONE_ORE, 41);
        BLOCK_VALUES.put(Blocks.EMERALD_ORE, 61);
        BLOCK_VALUES.put(Blocks.LAPIS_ORE, 41);
        BLOCK_VALUES.put(Blocks.DIAMOND_ORE, 81);
        BLOCK_VALUES.put(Blocks.DEEPSLATE_COAL_ORE, 21);
        BLOCK_VALUES.put(Blocks.DEEPSLATE_IRON_ORE, 41);
        BLOCK_VALUES.put(Blocks.DEEPSLATE_COPPER_ORE, 21);
        BLOCK_VALUES.put(Blocks.DEEPSLATE_GOLD_ORE, 41);
        BLOCK_VALUES.put(Blocks.DEEPSLATE_REDSTONE_ORE, 41);
        BLOCK_VALUES.put(Blocks.DEEPSLATE_EMERALD_ORE, 61);
        BLOCK_VALUES.put(Blocks.DEEPSLATE_LAPIS_ORE, 41);
        BLOCK_VALUES.put(Blocks.DEEPSLATE_DIAMOND_ORE, 81);
        BLOCK_VALUES.put(Blocks.NETHER_GOLD_ORE, 21);
        BLOCK_VALUES.put(Blocks.NETHER_QUARTZ_ORE, 21);
        BLOCK_VALUES.put(Blocks.ANCIENT_DEBRIS, 161);
        BLOCK_VALUES.put(Blocks.AMETHYST_BLOCK, 22);
        BLOCK_VALUES.put(Blocks.SANDSTONE, 14);
        BLOCK_VALUES.put(Blocks.OBSIDIAN, 41);
        BLOCK_VALUES.put(Blocks.ICE, 14);
        BLOCK_VALUES.put(Blocks.SNOW_BLOCK, 14);
        BLOCK_VALUES.put(Blocks.CLAY, 14);
        BLOCK_VALUES.put(Blocks.NETHERRACK, 14);
        BLOCK_VALUES.put(Blocks.SOUL_SAND, 14);
        BLOCK_VALUES.put(Blocks.SOUL_SOIL, 14);
        BLOCK_VALUES.put(Blocks.BASALT, 14);
        BLOCK_VALUES.put(Blocks.GLOWSTONE, 14);
        BLOCK_VALUES.put(Blocks.END_STONE, 14);
        BLOCK_VALUES.put(Blocks.WHITE_TERRACOTTA, 14);
        BLOCK_VALUES.put(Blocks.ORANGE_TERRACOTTA, 14);
        BLOCK_VALUES.put(Blocks.MAGENTA_TERRACOTTA, 14);
        BLOCK_VALUES.put(Blocks.LIGHT_BLUE_TERRACOTTA, 14);
        BLOCK_VALUES.put(Blocks.YELLOW_TERRACOTTA, 14);
        BLOCK_VALUES.put(Blocks.LIME_TERRACOTTA, 14);
        BLOCK_VALUES.put(Blocks.PINK_TERRACOTTA, 14);
        BLOCK_VALUES.put(Blocks.GRAY_TERRACOTTA, 14);
        BLOCK_VALUES.put(Blocks.LIGHT_GRAY_TERRACOTTA, 14);
        BLOCK_VALUES.put(Blocks.CYAN_TERRACOTTA, 14);
        BLOCK_VALUES.put(Blocks.PURPLE_TERRACOTTA, 14);
        BLOCK_VALUES.put(Blocks.BLUE_TERRACOTTA, 14);
        BLOCK_VALUES.put(Blocks.BROWN_TERRACOTTA, 14);
        BLOCK_VALUES.put(Blocks.GREEN_TERRACOTTA, 14);
        BLOCK_VALUES.put(Blocks.RED_TERRACOTTA, 14);
        BLOCK_VALUES.put(Blocks.BLACK_TERRACOTTA, 14);
        BLOCK_VALUES.put(Blocks.TERRACOTTA, 14);
        BLOCK_VALUES.put(Blocks.PACKED_ICE, 24);
        BLOCK_VALUES.put(Blocks.PRISMARINE, 14);
        BLOCK_VALUES.put(Blocks.PRISMARINE_BRICKS, 14);
        BLOCK_VALUES.put(Blocks.DARK_PRISMARINE, 14);
        BLOCK_VALUES.put(Blocks.SEA_LANTERN, 14);
        BLOCK_VALUES.put(Blocks.RED_SANDSTONE, 14);
        BLOCK_VALUES.put(Blocks.MAGMA_BLOCK, 14);
        BLOCK_VALUES.put(Blocks.BLUE_ICE, 44);
        BLOCK_VALUES.put(Blocks.CRYING_OBSIDIAN, 41);
        BLOCK_VALUES.put(Blocks.BLACKSTONE, 14);
    }
}
