package com.euphony.enc_vanilla.events;

import com.euphony.enc_vanilla.EVConfig;
import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.common.init.EVDataComponentTypes;
import com.euphony.enc_vanilla.common.init.EVItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@EventBusSubscriber(modid = EncVanilla.MODID)
public class TooltipEvent {
    @SubscribeEvent
    public static void addCustomTooltip(ItemTooltipEvent event) {
        ItemStack item = event.getItemStack();

        if (item.is(EVItems.BIOME_CRYSTAL_ITEM)) {
            ResourceKey<Biome> biome = item.get(EVDataComponentTypes.BIOME);
            if(biome == null) {
                event.getToolTip().add(createTooltip("item.enc_vanilla.biome_crystal.desc"));
            } else {
                event.getToolTip().add(createTooltip("biome." + biome.location().toLanguageKey()));
            }
        } else if(item.is(EVItems.HEATED_BIOME_CRYSTAL_ITEM)) {
            ResourceKey<Biome> biome = item.get(EVDataComponentTypes.BIOME);
            if(biome == null) {
                event.getToolTip().add(createTooltip("item.enc_vanilla.heated_biome_crystal.desc"));
            } else {
                event.getToolTip().add(createTooltip("biome." + biome.location().toLanguageKey()));
            }
        } else if(item.is(EVItems.FROZEN_BIOME_CRYSTAL_ITEM)) {
            ResourceKey<Biome> biome = item.get(EVDataComponentTypes.BIOME);
            if(biome == null) {
                event.getToolTip().add(createTooltip("item.enc_vanilla.frozen_biome_crystal.desc"));
            } else {
                event.getToolTip().add(createTooltip("biome." + biome.location().toLanguageKey()));
            }
        } else if(item.is(EVItems.SCULK_COMPASS_ITEM) || item.is(EVItems.DAMAGED_SCULK_COMPASS_ITEM)) {
            if(!EVConfig.instance().enabledSculkCompass()) {
                event.getToolTip().add(createTooltip("item.enc_vanilla.sculk_compass.desc"));
            }
        }
    }

    private static Component createTooltip(String key) {
        return Component.translatable(key).withStyle(ChatFormatting.GRAY);
    }
}
