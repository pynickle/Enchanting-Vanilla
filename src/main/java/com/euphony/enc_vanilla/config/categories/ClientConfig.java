package com.euphony.enc_vanilla.config.categories;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.utils.config.ConfigUtils;
import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.DoubleSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerFieldControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.gui.controllers.slider.DoubleSliderController;
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

    public static void load() {
        HANDLER.load();
    }

    public static void save() {
        HANDLER.save();
    }

    private static final String CLIENT_CATEGORY = "client";
    private static final String FADING_NIGHT_VISION_GROUP = "fading_night_vision";
    private static final String BETTER_PING_DISPLAY_GROUP = "better_ping_display";
    private static final String BETTER_CHAT_GROUP = "better_chat";
    private static final String OTHER_GROUP = "other";

    @SerialEntry public boolean enableFadingNightVision = true;
    @SerialEntry public double fadingOutDuration = 3.0D;

    @SerialEntry public boolean enableBetterPingDisplay = true;
    @SerialEntry public boolean enableDefaultPingBars = false;

    @SerialEntry public boolean enableLongerChatHistory = true;
    @SerialEntry public int chatMaxMessages = 4096;

    @SerialEntry public boolean enableBeeInfo = true;

    public static YetAnotherConfigLib makeScreen() {
        return YetAnotherConfigLib.create(HANDLER, (defaults, config, builder) -> {
            Option<Boolean> enableFadingNightVisionOpt = ConfigUtils.<Boolean>getGenericOption("enableFadingNightVision")
                    .binding(defaults.enableFadingNightVision,
                            () -> config.enableFadingNightVision,
                            newVal -> config.enableFadingNightVision = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Double> fadingOutDurationOpt = ConfigUtils.<Double>getGenericOption("fadingOutDuration")
                    .binding(defaults.fadingOutDuration,
                            () -> config.fadingOutDuration,
                            newVal -> config.fadingOutDuration = newVal)
                    .controller(opt -> DoubleSliderControllerBuilder.create(opt)
                            .range(1.0, 5.0).step(0.5).formatValue(value -> Component.literal(value + "s")))
                    .build();

            Option<Boolean> enableBetterPingDisplayOpt = ConfigUtils.<Boolean>getGenericOption("enableBetterPingDisplay", "better_ping_display")
                    .binding(defaults.enableBetterPingDisplay,
                            () -> config.enableBetterPingDisplay,
                            newVal -> config.enableBetterPingDisplay = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableDefaultPingBarsOpt = ConfigUtils.<Boolean>getGenericOption("enableDefaultPingBars", "default_ping_bars")
                    .binding(defaults.enableDefaultPingBars,
                            () -> config.enableDefaultPingBars,
                            newVal -> config.enableDefaultPingBars = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableBeeInfoOpt = ConfigUtils.<Boolean>getGenericOption("enableBeeInfo", "bee_info")
                    .binding(defaults.enableBeeInfo,
                            () -> config.enableBeeInfo,
                            newVal -> config.enableBeeInfo = newVal)
                    .flag(ConfigUtils.RESOURCE_RELOAD)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableLongerChatHistoryOpt = ConfigUtils.<Boolean>getGenericOption("enableLongerChatHistory")
                    .binding(defaults.enableLongerChatHistory,
                            () -> config.enableLongerChatHistory,
                            newVal -> config.enableLongerChatHistory = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Integer> chatMaxMessagesOpt = ConfigUtils.<Integer>getGenericOption("chatMaxMessages")
                    .binding(defaults.chatMaxMessages,
                            () -> config.chatMaxMessages,
                            newVal -> config.chatMaxMessages = newVal)
                    .controller(opt -> IntegerFieldControllerBuilder.create(opt)
                            .range(100, 32768))
                    .build();

            return builder
                    .title(Component.translatable("yacl3.config.enc_vanilla:config"))
                    .category(ConfigCategory.createBuilder()
                            .name(ConfigUtils.getCategoryName(CLIENT_CATEGORY))
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(CLIENT_CATEGORY, FADING_NIGHT_VISION_GROUP))
                                    .options(List.of(
                                            enableFadingNightVisionOpt,
                                            fadingOutDurationOpt
                                    ))
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(CLIENT_CATEGORY, BETTER_PING_DISPLAY_GROUP))
                                    .options(List.of(
                                            enableBetterPingDisplayOpt,
                                            enableDefaultPingBarsOpt
                                    ))
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(CLIENT_CATEGORY, BETTER_CHAT_GROUP))
                                    .options(List.of(
                                            enableLongerChatHistoryOpt,
                                            chatMaxMessagesOpt
                                    ))
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(CLIENT_CATEGORY, OTHER_GROUP))
                                    .options(List.of(
                                            enableBeeInfoOpt
                                    ))
                                    .build())
                            .build())
                    .save(ClientConfig::save);
        });
    }
}
