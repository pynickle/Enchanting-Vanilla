package com.euphony.enc_vanilla.data.loots;

import com.euphony.enc_vanilla.common.init.EVBlocks;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class BlockLootTables extends BlockLootSubProvider {
    public BlockLootTables(HolderLookup.Provider registries) {
        super(ImmutableSet.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return EVBlocks.BLOCKS.getEntries().stream().filter(holder -> !holder.get().asItem().equals(Items.AIR)).map(holder -> (Block)holder.get()).toList();
    }

    @Override
    protected void generate() {
        add(EVBlocks.CUT_VINE.get(), createShearsOnlyDrop(Items.VINE));
        dropOther(EVBlocks.CUT_SUGAR_CANE.get(), Items.SUGAR_CANE);
        dropOther(EVBlocks.CUT_BAMBOO_SAPLING.get(), Items.BAMBOO);
        dropSelf(EVBlocks.COMPRESSED_SLIME_BLOCK.get());
    }
}
