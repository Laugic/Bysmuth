package com.laugic.bysmuth.items;

import com.laugic.bysmuth.Bysmuth;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Bysmuth.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEventHandler {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getWorld();
        ItemStack stack = event.getItemStack();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);

        // Check that the player is holding your custom item
        if (!(stack.getItem() == ModItems.WoodenTrowel.get())) return;

        // Check if the block is a full composter
        if (state.is(Blocks.COMPOSTER) && state.getValue(ComposterBlock.LEVEL) == 8) {
            if (!level.isClientSide) {
                // Spawn your custom item instead of bonemeal
                double d0 = (double) (level.random.nextFloat() * 0.7F) + 0.15000000596046448D;
                double d1 = (double) (level.random.nextFloat() * 0.7F) + 0.06000000238418579D + 0.7D;
                double d2 = (double) (level.random.nextFloat() * 0.7F) + 0.15000000596046448D;
                ItemStack extra = WoodenTrowel.GetTrowelResult();
                if (extra != null) {
                    ItemEntity itementity = new ItemEntity(level, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, extra);
                    itementity.setDefaultPickUpDelay();
                    level.addFreshEntity(itementity);
                }
                ItemEntity dirtItem = new ItemEntity(level, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, new ItemStack(Items.DIRT));
                dirtItem.setDefaultPickUpDelay();
                level.addFreshEntity(dirtItem);
                // Reset the composter (optional)
                level.setBlock(pos, state.setValue(ComposterBlock.LEVEL, 0), 3);
            }

            // Prevent the composter’s normal bonemeal drop
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.SUCCESS);
        }
    }
}
