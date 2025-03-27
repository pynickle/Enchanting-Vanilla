package com.euphony.enc_vanilla.common.init;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.common.block.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

public class EVBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(EncVanilla.MODID);

    public static final DeferredBlock<WaterloggedLilyPadBlock> WATERLOGGED_LILY_PAD = register("waterlogged_lily_pad", WaterloggedLilyPadBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.LILY_PAD));

    public static final DeferredBlock<CutVineBlock> CUT_VINE = registerWithItem("cut_vine", CutVineBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.VINE));

    public static final DeferredBlock<CutSugarCaneBlock> CUT_SUGAR_CANE = registerWithItem("cut_sugar_cane", CutSugarCaneBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.SUGAR_CANE));

    public static final DeferredBlock<CutBambooSaplingBlock> CUT_BAMBOO_SAPLING = registerWithItem("cut_bamboo_sapling", CutBambooSaplingBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_SAPLING));

    public static final DeferredBlock<CompressedSlimeBlock> COMPRESSED_SLIME_BLOCK = registerWithItem("compressed_slime_block", CompressedSlimeBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK));

    public static <T extends Block> DeferredBlock<T> register(String name, Function<BlockBehaviour.Properties, T> function, BlockBehaviour.Properties properties) {
        return BLOCKS.registerBlock(name, function, properties);
    }

    public static <T extends Block> DeferredBlock<T> registerWithItem(String name, Function<BlockBehaviour.Properties, T> block, BlockBehaviour.Properties properties) {
        DeferredBlock<T> ret = BLOCKS.registerBlock(name, block, properties);
        EVItems.register(name, ret);
        return ret;
    }
}
