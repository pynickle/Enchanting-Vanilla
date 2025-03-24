package com.euphony.enchanting_vanilla.common.init;

import com.euphony.enchanting_vanilla.EnchantingVanilla;
import com.euphony.enchanting_vanilla.common.block.WaterloggedLilyPadBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class EVBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(EnchantingVanilla.MODID);

    public static final DeferredBlock<Block> WATERLOGGED_LILY_PAD = register("waterlogged_lily_pad", WaterloggedLilyPadBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.LILY_PAD));

    public static <T extends Block> DeferredBlock<T> register(String name, Function<BlockBehaviour.Properties, T> function, BlockBehaviour.Properties properties) {
        return BLOCKS.registerBlock(name, function, properties);
    }
}
