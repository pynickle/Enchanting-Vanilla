package com.euphony.enchanting_vanilla;

import com.euphony.enchanting_vanilla.common.init.EVBlocks;
import com.euphony.enchanting_vanilla.common.init.EVDataComponentTypes;
import com.euphony.enchanting_vanilla.common.init.EVItems;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

import java.util.Locale;

@Mod(EnchantingVanilla.MODID)
public class EnchantingVanilla {
    public static final String MODID = "enchanting_vanilla";
    private static final Logger LOGGER = LogUtils.getLogger();

    public EnchantingVanilla(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        EVBlocks.BLOCKS.register(modEventBus);
        EVDataComponentTypes.DATA_COMPONENTS.register(modEventBus);
        EVItems.ITEMS.register(modEventBus);
    }

    public static ResourceLocation prefix(String name) {
        return ResourceLocation.fromNamespaceAndPath(MODID, name.toLowerCase(Locale.ROOT));
    }
}
