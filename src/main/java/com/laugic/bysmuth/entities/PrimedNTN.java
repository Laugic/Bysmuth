package com.laugic.bysmuth.entities;


import com.laugic.bysmuth.blocks.ModBlocks;
import com.laugic.bysmuth.blocks.NTNBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.Nullable;

public class PrimedNTN extends PrimedTnt {


    public PrimedNTN(Level pLevel, double pX, double pY, double pZ, @Nullable LivingEntity pOwner) {
        this(ModEntities.NTN.get(), pLevel);
        this.setPos(pX, pY, pZ);
        double d0 = pLevel.random.nextDouble() * (double)((float)Math.PI * 2F);
        this.setDeltaMovement(-Math.sin(d0) * 0.02D, (double)0.2F, -Math.cos(d0) * 0.02D);
        this.setFuse(80);
        this.xo = pX;
        this.yo = pY;
        this.zo = pZ;
    }

    public PrimedNTN(EntityType<? extends PrimedNTN> entityType, Level level) {
        super(entityType, level);
        this.blocksBuilding = true;
    }

    @Override
    protected void explode() {
        for (int x = -2; x < 3; x++) {
            for (int z = -2; z < 3; z++) {
                for (int y = 2; y > -3; y--) {
                    BlockPos pos = new BlockPos(getBlockX() + x, getBlockY() + y, getBlockZ() + z);
                    if(level.getBlockState(pos).getBlock() instanceof NTNBlock ntn)
                    {
                        ntn.wasExploded(level, pos, null);
                        level.destroyBlock(pos, false);
                    }
                    if(!level.isEmptyBlock(pos) && (level.getBlockState(pos).getDestroySpeed(level, pos) != 0 || level.getBlockState(pos).getPistonPushReaction() != PushReaction.DESTROY)) //TODO: TEST
                        continue;
                    if(level.getRandom().nextInt(16) == 0)
                        continue;
                    if((x == -2 || x == 2) && level.getRandom().nextInt(4) == 0)
                        continue;
                    if((z == -2 || z == 2) && level.getRandom().nextInt(4) == 0)
                        continue;
                    if((y == -2) && level.getRandom().nextInt(4) == 0)
                        continue;

                    BlockState block;
                    if(level.dimensionType().hasSkyLight())
                        block = genOverworld(y);
                    else if(level.dimensionType().ultraWarm())
                        block = getNether(pos, y);
                    else
                        block = Blocks.END_STONE.defaultBlockState();

                    if(level.getRandom().nextInt(5) == 0)
                        ((ServerLevel)level).sendParticles(ParticleTypes.EXPLOSION, getBlockX() + x, getBlockY() + y, getBlockZ() + z, 1, 1.0D, 1.0D, 1.0D, 0);

                    level.setBlock(pos, block, 3);
                }
            }
        }
        level.playSound(null, new BlockPos(getBlockX(), getBlockY(), getBlockZ()), SoundEvents.ENDERMAN_TELEPORT, SoundSource.BLOCKS, 4.0F, 1);
    }

    private BlockState genOverworld(int y)
    {
        if(getBlockY() + y > 58)
        {
            if(y == 2)
                return Blocks.GRASS_BLOCK.defaultBlockState();

            if(y == 1)
            {
                if(level.getRandom().nextBoolean())
                    return getStone(getBlockY() + y);

                return Blocks.DIRT.defaultBlockState();
            }
            if(level.getRandom().nextInt(6) == 0)
                return getOre(getBlockY() + y);

            return getStone(getBlockY() + y);
        }

        if(level.getRandom().nextInt(8) == 0)
            return getOre(getBlockY() + y);

        return getStone(getBlockY() + y);
    }

    private BlockState getStone(int y)
    {
        if(level.getRandom().nextInt(6) != 0)
        {
            if(y <= 0)
                return Blocks.DEEPSLATE.defaultBlockState();
            return Blocks.STONE.defaultBlockState();
        }

        if(y <= 0)
            return Blocks.TUFF.defaultBlockState();

        if(level.getRandom().nextInt(3) == 0)
            return Blocks.ANDESITE.defaultBlockState();

        if(level.getRandom().nextBoolean())
            return Blocks.GRANITE.defaultBlockState();

        return Blocks.DIORITE.defaultBlockState();
    }

    private BlockState getOre(int y)
    {
        if(level.getRandom().nextInt(3) == 0)
        {
            if(y < 0)
                return Blocks.DEEPSLATE_COAL_ORE.defaultBlockState();
            return Blocks.COAL_ORE.defaultBlockState();
        }

        if(level.getRandom().nextInt(3) != 0)
        {
            if(y < 0)
                return Blocks.DEEPSLATE_IRON_ORE.defaultBlockState();
            return Blocks.IRON_ORE.defaultBlockState();
        }

        if(level.getRandom().nextBoolean() && y > 100)
            return Blocks.EMERALD_ORE.defaultBlockState();

        if(level.getRandom().nextBoolean() && y < 32)
        {
            if(y < 0)
                return Blocks.DEEPSLATE_REDSTONE_ORE.defaultBlockState();
            return Blocks.REDSTONE_ORE.defaultBlockState();
        }

        if(level.getRandom().nextBoolean() && y < 32)
        {
            if(y < 0)
                return Blocks.DEEPSLATE_GOLD_ORE.defaultBlockState();
            return Blocks.GOLD_ORE.defaultBlockState();
        }

        if(level.getRandom().nextBoolean() && y < 48)
        {
            if(y < 0)
                return Blocks.DEEPSLATE_LAPIS_ORE.defaultBlockState();
            return Blocks.LAPIS_ORE.defaultBlockState();
        }

        if(level.getRandom().nextBoolean() && y < 16)
        {
            if(y < 0)
                return Blocks.DEEPSLATE_DIAMOND_ORE.defaultBlockState();
            return Blocks.DIAMOND_ORE.defaultBlockState();
        }

        return Blocks.COAL_ORE.defaultBlockState();
    }

    private BlockState getNether(BlockPos pos, int y)
    {
        if(level.getRandom().nextInt(2000) == 0)
            return Blocks.ANCIENT_DEBRIS.defaultBlockState();

        Holder<Biome> biome = level.getBiome(pos);

        if (biome.is(Biomes.BASALT_DELTAS)) {
            if(level.getRandom().nextInt(5) == 0)
                return Blocks.MAGMA_BLOCK.defaultBlockState();

            if(level.getRandom().nextInt(3) == 0)
                return Blocks.BLACKSTONE.defaultBlockState();

            return Blocks.BASALT.defaultBlockState();
        }

        if(level.getRandom().nextInt(16) == 0 && y > 0)
            return Blocks.GLOWSTONE.defaultBlockState();

        if (biome.is(Biomes.SOUL_SAND_VALLEY)) {
            if(level.getRandom().nextInt(16) == 0)
                return Blocks.BONE_BLOCK.defaultBlockState();

            if(level.getRandom().nextInt(3) == 0)
                return Blocks.SOUL_SOIL.defaultBlockState();

            return Blocks.SOUL_SAND.defaultBlockState();
        }

        if(level.getRandom().nextInt(8) == 0)
            return Blocks.NETHER_QUARTZ_ORE.defaultBlockState();

        if(level.getRandom().nextInt(12) == 0)
            return Blocks.NETHER_GOLD_ORE.defaultBlockState();

        if(biome.is(Biomes.CRIMSON_FOREST) && y == 2)
            return Blocks.CRIMSON_NYLIUM.defaultBlockState();

        if(biome.is(Biomes.WARPED_FOREST) && y == 2)
            return Blocks.WARPED_NYLIUM.defaultBlockState();

        return Blocks.NETHERRACK.defaultBlockState();
    }
}
