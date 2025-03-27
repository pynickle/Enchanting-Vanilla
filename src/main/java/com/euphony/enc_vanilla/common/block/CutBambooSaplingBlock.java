package com.euphony.enc_vanilla.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.BambooSaplingBlock;
import net.minecraft.world.level.block.state.BlockState;

public class CutBambooSaplingBlock extends BambooSaplingBlock {
    public CutBambooSaplingBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {

    }
}
