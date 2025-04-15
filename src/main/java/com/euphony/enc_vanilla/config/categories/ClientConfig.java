package com.euphony.enc_vanilla.config.categories;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.utils.config.ConfigUtils;
import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.*;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.minecraft.network.chat.Component;

import java.awt.*;
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
    private static final String BIOME_TITLE_GROUP = "biome_title";
    private static final String OTHER_GROUP = "other";

    @SerialEntry public boolean enableFadingNightVision = true;
    @SerialEntry public double fadingOutDuration = 3.0D;

    @SerialEntry public boolean enableBetterPingDisplay = true;
    @SerialEntry public boolean enableDefaultPingBars = false;

    @SerialEntry public boolean enableLongerChatHistory = true;
    @SerialEntry public int chatMaxMessages = 4096;
    @SerialEntry public boolean enableTimeStamp = true;

    @SerialEntry public boolean enableBiomeTitle = true;
    @SerialEntry public boolean hideInF3 = true;
    @SerialEntry public boolean hideInF1 = true;
    @SerialEntry public double displayDuration = 1.5;
    @SerialEntry public int fadeInTime = 20;
    @SerialEntry public int fadeOutTime = 20;
    @SerialEntry public double scale = 2.1D;
    @SerialEntry public int yOffset = -10;
    @SerialEntry public Color color = new Color(0xffffff, false);
    @SerialEntry public double cooldownTime = 1.5D;
    @SerialEntry public boolean enableModName = false;
    @SerialEntry public boolean enableUndergroundUpdate = false;

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

            Option<Boolean> enableTimeStampOpt = ConfigUtils.<Boolean>getGenericOption("enableTimeStamp")
                    .binding(defaults.enableTimeStamp,
                            () -> config.enableTimeStamp,
                            newVal -> config.enableTimeStamp = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableBiomeTitleOpt = ConfigUtils.<Boolean>getGenericOption("enableBiomeTitle", "biome_title")
                    .binding(defaults.enableBiomeTitle,
                            () -> config.enableBiomeTitle,
                            newVal -> config.enableBiomeTitle = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> hideInF1Opt = ConfigUtils.<Boolean>getGenericOption("hideInF1")
                    .binding(defaults.hideInF1,
                            () -> config.hideInF1,
                            newVal -> config.hideInF1 = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> hideInF3Opt = ConfigUtils.<Boolean>getGenericOption("hideInF3")
                    .binding(defaults.hideInF3,
                            () -> config.hideInF3,
                            newVal -> config.hideInF3 = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Double> displayDurationOpt = ConfigUtils.<Double>getGenericOption("displayDuration")
                    .binding(defaults.displayDuration,
                            () -> config.displayDuration,
                            newVal -> config.displayDuration = newVal)
                    .controller(opt -> DoubleSliderControllerBuilder.create(opt)
                            .range(1.0, 5.0).step(0.5).formatValue(value -> Component.literal(value + "s")))
                    .build();

            Option<Integer> fadeInTimeOpt = ConfigUtils.<Integer>getGenericOption("fadeInTime")
                    .binding(defaults.fadeInTime,
                            () -> config.fadeInTime,
                            newVal -> config.fadeInTime = newVal)
                    .controller(opt -> IntegerFieldControllerBuilder.create(opt)
                            .range(0, 60))
                    .build();

            Option<Integer> fadeOutTimeOpt = ConfigUtils.<Integer>getGenericOption("fadeOutTime")
                    .binding(defaults.fadeOutTime,
                            () -> config.fadeOutTime,
                            newVal -> config.fadeOutTime = newVal)
                    .controller(opt -> IntegerFieldControllerBuilder.create(opt)
                            .range(0, 60))
                    .build();

            Option<Double> scaleOpt = ConfigUtils.<Double>getGenericOption("scale")
                    .binding(defaults.scale,
                            () -> config.scale,
                            newVal -> config.scale = newVal)
                    .controller(opt -> DoubleFieldControllerBuilder.create(opt)
                            .range(0.3, 3.0))
                    .build();

            Option<Integer> yOffsetOpt = ConfigUtils.<Integer>getGenericOption("yOffset")
                    .binding(defaults.yOffset,
                            () -> config.yOffset,
                            newVal -> config.yOffset = newVal)
                    .controller(opt -> IntegerFieldControllerBuilder.create(opt)
                            .range(-60, 60))
                    .build();

            Option<Color> colorOpt = ConfigUtils.<Color>getGenericOption("color")
                    .binding(defaults.color,
                            () -> config.color,
                            newVal -> config.color = newVal)
                    .controller(opt -> ColorControllerBuilder.create(opt).allowAlpha(false))
                    .build();

            Option<Double>  cooldownTimeOpt = ConfigUtils.<Double>getGenericOption("cooldownTime")
                    .binding(defaults.cooldownTime,
                            () -> config.cooldownTime,
                            newVal -> config.cooldownTime = newVal)
                    .controller(opt -> DoubleSliderControllerBuilder.create(opt)
                            .range(0.0, 5.0).step(0.5).formatValue(value -> Component.literal(value + "s")))
                    .build();

            Option<Boolean> enableModNameOpt = ConfigUtils.<Boolean>getGenericOption("enableModName")
                    .binding(defaults.enableModName,
                            () -> config.enableModName,
                            newVal -> config.enableModName = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableUndergroundUpdateOpt = ConfigUtils.<Boolean>getGenericOption("enableUndergroundUpdate")
                    .binding(defaults.enableUndergroundUpdate,
                            () -> config.enableUndergroundUpdate,
                            newVal -> config.enableUndergroundUpdate = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
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
                                            chatMaxMessagesOpt,
                                            enableTimeStampOpt
                                    ))
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(CLIENT_CATEGORY, BIOME_TITLE_GROUP))
                                    .options(List.of(
                                            enableBiomeTitleOpt,
                                            hideInF1Opt,
                                            hideInF3Opt,
                                            displayDurationOpt,
                                            fadeInTimeOpt,
                                            fadeOutTimeOpt,
                                            scaleOpt,
                                            yOffsetOpt,
                                            colorOpt,
                                            cooldownTimeOpt,
                                            enableModNameOpt,
                                            enableUndergroundUpdateOpt
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
