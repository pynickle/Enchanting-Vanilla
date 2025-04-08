package com.euphony.enc_vanilla.config.categories.qol;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.config.categories.qol.screen.ExtraSoulTorchItemsScreen;
import com.euphony.enc_vanilla.config.categories.qol.screen.ExtraTorchItemsScreen;
import com.euphony.enc_vanilla.utils.config.ConfigUtils;
import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.DoubleSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.gui.YACLScreen;
import net.minecraft.network.chat.Component;

import java.nio.file.Path;
import java.util.List;
import java.util.function.BiConsumer;

public final class QolConfig {
    public static ConfigClassHandler<QolConfig> HANDLER = ConfigClassHandler.createBuilder(QolConfig.class)
            .id(EncVanilla.prefix("config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .setPath(Path.of("config", EncVanilla.MODID + "/qol.json")).build()
            )
            .build();

    public static void load() {
        HANDLER.load();
    }

    public static void save() {
        HANDLER.save();
    }

    private static final String QOL_CATEGORY = "qol";
    private static final String VILLAGER_ATTRACTION_GROUP = "villager_attraction";
    private static final String ITEM_FRAME_GROUP = "item_frame";
    private static final String TORCH_HIT_GROUP = "torch_hit";
    private static final String TRAMPLING_PREVENTION_GROUP = "trampling_prevention";
    private static final String ANVIL_REPAIR_GROUP = "anvil_repair";
    private static final String WATER_CONVERSION_GROUP = "water_conversion";
    private static final String BELL_PHANTOM_GROUP = "bell_phantom";
    private static final String OTHER_GROUP = "other";

    @SerialEntry public boolean enableVillagerAttraction = true;
    @SerialEntry public boolean enableInvisibleItemFrame = true;

    @SerialEntry public boolean enableTorchHit = true;
    @SerialEntry public boolean enableMobTorchHit = true;
    @SerialEntry public int torchHitFireChance = 50;
    @SerialEntry public double torchHitFireDuration = 3.0;
    @SerialEntry public List<String> extraTorchItems = List.of(
            "bonetorch:bonetorch",
            "torchmaster:megatorch",
            "hardcore_torches:lit_torch",
            "magnumtorch:diamond_magnum_torch",
            "magnumtorch:emerald_magnum_torch",
            "magnumtorch:amethyst_magnum_torch",
            "magical_torches:mega_torch",
            "magical_torches:grand_torch",
            "magical_torches:medium_torch",
            "magical_torches:small_torch",
            "pgwbandedtorches:banded_torch_white",
            "pgwbandedtorches:banded_torch_orange",
            "pgwbandedtorches:banded_torch_magenta",
            "pgwbandedtorches:banded_torch_light_blue",
            "pgwbandedtorches:banded_torch_yellow",
            "pgwbandedtorches:banded_torch_lime",
            "pgwbandedtorches:banded_torch_pink",
            "pgwbandedtorches:banded_torch_gray",
            "pgwbandedtorches:banded_torch_light_gray",
            "pgwbandedtorches:banded_torch_cyan",
            "pgwbandedtorches:banded_torch_purple",
            "pgwbandedtorches:banded_torch_blue",
            "pgwbandedtorches:banded_torch_brown",
            "pgwbandedtorches:banded_torch_green",
            "pgwbandedtorches:banded_torch_red",
            "pgwbandedtorches:banded_torch_black"
    );
    @SerialEntry public List<String> extraSoulTorchItems = List.of();

    @SerialEntry public boolean enableFarmlandTramplingPrevention = true;

    @SerialEntry public boolean enableAnvilRepair = true;

    @SerialEntry public boolean enableWaterConversion = true;
    @SerialEntry public boolean enableMudConversion = false;

    @SerialEntry public boolean enableBellPhantom = true;

    @SerialEntry public boolean enableBlocksOnLilyPad = true;
    @SerialEntry public boolean enablePaintingSwitching = true;
    @SerialEntry public boolean enableCutVine = true;
    @SerialEntry public boolean enableStopGrowing = true;
    @SerialEntry public boolean enableSpongePlacing = true;
    @SerialEntry public boolean enableShutupNameTag = true;
    @SerialEntry public boolean enableJukeboxLoop = true;
    @SerialEntry public boolean enableCakeDrop = true;
    @SerialEntry public boolean enableCeilingTorch = true;

    public static YetAnotherConfigLib makeScreen() {
        return YetAnotherConfigLib.create(HANDLER, (defaults, config, builder) -> {
            Option<Boolean> enableVillagerAttractionOpt = ConfigUtils.<Boolean>getGenericOption("enableVillagerAttraction", "villager_attraction")
                    .binding(defaults.enableVillagerAttraction,
                            () -> config.enableVillagerAttraction,
                            newVal -> config.enableVillagerAttraction = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableInvisibleItemFrameOpt = ConfigUtils.<Boolean>getGenericOption("enableInvisibleItemFrame", "item_frame")
                    .binding(defaults.enableInvisibleItemFrame,
                            () -> config.enableInvisibleItemFrame,
                            newVal -> config.enableInvisibleItemFrame = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableTorchHitOpt = ConfigUtils.<Boolean>getGenericOption("enableTorchHit", "torch_hit")
                    .binding(defaults.enableTorchHit,
                            () -> config.enableTorchHit,
                            newVal -> config.enableTorchHit = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableMobTorchHitOpt = ConfigUtils.<Boolean>getGenericOption("enableMobTorchHit", "mob_torch_hit")
                    .binding(defaults.enableMobTorchHit,
                            () -> config.enableMobTorchHit,
                            newVal -> config.enableMobTorchHit = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Integer> torchHitFireChanceOpt = ConfigUtils.<Integer>getGenericOption("torchHitFireChance")
                    .binding(defaults.torchHitFireChance,
                            () -> config.torchHitFireChance,
                            newVal -> config.torchHitFireChance = newVal)
                    .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                            .range(0, 100).step(1).formatValue(value -> Component.literal(value + "%")))
                    .build();

            Option<Double> torchHitFireDurationOpt = ConfigUtils.<Double>getGenericOption("torchHitFireDuration")
                    .binding(defaults.torchHitFireDuration,
                            () -> config.torchHitFireDuration,
                            newVal -> config.torchHitFireDuration = newVal)
                    .controller(opt -> DoubleSliderControllerBuilder.create(opt)
                            .range(3.0, 10.0).step(0.5).formatValue(value -> Component.literal(value + "s")))
                    .build();

            Option<BiConsumer<YACLScreen, ButtonOption>> extraTorchItemsOpt = ConfigUtils.getButtonOption("extraTorchItems")
                    .action(((yaclScreen, buttonOption) -> yaclScreen.getMinecraft().setScreen(ExtraTorchItemsScreen.makeScreen().generateScreen(yaclScreen))))
                    .build();

            Option<BiConsumer<YACLScreen, ButtonOption>> extraSoulTorchItemsOpt = ConfigUtils.getButtonOption("extraSoulTorchItems")
                    .action(((yaclScreen, buttonOption) -> yaclScreen.getMinecraft().setScreen(ExtraSoulTorchItemsScreen.makeScreen().generateScreen(yaclScreen))))
                    .build();

            Option<Boolean> enableFarmlandTramplingPreventionOpt = ConfigUtils.<Boolean>getGenericOption("enableFarmlandTramplingPrevention", "trampling_prevention")
                    .binding(defaults.enableFarmlandTramplingPrevention,
                            () -> config.enableFarmlandTramplingPrevention,
                            newVal -> config.enableFarmlandTramplingPrevention = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableAnvilRepairOpt = ConfigUtils.<Boolean>getGenericOption("enableAnvilRepair", "anvil_repair")
                    .binding(defaults.enableAnvilRepair,
                            () -> config.enableAnvilRepair,
                            newVal -> config.enableAnvilRepair = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableBellPhantomOpt = ConfigUtils.<Boolean>getGenericOption("enableBellPhantom", "bell_phantom")
                    .binding(defaults.enableBellPhantom,
                            () -> config.enableBellPhantom,
                            newVal -> config.enableBellPhantom = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableBlocksOnLilyPadOpt = ConfigUtils.<Boolean>getGenericOption("enableBlocksOnLilyPad", "blocks_on_lily_pad")
                    .binding(defaults.enableBlocksOnLilyPad,
                            () -> config.enableBlocksOnLilyPad,
                            newVal -> config.enableBlocksOnLilyPad = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enablePaintingSwitchingOpt = ConfigUtils.<Boolean>getGenericOption("enablePaintingSwitching", "painting_switching")
                    .binding(defaults.enablePaintingSwitching,
                            () -> config.enablePaintingSwitching,
                            newVal -> config.enablePaintingSwitching = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableCutVineOpt = ConfigUtils.<Boolean>getGenericOption("enableCutVine", "cut_vine")
                    .binding(defaults.enableCutVine,
                            () -> config.enableCutVine,
                            newVal -> config.enableCutVine = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableStopGrowingOpt = ConfigUtils.<Boolean>getGenericOption("enableStopGrowing", "stop_growing")
                    .binding(defaults.enableStopGrowing,
                            () -> config.enableStopGrowing,
                            newVal -> config.enableStopGrowing = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableSpongePlacingOpt = ConfigUtils.<Boolean>getGenericOption("enableSpongePlacing", "sponge_placing")
                    .binding(defaults.enableSpongePlacing,
                            () -> config.enableSpongePlacing,
                            newVal -> config.enableSpongePlacing = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableShutupNameTagOpt = ConfigUtils.<Boolean>getGenericOption("enableShutupNameTag", "shutup_name_tag")
                    .binding(defaults.enableShutupNameTag,
                            () -> config.enableShutupNameTag,
                            newVal -> config.enableShutupNameTag = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableJukeboxLoopOpt = ConfigUtils.<Boolean>getGenericOption("enableJukeboxLoop", "jukebox_loop")
                    .binding(defaults.enableJukeboxLoop,
                            () -> config.enableJukeboxLoop,
                            newVal -> config.enableJukeboxLoop = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableCakeDropOpt = ConfigUtils.<Boolean>getGenericOption("enableCakeDrop", "cake_drop")
                    .binding(defaults.enableCakeDrop,
                            () -> config.enableCakeDrop,
                            newVal -> config.enableCakeDrop = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableCeilingTorchOpt = ConfigUtils.<Boolean>getGenericOption("enableCeilingTorch", "ceiling_torch")
                    .binding(defaults.enableCeilingTorch,
                            () -> config.enableCeilingTorch,
                            newVal -> config.enableCeilingTorch = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableWaterConversionOpt = ConfigUtils.<Boolean>getGenericOption("enableWaterConversion", "water_conversion")
                    .binding(defaults.enableWaterConversion,
                            () -> config.enableWaterConversion,
                            newVal -> config.enableWaterConversion = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableMudConversionOpt = ConfigUtils.<Boolean>getGenericOption("enableMudConversion")
                    .binding(defaults.enableMudConversion,
                            () -> config.enableMudConversion,
                            newVal -> config.enableMudConversion = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            return builder
                    .title(Component.translatable("yacl3.config.enc_vanilla:config"))
                    .category(ConfigCategory.createBuilder()
                            .name(ConfigUtils.getCategoryName(QOL_CATEGORY))
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(QOL_CATEGORY, VILLAGER_ATTRACTION_GROUP))
                                    .options(List.of(
                                            enableVillagerAttractionOpt
                                    ))
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(QOL_CATEGORY, ITEM_FRAME_GROUP))
                                    .options(List.of(
                                            enableInvisibleItemFrameOpt
                                    ))
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(QOL_CATEGORY, TORCH_HIT_GROUP))
                                    .options(List.of(
                                            enableTorchHitOpt,
                                            enableMobTorchHitOpt,
                                            torchHitFireChanceOpt,
                                            torchHitFireDurationOpt,
                                            extraTorchItemsOpt,
                                            extraSoulTorchItemsOpt
                                    ))
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(QOL_CATEGORY, TRAMPLING_PREVENTION_GROUP))
                                    .options(List.of(
                                            enableFarmlandTramplingPreventionOpt
                                    ))
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(QOL_CATEGORY, ANVIL_REPAIR_GROUP))
                                    .options(List.of(
                                            enableAnvilRepairOpt
                                    ))
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(QOL_CATEGORY, WATER_CONVERSION_GROUP))
                                    .options(List.of(
                                            enableWaterConversionOpt,
                                            enableMudConversionOpt
                                    ))
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(QOL_CATEGORY, BELL_PHANTOM_GROUP))
                                    .options(List.of(
                                            enableBellPhantomOpt
                                    ))
                                    .build())
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(QOL_CATEGORY, OTHER_GROUP))
                                    .options(List.of(
                                            enableBlocksOnLilyPadOpt,
                                            enablePaintingSwitchingOpt,
                                            enableCutVineOpt,
                                            enableStopGrowingOpt,
                                            enableSpongePlacingOpt,
                                            enableShutupNameTagOpt,
                                            enableJukeboxLoopOpt,
                                            enableCakeDropOpt,
                                            enableCeilingTorchOpt,
                                            enableWaterConversionOpt
                                    ))
                                    .build())
                            .build())
                    .save(QolConfig::save);
        });
    }

}
