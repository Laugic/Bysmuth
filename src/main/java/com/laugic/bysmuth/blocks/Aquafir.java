package com.laugic.bysmuth.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class Aquafir extends Block implements BlockRecipeBlock{

    private static final Random random = new Random();
    public Aquafir(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel level, BlockPos pos, Random pRandom) {
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++){
                if(random.nextInt(8) == 0){
                    BlockPos blockPos = pos.offset(i, -1, j);
                    checkBlock(level, pos, blockPos);
                }
            }
        }
    }
}
