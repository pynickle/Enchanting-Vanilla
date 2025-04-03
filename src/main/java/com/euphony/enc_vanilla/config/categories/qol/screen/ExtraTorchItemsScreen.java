package com.euphony.enc_vanilla.config.categories.qol.screen;

import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import com.euphony.enc_vanilla.utils.config.ConfigUtils;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import net.minecraft.network.chat.Component;

import static com.euphony.enc_vanilla.config.categories.qol.QolConfig.HANDLER;

public class ExtraTorchItemsScreen {
    private static final String QOL_CATEGORY = "qol";

    public static YetAnotherConfigLib makeScreen() {
        return YetAnotherConfigLib.create(HANDLER, (defaults, config, builder) -> {
            ListOption<String> extraTorchItemsOpt = ConfigUtils.getListGroupOption("extraTorchItems")
                    .binding(defaults.extraTorchItems,
                            () -> config.extraTorchItems,
                            newVal -> config.extraTorchItems = newVal)
                    .controller(StringControllerBuilder::create)
                    .initial("")
                    .build();

            return builder
                    .title(Component.translatable("yacl3.config.enc_vanilla:config"))
                    .category(ConfigCategory.createBuilder()
                            .name(ConfigUtils.getCategoryName(QOL_CATEGORY))
                            .group(extraTorchItemsOpt)
                            .build())
                    .save(QolConfig::save);
        });
    }
}
