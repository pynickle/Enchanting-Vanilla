package com.euphony.enc_vanilla.data.models;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.common.init.EVBlocks;
import com.euphony.enc_vanilla.common.init.EVItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Objects;

public class ItemModelGenerator extends ItemModelProvider {
    ExistingFileHelper existingFileHelper;

    public ItemModelGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, EncVanilla.MODID, existingFileHelper);
        this.existingFileHelper = existingFileHelper;
    }

    @Override
    protected void registerModels() {
        basicItem(EncVanilla.prefix("frog_bucket_active"));
        predicateItem(EVItems.FROG_BUCKET_ITEM.get(), 1, "frog_bucket_active");
        for(int i = 0; i < 16; i++) {
            basicItem(EncVanilla.prefix("sculk_compass_" + String.format("%02d", i)));
        }
        for(int i = 17; i < 32; i++) {
            basicItem(EncVanilla.prefix("sculk_compass_" + i));
        }
        basicItem(EVItems.DAMAGED_SCULK_COMPASS_ITEM.get());
        basicItem(EVItems.BIOME_CRYSTAL_ITEM.get());
        basicItem(EVItems.FROZEN_BIOME_CRYSTAL_ITEM.get());
        basicItem(EVItems.HEATED_BIOME_CRYSTAL_ITEM.get());

        basicBlockItem(EVBlocks.CUT_VINE.asItem());
        basicBlockItem(EVBlocks.CUT_SUGAR_CANE.asItem());

        simpleVanillaBlockItem(EVBlocks.COMPRESSED_SLIME_BLOCK.get(), "slime_block");

        simpleBlockItem(EVBlocks.CEILING_TORCH.get());
        simpleBlockItem(EVBlocks.CEILING_REDSTONE_TORCH.get());
        simpleBlockItem(EVBlocks.CEILING_SOUL_TORCH.get());
    }

    public ItemModelBuilder basicBlockItem(Item item) {
        return basicBlockItem(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item)));
    }

    public ItemModelBuilder basicBlockItem(ResourceLocation item) {
        return getBuilder(item.toString()).parent(new ModelFile.UncheckedModelFile("item/generated")).texture("layer0", ResourceLocation.fromNamespaceAndPath(item.getNamespace(), "block/" + item.getPath()));
    }

    public ItemModelBuilder simpleVanillaBlockItem(Block block, String path) {
        return simpleVanillaBlockItem(Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(block)), path);
    }

    public ItemModelBuilder simpleVanillaBlockItem(ResourceLocation block, String path) {
        return this.withExistingParent(block.toString(), ResourceLocation.withDefaultNamespace("block/" + path));
    }


    protected ItemModelBuilder.OverrideBuilder predicateItem(Item item, int customModelData, String overrideModel) {
        return predicateItem(BuiltInRegistries.ITEM.getKey(item), customModelData, overrideModel);
    }

    protected ItemModelBuilder.OverrideBuilder predicateItem(ResourceLocation item, int customModelData, String overrideModel) {
        return this.getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", EncVanilla.prefix("item/" + item.getPath()))
                .override()
                .predicate(ResourceLocation.withDefaultNamespace("custom_model_data"), customModelData)
                .model(new ModelFile.ExistingModelFile(EncVanilla.prefix("item/" + overrideModel), existingFileHelper));
    }
}
