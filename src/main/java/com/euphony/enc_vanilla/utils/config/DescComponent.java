package com.euphony.enc_vanilla.utils.config;

import com.euphony.enc_vanilla.EncVanilla;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public enum DescComponent {
    TICK_EXPLANATION("tick_explanation", ChatFormatting.GRAY);

    private final String translationKey;
    private final ChatFormatting[] formattings;

    DescComponent(String translationKey, ChatFormatting... formattings) {
        this.translationKey = translationKey;
        this.formattings = formattings;
    }

    public Component getText() {
        return Component.translatable(
                String.format("yacl3.config.%s:description.%s", EncVanilla.MODID, this.translationKey)
        ).withStyle(this.formattings);
    }
}