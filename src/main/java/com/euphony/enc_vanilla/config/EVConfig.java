package com.euphony.enc_vanilla.config;

import com.euphony.enc_vanilla.config.categories.ClientConfig;
import com.euphony.enc_vanilla.config.categories.RecipesConfig;
import com.euphony.enc_vanilla.config.categories.ToolsConfig;
import com.euphony.enc_vanilla.config.categories.qol.QolConfig;

public final class EVConfig {
    public void load() {
        QolConfig.load();
        ToolsConfig.load();
        ClientConfig.load();
        RecipesConfig.load();
    }
}
