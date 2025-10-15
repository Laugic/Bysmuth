package com.laugic.bysmuth.util;

import com.laugic.bysmuth.Bysmuth;
import com.laugic.bysmuth.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Bysmuth.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class StarSpawnHandler {
    private static final int TICK_INTERVAL = 200;
    private static int tickCounter = 0;

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        // Only run on server and only during END phase (after world updates)
        if (event.phase != TickEvent.Phase.END || event.world.isClientSide) return;

        Level world = event.world;
        tickCounter++;

        if (tickCounter % TICK_INTERVAL != 0) return;

        // Check for night
        long time = world.getDayTime() % 24000;
        boolean isNight = time >= 13000 && time <= 23000;
        if (!isNight) return;

        // Get all players in this world
        for (Player player : world.players()) {
            if (world.random.nextInt(10) != 0) return; //23000-13000 = 10000. 10000 / 200 = 50. 5 fallen stars will spawn on average per night

            BlockPos playerPos = player.blockPosition();
            if (playerPos.getY() < 48) return;

            double x = player.getX() + world.random.nextGaussian() * 10;
            double z = player.getZ() + world.random.nextGaussian() * 10;
            double y = world.getHeight(Heightmap.Types.MOTION_BLOCKING, (int)x, (int)z) + 30;

            ItemStack starStack = new ItemStack(ModItems.FallenStar.get());
            ItemEntity star = new ItemEntity(world, x, y, z, starStack);

            star.setDeltaMovement(0, -0.25, 0);

            world.addFreshEntity(star);
        }
    }
}
