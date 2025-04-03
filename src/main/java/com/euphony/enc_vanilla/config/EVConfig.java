package com.euphony.enc_vanilla.config;

import com.euphony.enc_vanilla.config.categories.QolConfig;

public final class EVConfig {
    public static QolConfig qolConfig = QolConfig.HANDLER.instance();

    public static final int MIN_PERCENT_VALUE = 0;
    public static final int MAX_PERCENT_VALUE = 100;
    public static final int PERCENT_STEP = 1;
    public static final String PERCENT_FORMAT = "%.0f%%";


    public void load() {
        QolConfig.HANDLER.load();
    }
}
