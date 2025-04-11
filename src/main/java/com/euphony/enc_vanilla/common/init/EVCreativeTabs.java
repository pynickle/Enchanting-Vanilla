package com.euphony.enc_vanilla.common.init;

import com.euphony.enc_vanilla.EncVanilla;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EVCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, EncVanilla.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ITEMS = TABS.register("items", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.enc_vanilla.items"))
            .icon(() -> new ItemStack(EVItems.BIOME_CRYSTAL_ITEM.get()))
            .displayItems((parameters, output) -> {
                output.accept(EVItems.DAMAGED_SCULK_COMPASS_ITEM);
                output.accept(EVItems.SCULK_COMPASS_ITEM);
                output.accept(EVItems.BIOME_CRYSTAL_ITEM);
                output.accept(EVItems.HEATED_BIOME_CRYSTAL_ITEM);
                output.accept(EVItems.FROZEN_BIOME_CRYSTAL_ITEM);
                output.accept(EVBlocks.COMPRESSED_SLIME_BLOCK);
                output.accept(EVItems.FROG_BUCKET_ITEM);
            }).build());
}
