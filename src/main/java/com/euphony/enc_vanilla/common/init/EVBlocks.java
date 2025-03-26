package com.euphony.enc_vanilla.common.init;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.common.block.WaterloggedLilyPadBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class EVBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(EncVanilla.MODID);

    public static final DeferredBlock<Block> WATERLOGGED_LILY_PAD = register("waterlogged_lily_pad", WaterloggedLilyPadBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.LILY_PAD));

    public static <T extends Block> DeferredBlock<T> register(String name, Function<BlockBehaviour.Properties, T> function, BlockBehaviour.Properties properties) {
        return BLOCKS.registerBlock(name, function, properties);
    }
}
