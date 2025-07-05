package com.laugic.bysmuth.world.features;

import com.laugic.bysmuth.blocks.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

public class ModConfiguredFeatures {
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> BYSMUR_TREE =
            FeatureUtils.register("bysmur", Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(ModBlocks.Bysmur_Log.get()),
                    new StraightTrunkPlacer(4, 2, 4),
                    BlockStateProvider.simple(ModBlocks.Bysmur_Leaves.get()),
                    new BlobFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), 2),
                    new TwoLayersFeatureSize(1, 0, 2)).build());
}
