package com.euphony.enc_vanilla;

import com.euphony.enc_vanilla.common.init.EVBlocks;
import com.euphony.enc_vanilla.common.init.EVItems;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

import java.util.Locale;

@Mod(EncVanilla.MODID)
public class EncVanilla {
    public static final String MODID = "enc_vanilla";
    private static final Logger LOGGER = LogUtils.getLogger();

    public EncVanilla(IEventBus modEventBus, ModContainer modContainer) {
        EVConfig.register(modContainer);

        EVBlocks.BLOCKS.register(modEventBus);
        EVItems.ITEMS.register(modEventBus);
    }

    public static ResourceLocation prefix(String name) {
        return ResourceLocation.fromNamespaceAndPath(MODID, name.toLowerCase(Locale.ROOT));
    }
}
