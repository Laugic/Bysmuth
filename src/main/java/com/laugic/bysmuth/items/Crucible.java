package com.laugic.bysmuth.items;

import com.laugic.bysmuth.blocks.ModBlocks;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.*;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class Crucible extends Item implements DispensibleContainerItem {
    private final Fluid content;

    public Crucible(Fluid pContent, Item.Properties pProperties) {
        super(pProperties);
        this.content = pContent;
        this.fluidSupplier = pContent.delegate;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        BlockHitResult blockhitresult = getPlayerPOVHitResult(level, player, this.content == Fluids.EMPTY ? ClipContext.Fluid.SOURCE_ONLY : ClipContext.Fluid.NONE);

        if (blockhitresult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemstack);
        } else if (blockhitresult.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(itemstack);
        } else {
            BlockPos blockpos = blockhitresult.getBlockPos();
            Direction direction = blockhitresult.getDirection();
            BlockPos relative = blockpos.relative(direction);
            if (level.mayInteract(player, blockpos) && player.mayUseItemAt(relative, direction, itemstack)) {
                if (this.content == Fluids.EMPTY) {
                    BlockState blockstate = level.getBlockState(blockpos);
                    ItemStack result = getFilledCrucible(blockstate.getFluidState().getType(), level, blockpos);
                    if(result.getItem() != ModItems.Crucible.get()){
                        level.gameEvent(player, GameEvent.FLUID_PICKUP, blockpos);
                        BucketPickup bucketpickup = (BucketPickup)blockstate.getBlock();
                        bucketpickup.pickupBlock(level, blockpos, blockstate);
                        //level.setBlock(blockpos, Fluids.EMPTY.defaultFluidState().createLegacyBlock(), 3);
                        ItemStack result2 = ItemUtils.createFilledResult(itemstack, player, result);
                        return InteractionResultHolder.sidedSuccess(result2, level.isClientSide());
                    }
                    else{
                        level.setBlock(relative, ModBlocks.Crucible_Block.get().defaultBlockState(), 3);
                        ItemStack reducedItem = itemstack.copy();
                        reducedItem.setCount(reducedItem.getCount() - 1);
                        return InteractionResultHolder.sidedSuccess(reducedItem, level.isClientSide());
                    }
                } else {
                    BlockState blockstate = level.getBlockState(blockpos);
                    BlockPos blockpos2 = canBlockContainFluid(level, blockpos, blockstate) ? blockpos : relative;
                    if (this.emptyContents(player, level, blockpos2, blockhitresult, itemstack)) {
                        this.checkExtraContent(player, level, itemstack, blockpos2);
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)player, blockpos2, itemstack);
                        }

                        player.awardStat(Stats.ITEM_USED.get(this));
                        return InteractionResultHolder.sidedSuccess(content == Fluids.LAVA?new ItemStack(Items.AIR):getEmptySuccessItem(itemstack, player), level.isClientSide());
                    } else {
                        return InteractionResultHolder.fail(itemstack);
                    }
                }
            } else {
                return InteractionResultHolder.fail(itemstack);
            }
        }
    }

    public ItemStack getFilledCrucible(Fluid fluid, Level level, BlockPos blockpos){
        if(fluid == Fluids.WATER){
            level.playSound(null, blockpos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
            return new ItemStack(ModItems.Water_Crucible.get());
        }
        if(fluid == Fluids.LAVA){
            level.playSound(null, blockpos, SoundEvents.BUCKET_FILL_LAVA, SoundSource.BLOCKS, 1.0F, 1.0F);
            return new ItemStack(ModItems.Lava_Crucible.get());
        }
        level.playSound(null, blockpos, SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
        return new ItemStack(ModItems.Crucible.get());
    }

    public static ItemStack getEmptySuccessItem(ItemStack pBucketStack, Player pPlayer) {
        return !pPlayer.getAbilities().instabuild ? new ItemStack(ModItems.Crucible.get()) : pBucketStack;
    }

    public void checkExtraContent(@Nullable Player pPlayer, Level pLevel, ItemStack pContainerStack, BlockPos pPos) {
    }

    @Deprecated //Forge: use the ItemStack sensitive version
    public boolean emptyContents(@Nullable Player pPlayer, Level pLevel, BlockPos pPos, @Nullable BlockHitResult pResult) {
        return this.emptyContents(pPlayer, pLevel, pPos, pResult, null);
    }

    public boolean emptyContents(@Nullable Player pPlayer, Level pLevel, BlockPos pPos, @Nullable BlockHitResult pResult, @Nullable ItemStack container) {
        if (!(this.content instanceof FlowingFluid)) {
            return false;
        } else {
            BlockState blockstate = pLevel.getBlockState(pPos);
            Block block = blockstate.getBlock();
            Material material = blockstate.getMaterial();
            boolean flag = blockstate.canBeReplaced(this.content);
            boolean flag1 = blockstate.isAir() || flag || block instanceof LiquidBlockContainer && ((LiquidBlockContainer)block).canPlaceLiquid(pLevel, pPos, blockstate, this.content);
            var containedFluidStack = java.util.Optional.ofNullable(container).flatMap(net.minecraftforge.fluids.FluidUtil::getFluidContained);
            if (!flag1) {
                return pResult != null && this.emptyContents(pPlayer, pLevel, pResult.getBlockPos().relative(pResult.getDirection()), (BlockHitResult)null, container);
            } else if (pLevel.dimensionType().ultraWarm() && containedFluidStack.isPresent() && this.content.getAttributes().doesVaporize(pLevel, pPos, containedFluidStack.get())) {
                this.content.getAttributes().vaporize(pPlayer, pLevel, pPos, containedFluidStack.get());
                return true;
            } else if (pLevel.dimensionType().ultraWarm() && this.content.is(FluidTags.WATER)) {
                int i = pPos.getX();
                int j = pPos.getY();
                int k = pPos.getZ();
                pLevel.playSound(pPlayer, pPos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5F, 2.6F + (pLevel.random.nextFloat() - pLevel.random.nextFloat()) * 0.8F);

                for(int l = 0; l < 8; ++l) {
                    pLevel.addParticle(ParticleTypes.LARGE_SMOKE, (double)i + Math.random(), (double)j + Math.random(), (double)k + Math.random(), 0.0D, 0.0D, 0.0D);
                }

                return true;
            } else if (block instanceof LiquidBlockContainer && ((LiquidBlockContainer)block).canPlaceLiquid(pLevel,pPos,blockstate,content)) {
                ((LiquidBlockContainer)block).placeLiquid(pLevel, pPos, blockstate, ((FlowingFluid)this.content).getSource(false));
                this.playEmptySound(pPlayer, pLevel, pPos);
                return true;
            } else {
                if (!pLevel.isClientSide && flag && !material.isLiquid()) {
                    pLevel.destroyBlock(pPos, true);
                }

                if (!pLevel.setBlock(pPos, this.content.defaultFluidState().createLegacyBlock(), 3) && !blockstate.getFluidState().isSource()) {
                    return false;
                } else {
                    this.playEmptySound(pPlayer, pLevel, pPos);
                    return true;
                }
            }
        }
    }

    protected void playEmptySound(@Nullable Player pPlayer, LevelAccessor pLevel, BlockPos pPos) {
        SoundEvent soundevent = this.content.getAttributes().getEmptySound();
        if(soundevent == null) soundevent = this.content.is(FluidTags.LAVA) ? SoundEvents.BUCKET_EMPTY_LAVA : SoundEvents.BUCKET_EMPTY;
        pLevel.playSound(pPlayer, pPos, soundevent, SoundSource.BLOCKS, 1.0F, 1.0F);
        pLevel.gameEvent(pPlayer, GameEvent.FLUID_PLACE, pPos);
    }

    @Override
    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, @Nullable net.minecraft.nbt.CompoundTag nbt) {
        return super.initCapabilities(stack, nbt);
    }

    private final java.util.function.Supplier<? extends Fluid> fluidSupplier;
    public Fluid getFluid() { return fluidSupplier.get(); }

    private boolean canBlockContainFluid(Level worldIn, BlockPos posIn, BlockState blockstate)
    {
        return blockstate.getBlock() instanceof LiquidBlockContainer && ((LiquidBlockContainer)blockstate.getBlock()).canPlaceLiquid(worldIn, posIn, blockstate, this.content);
    }
}