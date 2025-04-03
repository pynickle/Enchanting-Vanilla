package com.euphony.enc_vanilla.config.categories;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.utils.config.ConfigUtils;
import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.api.*;
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

    @SerialEntry public boolean enableSlabsToBlocks = true;

    public static YetAnotherConfigLib makeScreen() {
        return YetAnotherConfigLib.create(HANDLER, (defaults, config, builder) -> {
            Option<Boolean> enableSlabsToBlocksOpt = ConfigUtils.<Boolean>getGenericOption("enableSlabsToBlocks", "slabs_to_blocks")
                    .binding(defaults.enableSlabsToBlocks,
                            () -> config.enableSlabsToBlocks,
                            newVal -> config.enableSlabsToBlocks = newVal)
                    .controller(opt -> BooleanControllerBuilder.create(opt).trueFalseFormatter())
                    .build();

            return builder
                    .title(Component.translatable("yacl3.config.enc_vanilla:config"))
                    .category(ConfigCategory.createBuilder()
                            .name(ConfigUtils.getCategoryName(RECIPES_CATEGORY))
                            .group(OptionGroup.createBuilder()
                                    .name(ConfigUtils.getGroupName(RECIPES_CATEGORY, OTHER_GROUP))
                                    .options(List.of(
                                            enableSlabsToBlocksOpt
                                    ))
                                    .build())
                            .build())
                    .save(RecipesConfig::save);
        });
    }
}
