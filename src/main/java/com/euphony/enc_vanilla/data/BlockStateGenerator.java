package com.euphony.enc_vanilla.data;

import com.euphony.enc_vanilla.EncVanilla;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;


public class BlockStateGenerator extends BlockStateProvider {
    ExistingFileHelper existingFileHelper;
    public BlockStateGenerator(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, EncVanilla.MODID, exFileHelper);
        this.existingFileHelper = exFileHelper;

    }

    @Override
    protected void registerStatesAndModels() {

    }
}
