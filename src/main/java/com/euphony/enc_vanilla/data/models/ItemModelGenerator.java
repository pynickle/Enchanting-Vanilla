package com.euphony.enc_vanilla.data.models;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.common.init.EVItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

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
