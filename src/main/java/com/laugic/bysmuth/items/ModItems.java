package com.laugic.bysmuth.items;

import com.laugic.bysmuth.Bysmuth;
import com.laugic.bysmuth.blocks.ModBlocks;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Bysmuth.MOD_ID);

    public static final RegistryObject<Item> FallenStar = ITEMS.register("fallen_star",
            () -> new FallenStar(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> BysmuthChunk = ITEMS.register("bysmuth_chunk",
            () -> new FallenStar(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> BysmuthItem = ITEMS.register("bysmuth",
            () -> new FallenStar(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> ChaosPowder = ITEMS.register("chaos_powder",
            () -> new ChaosPowder(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> WoodenShears = ITEMS.register("wooden_shears",
            () -> new ShearsItem(new Item.Properties().durability(32).tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> WoodenTrowel = ITEMS.register("wooden_trowel",
            () -> new WoodenTrowel(new Item.Properties().durability(32).tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> MythSeeds = ITEMS.register("myth_seeds",
            () -> new ItemNameBlockItem(ModBlocks.MythCrop.get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> SedimittBud = ITEMS.register("sedimitt_bud",
            () -> new SedimittItem(ModBlocks.SedimittBud.get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> Opal = ITEMS.register("opal",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> Rhodonite = ITEMS.register("rhodonite",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> Ruby = ITEMS.register("ruby",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> Sapphire = ITEMS.register("sapphire",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> Azurite = ITEMS.register("azurite",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> Jade = ITEMS.register("jade",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> Amber = ITEMS.register("amber",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> Topaz = ITEMS.register("topaz",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> Bronzite = ITEMS.register("bronzite",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> Onyx = ITEMS.register("onyx",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> Hematite = ITEMS.register("hematite",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> Covellite = ITEMS.register("covellite",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));


    //BLOCK ITEMS

    public static final RegistryObject<Item> Crucible = ITEMS.register("crucible",
            () -> new Crucible(Fluids.EMPTY, new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(16)));

    public static final RegistryObject<Item> Lava_Crucible = ITEMS.register("lava_crucible",
            () -> new Crucible(Fluids.LAVA, new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(1)));

    public static final RegistryObject<Item> Water_Crucible = ITEMS.register("water_crucible",
            () -> new Crucible(Fluids.WATER, new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(1)));



    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}

