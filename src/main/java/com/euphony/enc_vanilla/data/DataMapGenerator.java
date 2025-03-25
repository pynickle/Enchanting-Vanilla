package com.euphony.enc_vanilla.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.ComposterBlock;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class DataMapGenerator extends DataMapProvider {
    Builder<Compostable, Item> compostables = this.builder(NeoForgeDataMaps.COMPOSTABLES);

    public DataMapGenerator(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        addCompostable(0.1f, Items.DEAD_BUSH);

        addCompostable(0.3f, Items.DIRT);
        addCompostable(0.3f, Items.COARSE_DIRT);
        addCompostable(0.3f, Items.PODZOL);
        addCompostable(0.3f, Items.MUDDY_MANGROVE_ROOTS);

        addCompostable(0.5f, Items.ROTTEN_FLESH);
        addCompostable(0.5f, Items.GRASS_BLOCK);
        addCompostable(0.5f, Items.BAMBOO);

        addCompostable(0.65f, Items.CHORUS_FRUIT);
        addCompostable(0.65f, Items.CHORUS_PLANT);
        addCompostable(0.65f, Items.MYCELIUM);
        addCompostable(0.65f, Items.ROOTED_DIRT);

        addCompostable(0.85f, Items.POPPED_CHORUS_FRUIT);
        addCompostable(0.85f, Items.CHORUS_FLOWER);
        addCompostable(0.85f, Items.MUSHROOM_STEW);
        addCompostable(0.85f, Items.BEETROOT_SOUP);
    }

    protected void addCompostable(float chance, ItemLike item) {
        ComposterBlock.COMPOSTABLES.put(item.asItem(), chance);
        compostables.add(item.asItem().builtInRegistryHolder(), new Compostable(chance), false);
    }
}

