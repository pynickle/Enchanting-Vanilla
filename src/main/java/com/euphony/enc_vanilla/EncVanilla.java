package com.euphony.enc_vanilla;

import com.euphony.enc_vanilla.common.init.EVBlocks;
import com.euphony.enc_vanilla.common.init.EVCreativeTabs;
import com.euphony.enc_vanilla.common.init.EVDataComponentTypes;
import com.euphony.enc_vanilla.common.init.EVItems;
import com.euphony.enc_vanilla.common.loot.EVLootModifiers;
import com.euphony.enc_vanilla.config.EVConfig;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

@Mod(EncVanilla.MODID)
public class EncVanilla {
    public static final String MODID = "enc_vanilla";
    private static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    private static final EVConfig CONFIG = new EVConfig();

    public static EVConfig getConfig() {
        return CONFIG;
    }

    public EncVanilla(IEventBus modEventBus, ModContainer modContainer) {
        // EVConfig.register(modContainer);
        EncVanilla.getConfig().load();
        EVBlocks.BLOCKS.register(modEventBus);
        EVItems.ITEMS.register(modEventBus);

        EVCreativeTabs.TABS.register(modEventBus);

        EVDataComponentTypes.DATA_COMPONENTS.register(modEventBus);
        EVLootModifiers.register(modEventBus);
    }

    public static ResourceLocation prefix(String name) {
        return ResourceLocation.fromNamespaceAndPath(MODID, name.toLowerCase(Locale.ROOT));
    }
}
