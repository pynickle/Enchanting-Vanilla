package com.euphony.enchanting_vanilla.data.models;

import com.euphony.enchanting_vanilla.EnchantingVanilla;
import com.euphony.enchanting_vanilla.common.init.EVItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ItemModelGenerator extends ItemModelProvider {
    public ItemModelGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, EnchantingVanilla.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // basicItem(EVItems.FROG_BUCKET_ITEM.get());
    }
}
