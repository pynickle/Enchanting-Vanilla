package com.euphony.enc_vanilla.config.categories;

import com.euphony.enc_vanilla.EncVanilla;
import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;

import java.nio.file.Path;

public class ClientConfig {
    public static ConfigClassHandler<ClientConfig> HANDLER = ConfigClassHandler.createBuilder(ClientConfig.class)
            .id(EncVanilla.prefix("config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .setPath(Path.of("config", EncVanilla.MODID + "/client.json")).build()
            )
            .build();
}
