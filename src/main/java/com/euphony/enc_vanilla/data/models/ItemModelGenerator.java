package com.euphony.enc_vanilla.data.models;

import com.euphony.enc_vanilla.EnchantingVanilla;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ItemModelGenerator extends ItemModelProvider {
    public ItemModelGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, EnchantingVanilla.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
    }
}
