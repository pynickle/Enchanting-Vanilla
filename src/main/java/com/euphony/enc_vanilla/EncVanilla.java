package com.euphony.enc_vanilla;

import com.euphony.enc_vanilla.common.init.*;
import com.euphony.enc_vanilla.common.loot.EVLootModifiers;
import com.euphony.enc_vanilla.config.EVConfig;
import com.euphony.enc_vanilla.utils.BlockEntityMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
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
        EncVanilla.getConfig().load();

        BlockEntityMap.addBlockEntity(BlockEntityType.CAMPFIRE);

        EVBlocks.BLOCKS.register(modEventBus);
        EVItems.ITEMS.register(modEventBus);

        EVConditions.CONDITION_CODECS.register(modEventBus);
        EVAttachmentTypes.ATTACHMENT_TYPES.register(modEventBus);

        EVCreativeTabs.TABS.register(modEventBus);

        EVDataComponentTypes.DATA_COMPONENTS.register(modEventBus);
        EVLootModifiers.register(modEventBus);
    }

    public static ResourceLocation prefix(String name) {
        return ResourceLocation.fromNamespaceAndPath(MODID, name.toLowerCase(Locale.ROOT));
    }
}
