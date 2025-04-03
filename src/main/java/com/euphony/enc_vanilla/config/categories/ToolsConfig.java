package com.euphony.enc_vanilla.config.categories;

import com.euphony.enc_vanilla.EncVanilla;
import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;

import java.nio.file.Path;

public class ToolsConfig {
    public static ConfigClassHandler<ToolsConfig> HANDLER = ConfigClassHandler.createBuilder(ToolsConfig.class)
            .id(EncVanilla.prefix("config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .setPath(Path.of("config", EncVanilla.MODID + "/tools.json")).build()
            )
            .build();

    private static final String TOOL_CATEGORY = "tools";
    private static final String VILLAGER_ATTRACTION_GROUP = "villager_attraction";
    private static final String ITEM_FRAME_GROUP = "item_frame";
    private static final String OTHER_GROUP = "other";
}
