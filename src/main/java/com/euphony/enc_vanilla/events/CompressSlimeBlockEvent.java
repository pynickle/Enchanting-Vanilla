package com.euphony.enc_vanilla.events;

import com.euphony.enc_vanilla.common.init.EVBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.events.custom.AnvilFallOnLandEvent;

@EventBusSubscriber(modid = EncVanilla.MODID)
public class CompressSlimeBlockEvent {
    @SubscribeEvent
    public static void onLand(AnvilFallOnLandEvent event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        if(level.isClientSide()) {
            return;
        }
        final BlockPos hitBlockPos = pos.below();
        final BlockState hitBlockState = level.getBlockState(hitBlockPos);
        BlockPos belowPos = hitBlockPos.below();
        BlockState hitBelowState = level.getBlockState(belowPos);

        if(hitBlockState.is(Blocks.SLIME_BLOCK) && hitBelowState.is(Blocks.SLIME_BLOCK)) {
            level.setBlockAndUpdate(hitBlockPos, Blocks.AIR.defaultBlockState());
            level.setBlockAndUpdate(belowPos, EVBlocks.COMPRESSED_SLIME_BLOCK.get().defaultBlockState());
        }
    }
}
