package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.config.categories.ClientConfig;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.gui.components.ChatComponent;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ChatComponent.class)
public class ChatComponentMixin {
    @ModifyExpressionValue(
            method = {"addMessageToDisplayQueue(Lnet/minecraft/client/GuiMessage;)V",
                    "addMessageToQueue(Lnet/minecraft/client/GuiMessage;)V",
                    "addRecentChat(Ljava/lang/String;)V"},
            at = @At(value = "CONSTANT", args = "intValue=100")
    )
    private int moreMessages(int chatMaxMessages) {
        return ClientConfig.HANDLER.instance().chatMaxMessages;
    }
}
