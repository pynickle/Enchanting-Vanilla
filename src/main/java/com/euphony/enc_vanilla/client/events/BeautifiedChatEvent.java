package com.euphony.enc_vanilla.client.events;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.config.categories.ClientConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientChatReceivedEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

@EventBusSubscriber(modid = EncVanilla.MODID)
public class BeautifiedChatEvent {
    public static final String VANILLA_FORMAT = "(?i)^<[a-z0-9_]{3,16}>\\s.+$";

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onClientChat(ClientChatReceivedEvent e) {
        Component originalMessage = e.getMessage();
        Component newMessage = processMessage(originalMessage);

        if (originalMessage != newMessage) {
            e.setMessage(newMessage);
        }
    }

    public static Component processMessage(Component message) {
        if(message.getString().matches(VANILLA_FORMAT)) {
            MutableComponent output = Component.empty();
            if(ClientConfig.HANDLER.instance().enableTimeStamp) {
                Date now = new Date();
                String timestamp = new SimpleDateFormat("[dd:HH:mm] ").format(now);

                output.append(Component.literal(timestamp).withColor(0xAA00AA));
            }
            output.append(message);
            return output;
        }
        return message.copy();
    }
}
