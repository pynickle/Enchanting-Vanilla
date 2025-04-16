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

public class RecipesConfig {
    public static ConfigClassHandler<RecipesConfig> HANDLER = ConfigClassHandler.createBuilder(RecipesConfig.class)
            .id(EncVanilla.prefix("config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .setPath(Path.of("config", EncVanilla.MODID + "/recipes.json")).build()
            )
            .build();

    public static void load() {
        HANDLER.load();
    }

    public static void save() {
        HANDLER.save();
    }

    private static final String RECIPES_CATEGORY = "recipes";
    private static final String OTHER_GROUP = "other";

    @SerialEntry public boolean enableMoreCompostable = true;
    @SerialEntry public boolean enableSlabsToBlocks = true;
    @SerialEntry public boolean enableSpongeCampfire = true;
    @SerialEntry public boolean enableBetterLodestone = true;

    public static YetAnotherConfigLib makeScreen() {
        return YetAnotherConfigLib.create(HANDLER, (defaults, config, builder) -> {
            // Other
            Option<Boolean> enableMoreCompostableOpt = ConfigUtils.<Boolean>getGenericOption("enableMoreCompostable", "more_compostable")
                    .binding(defaults.enableMoreCompostable,
                            () -> config.enableMoreCompostable,
                            newVal -> config.enableMoreCompostable = newVal)
                    .flag(ConfigUtils.RESOURCE_RELOAD)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableSlabsToBlocksOpt = ConfigUtils.<Boolean>getGenericOption("enableSlabsToBlocks", "slabs_to_blocks")
                    .binding(defaults.enableSlabsToBlocks,
                            () -> config.enableSlabsToBlocks,
                            newVal -> config.enableSlabsToBlocks = newVal)
                    .flag(ConfigUtils.RESOURCE_RELOAD)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableSpongeCampfireOpt = ConfigUtils.<Boolean>getGenericOption("enableSpongeCampfire", "sponge_campfire")
                    .binding(defaults.enableSpongeCampfire,
                            () -> config.enableSpongeCampfire,
                            newVal -> config.enableSpongeCampfire = newVal)
                    .flag(ConfigUtils.RESOURCE_RELOAD)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            Option<Boolean> enableBetterLodestoneOpt = ConfigUtils.<Boolean>getGenericOption("enableBetterLodestone", "better_lodestone")
                    .binding(defaults.enableBetterLodestone,
                            () -> config.enableBetterLodestone,
                            newVal -> config.enableBetterLodestone = newVal)
                    .flag(ConfigUtils.RESOURCE_RELOAD)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            return builder
                    .title(Component.translatable("yacl3.config.enc_vanilla:config"))
                    .category(ConfigCategory.createBuilder()
                            .name(ConfigUtils.getCategoryName(RECIPES_CATEGORY))
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(RECIPES_CATEGORY, OTHER_GROUP))
                                    .options(List.of(
                                            enableMoreCompostableOpt,
                                            enableSlabsToBlocksOpt,
                                            enableSpongeCampfireOpt,
                                            enableBetterLodestoneOpt
                                    ))
                                    .build())
                            .build())
                    .save(RecipesConfig::save);
        });
    }
}
