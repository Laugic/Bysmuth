package com.laugic.bysmuth.blocks;

import com.laugic.bysmuth.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class MythCropBlock extends CropBlock {


    public MythCropBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public int getMaxAge() {
        return 7;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return ModItems.MythSeeds.get();
    }

//    @Override
//    public List<ItemStack> getDrops(BlockState pState, LootContext.Builder builder) {
//        ServerLevel level = builder.getLevel();
//        BlockPos pos = new BlockPos(builder.getParameter(LootContextParams.ORIGIN));
//        BlockState belowState = level.getBlockState(pos.below().below());
//        Block belowBlock = belowState.getBlock();
//
//        ResourceLocation lootTable;
//
//        // Choose a loot table based on the block below
//        if(ResourceLocation.isValidResourceLocation("bysmuth/myth/" + ForgeRegistries.BLOCKS.getKey(belowBlock).toString()))
//            lootTable = new ResourceLocation("bysmuth", "myth/" + ForgeRegistries.BLOCKS.getKey(belowBlock).toString());
//        else
//            lootTable = new ResourceLocation("bysmuth", "blocks/myth_crop");
//
//        LootTable table = level.getServer().getLootTables().get(lootTable);
//        LootContext lootContext = builder.create(LootContextParamSets.BLOCK);
//
//        return table.getRandomItems(lootContext);
//    }
}
