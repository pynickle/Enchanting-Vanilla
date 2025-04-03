package com.euphony.enc_vanilla.utils.config;

import com.euphony.enc_vanilla.EncVanilla;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public enum DescComponent {

    VILLAGER_ATTRACTION("villager_attraction");

    private final String translationKey;
    private final ChatFormatting[] formattings;

    DescComponent(String translationKey, ChatFormatting... formattings) {
        this.translationKey = translationKey;
        this.formattings = formattings;
    }

    public Component getText() {
        return Component.translatable(
                String.format("option.%s.description.%s", EncVanilla.MODID, this.translationKey)
        ).withStyle(this.formattings);
    }
}