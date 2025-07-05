package com.laugic.bysmuth.items;

import com.laugic.bysmuth.entities.PrimedNTN;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChaosPowder extends Item implements IOnUseForRecipeItem{

    public ChaosPowder(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onUse(BlockPos pos, Level level) {
        level.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_STEP, SoundSource.BLOCKS, 1.0F, 1.0F);
        level.playSound(null, pos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    @Override
    public void onUseClient(BlockPos pos, Level level) {
        for(int l = 0; l < 8; ++l)
        {
            level.addParticle(ParticleTypes.ENCHANT, (double)pos.getX() - .2 + 1.4 * Math.random(), (double)pos.getY() - .2 + 1.4 * Math.random(), (double)pos.getZ() - .2 + 1.4 * Math.random(), 0.0D, 0.0D, 0.0D);
            level.addParticle(ParticleTypes.DRAGON_BREATH, (double)pos.getX() - .2 + 1.4 * Math.random(), (double)pos.getY() - .2 + 1.4 * Math.random(), (double)pos.getZ() - .2 + 1.4 * Math.random(), 0.0D, 0.0D, 0.0D);
        }
    }
}
