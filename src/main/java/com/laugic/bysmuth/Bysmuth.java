package com.laugic.bysmuth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.laugic.bysmuth.blocks.ModBlocks;
import com.laugic.bysmuth.entities.ModEntities;
import com.laugic.bysmuth.items.ModItems;
import com.laugic.bysmuth.recipes.ModRecipes;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Bysmuth.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Bysmuth {
    public static final String MOD_ID = "bysmuth";
    public static final String ID = MOD_ID;
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting()
            .disableHtmlEscaping()
            .create();
    public Bysmuth()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModBlocks.register(eventBus);
        ModRecipes.register(eventBus);
        ModItems.register(eventBus);
        ModEntities.register(eventBus);

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("HELLO from setup!");
        event.enqueueWork(() -> {
            ComposterBlock.COMPOSTABLES.put(ModBlocks.PackedLeaves.get(), 1F);
        });
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.Bysmur_Leaves.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.Bysmur_Sapling.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.MythCrop.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.Aquafir.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SedimittBud.get(), RenderType.cutout());
    }

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        // do something when the setup is run on both client and server
        LOGGER.info("HELLO from common setup!");
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // do something when the setup is run on only the client
        LOGGER.info("HELLO from client setup!");
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(ID, path);
    }
}
