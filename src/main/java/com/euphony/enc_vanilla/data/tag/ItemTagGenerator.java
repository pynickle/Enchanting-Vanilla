package com.euphony.enc_vanilla.data.tag;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.common.init.EVItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ItemTagGenerator extends ItemTagsProvider {
    public static final TagKey<Item> BIOME_CRYSTAL = create("biome_crystal");

    public ItemTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, EncVanilla.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BIOME_CRYSTAL).add(EVItems.BIOME_CRYSTAL_ITEM.get(), EVItems.FROZEN_BIOME_CRYSTAL_ITEM.get(), EVItems.HEATED_BIOME_CRYSTAL_ITEM.get());
    }

    public static TagKey<Item> create(String tagName) {
        return ItemTags.create(EncVanilla.prefix(tagName));
    }
}
