package com.laugic.bysmuth.blocks;

import com.laugic.bysmuth.Bysmuth;
import com.laugic.bysmuth.items.ModItems;
import com.laugic.bysmuth.world.features.trees.BysmurTreeGrower;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Bysmuth.ID);
    public static final DeferredRegister<Block> ITEM_BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Bysmuth.ID);

    public static final RegistryObject<Block> Crucible_Block = registerBlockNoItem("crucible_block",
            () -> new CrucibleBlock(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE)
                    .requiresCorrectToolForDrops().strength(1f, 10f)
                    .noOcclusion().randomTicks()));

    public static final RegistryObject<Block> Crucible_Block_Full_Water = registerBlockNoItem("crucible_block_full_water",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE)
                    .requiresCorrectToolForDrops().strength(1f, 10f)
                    .noOcclusion().randomTicks()));

        public static final RegistryObject<Block> Crucible_Block_Full_Lava = registerBlockNoItem("crucible_block_full_lava",
                () -> new Block(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE)
                        .requiresCorrectToolForDrops().strength(1f, 10f)
                    .noOcclusion()));

    public static final RegistryObject<Block> NTNBlock = registerBlock("ntn_block",
            () -> new NTNBlock(BlockBehaviour.Properties.of(Material.EXPLOSIVE).sound(SoundType.AMETHYST)
                    .strength(0f, 0f)), CreativeModeTab.TAB_MISC);

    public static final RegistryObject<Block> Stranglevine = registerBlock("stranglevine",
            () -> new Stranglevine(BlockBehaviour.Properties.of(Material.VEGETABLE).randomTicks().sound(SoundType.WOOD)
                    .strength(.25f, 0f)), CreativeModeTab.TAB_MISC);

    public static final RegistryObject<Block> Sedimitt = registerBlockNoItem("sedimitt_block",
            () -> new Sedimitt(BlockBehaviour.Properties.of(Material.VEGETABLE).randomTicks().sound(SoundType.AMETHYST)
                    .strength(.25f, 0f)));

    public static final RegistryObject<Block> SedimittBud = registerBlockNoItem("sedimitt_bud",
            () -> new SedimittBud(BlockBehaviour.Properties.of(Material.VEGETABLE).randomTicks().sound(SoundType.WOOD).instabreak().noCollission().noOcclusion().randomTicks()));

    public static final RegistryObject<Block> Increatus = registerBlock("increatus",
            () -> new Increatus(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE)
                    .strength(.5f, .5f)), CreativeModeTab.TAB_MISC);

    public static final RegistryObject<Block> Bysmur_Log = registerBlock("bysmur_log",
            () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).sound(SoundType.AMETHYST)),
                    CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> Bysmur_Wood = registerBlock("bysmur_wood",
            () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).sound(SoundType.AMETHYST)),
                    CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> Stripped_Bysmur_Log = registerBlock("stripped_bysmur_log",
            () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG)),
                    CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> Stripped_Bysmur_Wood = registerBlock("stripped_bysmur_wood",
            () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG)),
            CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> Bysmur_Sapling = registerBlock("bysmur_sapling",
            () -> new SaplingBlock(new BysmurTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)),
            CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> Bysmuth_Chunk_Block = registerBlock("bysmuth_chunk_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.AMETHYST)
                    .requiresCorrectToolForDrops().strength(1f, 10f)), CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> Bysmuth_Block = registerBlock("bysmuth_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.AMETHYST)
                    .requiresCorrectToolForDrops().strength(1f, 10f)), CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> Aquafir = registerBlock("aquafir",
            () -> new Aquafir(BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).sound(SoundType.WOOD).noOcclusion().noCollission().randomTicks()),
            CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> MythCrop = registerBlockNoItem("myth_crop",
            () -> new MythCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion().noCollission()));

    public static final RegistryObject<Block> Bysmur_Planks = registerBlock("bysmur_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return 5;
                }
            }, CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> PackedLeaves = registerBlock("packed_leaves",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.AZALEA_LEAVES)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return 5;
                }


            }, CreativeModeTab.TAB_BUILDING_BLOCKS);

    public static final RegistryObject<Block> Bysmur_Leaves = registerBlock("bysmur_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES).sound(SoundType.GRASS).strength(0.2F).randomTicks().noOcclusion().isSuffocating(ModBlocks::never).isViewBlocking(ModBlocks::never)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return 60;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
                    return 30;
                }
            }, CreativeModeTab.TAB_BUILDING_BLOCKS);

    private static boolean never(BlockState p_50806_, BlockGetter p_50807_, BlockPos p_50808_) {
        return false;
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block,
                                                                     CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<T> registerBlockNoItem(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);

        return toReturn;
    }


    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
                                                                            CreativeModeTab tab, String tooltipKey) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(tab)) {
            @Override
            public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
                pTooltip.add(new TranslatableComponent(tooltipKey));
            }
        });
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
                                                                            CreativeModeTab tab) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
