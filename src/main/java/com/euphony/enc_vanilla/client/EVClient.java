package com.euphony.enc_vanilla.client;

import com.euphony.enc_vanilla.EnchantingVanilla;
import com.euphony.enc_vanilla.common.init.EVBlocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@EventBusSubscriber(modid = EnchantingVanilla.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EVClient {
    @SubscribeEvent
    public static void registerBlockColor(RegisterColorHandlersEvent.Block event) {
        event.register((state, level, pos, tintIndex) -> 2129968, EVBlocks.WATERLOGGED_LILY_PAD.get());
    }
}
