package com.laugic.bysmuth.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.*;

public class SedimittBud extends Block {

    public static final int MAX_AGE = 16;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_25;

    public SedimittBud(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        registerDefaultState(stateDefinition.any().setValue(getAgeProperty(), 0));
        super.onPlace(pState, pLevel, pPos, pOldState, pIsMoving);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random pRandom) {
        if (!level.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (level.getRawBrightness(pos, 0) >= 9) {
            int i = this.getAge(state);
            if (i < this.getMaxAge()) {
                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(level, pos, state, pRandom.nextBoolean())) {
                    level.setBlock(pos, this.getStateForAge(i + 1), 2);
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(level, pos, state);
                }
            }
        }

        BlockPos blockPos = pos.below();
        Block belowBlock = level.getBlockState(blockPos).getBlock();

        if(!Sedimitt.BLOCK_VALUES.containsKey(belowBlock))
            level.destroyBlock(pos, true);
        else {
            int maxAge = Sedimitt.BLOCK_VALUES.get(belowBlock);
            maxAge = (maxAge - maxAge % 10) / 10;
            if(getAge(state) >= maxAge)
                level.setBlock(pos, ModBlocks.Sedimitt.get().defaultBlockState(), 3);
        }
    }

    public BlockState getStateForAge(int pAge) {
        return this.defaultBlockState().setValue(this.getAgeProperty(), pAge);
    }

    protected int getAge(BlockState pState) {
        return pState.getValue(this.getAgeProperty());
    }

    public int getMaxAge() {
        return MAX_AGE;
    }

    public IntegerProperty getAgeProperty() {
        return AGE;
    }

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

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE);
    }
}
