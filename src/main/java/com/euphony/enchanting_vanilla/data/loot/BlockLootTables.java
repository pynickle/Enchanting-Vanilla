package com.euphony.enchanting_vanilla.data.loot;

import com.euphony.enchanting_vanilla.common.init.EVBlocks;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class BlockLootTables extends BlockLootSubProvider {
    public BlockLootTables(HolderLookup.Provider registries) {
        super(ImmutableSet.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return EVBlocks.BLOCKS.getEntries().stream().filter(holder -> !holder.get().asItem().equals(Items.AIR)).map(holder -> (Block)holder.get()).toList();
    }

    @Override
    protected void generate() {
        // dropOther(EVBlocks.WATERLOGGED_LILY_PAD.get(), Items.LILY_PAD);
    }
}
