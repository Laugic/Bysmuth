package com.laugic.bysmuth.items;

import com.laugic.bysmuth.Bysmuth;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

public class WoodenTrowel extends Item {

    public WoodenTrowel(Properties pProperties) {
        super(pProperties);
    }

//    @Override
//    public InteractionResult useOn(UseOnContext context)
//    {
//        Level world = context.getLevel();
//        BlockPos blockpos = context.getClickedPos();
//        BlockState blockstate = world.getBlockState(blockpos);
//
//        if(blockstate.getBlock() == Blocks.COMPOSTER && blockstate.getValue(BlockStateProperties.LEVEL_COMPOSTER) == 8)
//        {
//            if (!world.isClientSide)
//            {
//                float f = 0.7F;
//                double d0 = (double)(world.random.nextFloat() * 0.7F) + 0.15000000596046448D;
//                double d1 = (double)(world.random.nextFloat() * 0.7F) + 0.06000000238418579D + 0.7D;
//                double d2 = (double)(world.random.nextFloat() * 0.7F) + 0.15000000596046448D;
//                ItemStack extra = GetTrowelResult();
//                if(extra != null)
//                {
//                    ItemEntity itementity = new ItemEntity(world, (double)blockpos.getX() + d0, (double)blockpos.getY() + d1, (double)blockpos.getZ() + d2, extra);
//                    itementity.setDefaultPickUpDelay();
//                    world.addFreshEntity(itementity);
//                }
//                ItemEntity dirtItem = new ItemEntity(world, (double)blockpos.getX() + d0, (double)blockpos.getY() + d1, (double)blockpos.getZ() + d2, new ItemStack(Items.DIRT));
//                dirtItem.setDefaultPickUpDelay();
//                world.addFreshEntity(dirtItem);
//                BlockState state = blockstate.setValue(ComposterBlock.LEVEL, 0);
//                world.setBlock(blockpos, state, 3);
//            }
//
//            Player player = context.getPlayer();
//            if (player != null)
//                context.getItemInHand().setDamageValue(context.getItemInHand().getDamageValue()-1);
//            world.playSound(null, context.getClickedPos(), SoundEvents.COMPOSTER_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
//
//            return InteractionResult.SUCCESS;
//        }
//        return InteractionResult.PASS;
//    }

    public static ItemStack GetTrowelResult()
    {
        Random random = new Random();
        int roll = random.nextInt(99);
        if(roll < 10)
            return new ItemStack(Items.WHEAT_SEEDS);
        if(roll < 15)
            return new ItemStack(Items.GRASS);
        return null;
    }
}
