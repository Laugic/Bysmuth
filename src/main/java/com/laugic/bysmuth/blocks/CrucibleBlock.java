package com.laugic.bysmuth.blocks;

import com.laugic.bysmuth.util.BlockHelper;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class CrucibleBlock extends Block
{
    private static final Random random = new Random();
    public CrucibleBlock(Properties properties) {
        super(properties);
    }

    private static final List<Block> ROCKS = new ArrayList<>(Arrays.asList(Blocks.STONE, Blocks.COBBLESTONE, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE, Blocks.DEEPSLATE, Blocks.COBBLED_DEEPSLATE, Blocks.NETHERRACK, Blocks.BLACKSTONE, Blocks.BASALT, Blocks.TUFF));
    private static final List<Block> HOTS = new ArrayList<>(Arrays.asList(Blocks.FIRE, Blocks.LAVA, Blocks.MAGMA_BLOCK, Blocks.CAMPFIRE, Blocks.SOUL_CAMPFIRE, Blocks.SOUL_FIRE));
    private static final List<Block> WATERS = new ArrayList<>(Arrays.asList(Blocks.OAK_LEAVES, Blocks.DARK_OAK_LEAVES, Blocks.BIRCH_LEAVES, Blocks.ACACIA_LEAVES, Blocks.SPRUCE_LEAVES, Blocks.JUNGLE_LEAVES, Blocks.AZALEA_LEAVES, Blocks.MOSS_BLOCK));

    @Override
    public void randomTick(BlockState pState, ServerLevel level, BlockPos pos, Random pRandom) {
        if(random.nextInt(4) == 0){
            BlockState above = level.getBlockState(pos.above());
            BlockState below = level.getBlockState(pos.below());
            if(ROCKS.contains(above.getBlock()) && HOTS.contains(below.getBlock()))
            {
                level.destroyBlock(pos.above(), false);
                level.setBlock(pos, ModBlocks.Crucible_Block_Full_Lava.get().defaultBlockState(), 3);
            }
            else if(WATERS.contains(above.getBlock()) || above.getBlock() == ModBlocks.Bysmur_Leaves.get())
            {
                level.destroyBlock(pos.above(), false);
                level.setBlock(pos, ModBlocks.Crucible_Block_Full_Water.get().defaultBlockState(), 3);
            }
        }
    }
}
