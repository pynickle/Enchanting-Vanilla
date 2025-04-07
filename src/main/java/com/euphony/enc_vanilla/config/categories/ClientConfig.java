package com.euphony.enc_vanilla.config.categories;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.utils.config.ConfigUtils;
import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.minecraft.network.chat.Component;

import java.nio.file.Path;
import java.util.List;

public class ClientConfig {
    public static ConfigClassHandler<ClientConfig> HANDLER = ConfigClassHandler.createBuilder(ClientConfig.class)
            .id(EncVanilla.prefix("config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .setPath(Path.of("config", EncVanilla.MODID + "/client.json")).build()
            )
            .build();

    private static final String CLIENT_CATEGORY = "client";
    private static final String FADING_NIGHT_VISION_GROUP = "fading_night_vision";
    private static final String OTHER_GROUP = "other";

    @SerialEntry public boolean enableBeeInfo = true;
    @SerialEntry public boolean enableFadingNightVision = true;

    public static YetAnotherConfigLib makeScreen() {
        return YetAnotherConfigLib.create(HANDLER, (defaults, config, builder) -> {
            Option<Boolean> enableBeeInfoOpt = ConfigUtils.<Boolean>getGenericOption("enableBeeInfo", "bee_info")
                    .binding(defaults.enableBeeInfo,
                            () -> config.enableBeeInfo,
                            newVal -> config.enableBeeInfo = newVal)
                    .flag(ConfigUtils.RESOURCE_RELOAD)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableFadingNightVisionOpt = ConfigUtils.<Boolean>getGenericOption("enableFadingNightVision", "fading_night_vision")
                    .binding(defaults.enableFadingNightVision,
                            () -> config.enableFadingNightVision,
                            newVal -> config.enableFadingNightVision = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            return builder
                    .title(Component.translatable("yacl3.config.enc_vanilla:config"))
                    .category(ConfigCategory.createBuilder()
                            .name(ConfigUtils.getCategoryName(CLIENT_CATEGORY))
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(CLIENT_CATEGORY, FADING_NIGHT_VISION_GROUP))
                                    .options(List.of(
                                            enableFadingNightVisionOpt
                                    ))
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(CLIENT_CATEGORY, OTHER_GROUP))
                                    .options(List.of(
                                            enableBeeInfoOpt
                                    ))
                                    .build())
                            .build())
                    .save(RecipesConfig::save);
        });
    }
}
