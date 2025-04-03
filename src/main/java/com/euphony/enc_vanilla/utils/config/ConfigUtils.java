package com.euphony.enc_vanilla.utils.config;

import com.euphony.enc_vanilla.EncVanilla;
import dev.isxander.yacl3.api.ButtonOption;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

public class ConfigUtils {
    public static final int IMG_WIDTH = 1920;
    public static final int IMG_HEIGHT = 1080;

    public static ButtonOption.Builder getButtonOption(String name) {
        return ButtonOption.createBuilder()
                .name(getButtonOptionName(name))
                .description(OptionDescription.createBuilder()
                        .text(getDesc(name, null))
                        .build()
                );
    }

    public static <T> Option.Builder<T> getGenericOption(String name) {
        return getGenericOption(name, (DescComponent) null);
    }

    public static <T> Option.Builder<T> getGenericOption(String name, @Nullable DescComponent descComponent) {
        return Option.<T>createBuilder()
                .name(getOptionName(name))
                .description(OptionDescription.createBuilder()
                        .text(getDesc(name, descComponent))
                        .build()
                );
    }

    public static <T> Option.Builder<T> getGenericOption(String name, String image) {
        return getGenericOption(name, image, null);
    }

    public static <T> Option.Builder<T> getGenericOption(String name, String image, @Nullable DescComponent descComponent) {
        return Option.<T>createBuilder()
                .name(getOptionName(name))
                .description(OptionDescription.createBuilder()
                        .text(getDesc(name, descComponent))
                        .image(getImage(image), IMG_WIDTH, IMG_HEIGHT)
                        .build()
                );
    }

    public static Component getCategoryName(String category) {
        return Component.translatable(String.format("yacl3.config.%s:config.category.%s",  EncVanilla.MODID, category));
    }

    public static Component getGroupName(String category, String group) {
        return Component.translatable(String.format("yacl3.config.%s:config.category.%s.group.%s", EncVanilla.MODID, category, group));
    }

    private static Component getButtonOptionName(String option) {
        return Component.translatable(String.format("yacl3.config.%s:config.%s.button", EncVanilla.MODID, option));
    }

    private static Component getOptionName(String option) {
        return Component.translatable(String.format("yacl3.config.%s:config.%s", EncVanilla.MODID, option));
    }

    private static Component getDesc(String option, @Nullable DescComponent descComponent) {
        MutableComponent component = Component.translatable(String.format("yacl3.config.%s:config.%s.desc", EncVanilla.MODID, option));
        if (descComponent != null) component.append(Component.literal("\n").append(descComponent.getText()));
        return component;
    }

    private static ResourceLocation getImage(String name) {
        return EncVanilla.prefix(String.format("config/%s.png", name));
    }
}
