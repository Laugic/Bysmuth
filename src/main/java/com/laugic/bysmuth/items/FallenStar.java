package com.laugic.bysmuth.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FallenStar extends Item implements IOnUseForRecipeItem{

    public FallenStar(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if(!entity.isOnGround())
        {
            entity.level.addParticle(ParticleTypes.FIREWORK, entity.position().x, entity.position().y, entity.position().z, 0.0D, 0.0D, 0.0D);
            if(entity.level.getRandom().nextInt(20) == 0)
                entity.level.playSound(null, entity, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.AMBIENT, 1.0F, 1.0F);
        }
        else
        {
            if(entity.level.getRandom().nextInt(20 * 8) == 0)
                entity.level.playSound(null, entity, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.AMBIENT, 1.0F, 1.0F);
        }
        return super.onEntityItemUpdate(stack, entity);
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