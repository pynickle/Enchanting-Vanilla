package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.common.init.EVBlocks;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.BambooStalkBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.common.util.TriState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

import static net.minecraft.world.level.block.BambooStalkBlock.AGE;

@Mixin(BambooStalkBlock.class)
public class BambooStalkBlockMixin {
    @Inject(method = "getStateForPlacement", at = @At("HEAD"), cancellable = true)
    @Nullable
    public void getStateForPlacementInject(BlockPlaceContext context, CallbackInfoReturnable<BlockState> cir) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        if (fluidstate.isEmpty()) {
            BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos().below());
            TriState soilDecision = blockstate.canSustainPlant(context.getLevel(), context.getClickedPos().below(), Direction.UP, Blocks.BAMBOO.defaultBlockState());
            if (!soilDecision.isTrue()) {
                return;
            }

            if (blockstate.is(EVBlocks.CUT_BAMBOO_SAPLING)) {
                cir.setReturnValue(Blocks.BAMBOO.defaultBlockState().setValue(AGE, 0));
            } else {
                cir.cancel();
            }
        }
    }
}
