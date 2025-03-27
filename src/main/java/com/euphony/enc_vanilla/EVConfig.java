package com.euphony.enc_vanilla;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.DoubleValue;
import net.neoforged.neoforge.common.ModConfigSpec.EnumValue;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;


public class EVConfig {
    private final ClientConfig client = new ClientConfig();
    private final CommonConfig common = new CommonConfig();

    private static EVConfig instance;

    private EVConfig(ModContainer container) {
        container.registerConfig(ModConfig.Type.CLIENT, client.spec);
        container.registerConfig(ModConfig.Type.COMMON, common.spec);
    }

    public static void register(ModContainer container) {
        if (!container.getModId().equals(EncVanilla.MODID)) {
            throw new IllegalArgumentException();
        }
        instance = new EVConfig(container);
    }

    public static EVConfig instance() {
        return instance;
    }

    public boolean enabledVillagerAttraction() {
        return common.enabledVillagerAttraction.getAsBoolean();
    }

    public boolean enabledInvisibleItemFrame() {
        return common.enabledInvisibleItemFrame.getAsBoolean();
    }

    public boolean enabledBlocksOnLilyPad() {
        return common.enabledBlocksOnLilyPad.getAsBoolean();
    }

    public boolean enabledSlimeChunkDetecting() {
        return common.enabledSlimeChunkDetecting.getAsBoolean();
    }

    public boolean enabledPaintingSwitching() {
        return common.enabledPaintingSwitching.getAsBoolean();
    }

    public boolean enabledCutVine() {
        return common.enabledCutVine.getAsBoolean();
    }

    public boolean enabledStopGrowing() {
        return common.enabledStopGrowing.getAsBoolean();
    }

    public boolean enabledCompressSlimeBlock() {
        return common.enabledCompressSlimeBlock.getAsBoolean();
    }

    public void save() {
        common.spec.save();
        client.spec.save();
    }

    private static class ClientConfig {
        private final ModConfigSpec spec;

        public ClientConfig() {
            var builder = new ModConfigSpec.Builder();
            this.spec = builder.build();
        }

    }

    private static class CommonConfig {
        private final ModConfigSpec spec;

        public final BooleanValue enabledVillagerAttraction;
        public final BooleanValue enabledInvisibleItemFrame;
        public final BooleanValue enabledBlocksOnLilyPad;
        public final BooleanValue enabledSlimeChunkDetecting;
        public final BooleanValue enabledPaintingSwitching;
        public final BooleanValue enabledCutVine;
        public final BooleanValue enabledStopGrowing;
        public final BooleanValue enabledCompressSlimeBlock;

        public CommonConfig() {
            var builder = new ModConfigSpec.Builder();

            builder.push("general");
            enabledVillagerAttraction = builder.define("enabledVillagerAttraction", true);
            enabledInvisibleItemFrame = builder.define("enabledInvisibleItemFrame", true);
            enabledBlocksOnLilyPad = builder.define("enabledBlocksOnLilyPad", true);
            enabledSlimeChunkDetecting = builder.define("enabledSlimeChunkDetecting", true);
            enabledPaintingSwitching = builder.define("enabledPaintingSwitching", true);
            enabledCutVine = builder.define("enabledCutVine", true);
            enabledStopGrowing = builder.define("enabledStopGrowing", true);
            enabledCompressSlimeBlock = builder.define("enabledCompressSlimeBlock", true);
            builder.pop();

            spec = builder.build();
        }
    }

    private static BooleanValue define(ModConfigSpec.Builder builder, String name, boolean defaultValue,
                                       String comment) {
        builder.comment(comment);
        return define(builder, name, defaultValue);
    }

    private static BooleanValue define(ModConfigSpec.Builder builder, String name, boolean defaultValue) {
        return builder.define(name, defaultValue);
    }

    private static IntValue define(ModConfigSpec.Builder builder, String name, int defaultValue, String comment) {
        builder.comment(comment);
        return define(builder, name, defaultValue);
    }

    private static DoubleValue define(ModConfigSpec.Builder builder, String name, double defaultValue) {
        return define(builder, name, defaultValue, Double.MIN_VALUE, Double.MAX_VALUE);
    }

    private static DoubleValue define(ModConfigSpec.Builder builder, String name, double defaultValue, String comment) {
        builder.comment(comment);
        return define(builder, name, defaultValue);
    }

    private static DoubleValue define(ModConfigSpec.Builder builder, String name, double defaultValue, double min,
                                      double max, String comment) {
        builder.comment(comment);
        return define(builder, name, defaultValue, min, max);
    }

    private static DoubleValue define(ModConfigSpec.Builder builder, String name, double defaultValue, double min,
                                      double max) {
        return builder.defineInRange(name, defaultValue, min, max);
    }

    private static IntValue define(ModConfigSpec.Builder builder, String name, int defaultValue, int min, int max,
                                   String comment) {
        builder.comment(comment);
        return define(builder, name, defaultValue, min, max);
    }

    private static IntValue define(ModConfigSpec.Builder builder, String name, int defaultValue, int min, int max) {
        return builder.defineInRange(name, defaultValue, min, max);
    }

    private static IntValue define(ModConfigSpec.Builder builder, String name, int defaultValue) {
        return define(builder, name, defaultValue, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private static <T extends Enum<T>> EnumValue<T> defineEnum(ModConfigSpec.Builder builder, String name,
                                                               T defaultValue) {
        return builder.defineEnum(name, defaultValue);
    }

    private static <T extends Enum<T>> EnumValue<T> defineEnum(ModConfigSpec.Builder builder, String name,
                                                               T defaultValue, String comment) {
        builder.comment(comment);
        return defineEnum(builder, name, defaultValue);
    }
}
