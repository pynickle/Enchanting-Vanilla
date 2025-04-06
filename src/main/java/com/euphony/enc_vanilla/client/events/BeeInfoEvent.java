package com.euphony.enc_vanilla.client.events;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.config.categories.ClientConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.level.block.BeehiveBlock;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;
import java.util.Objects;

@EventBusSubscriber(modid = EncVanilla.MODID, value = Dist.CLIENT)
public class BeeInfoEvent {
    @SubscribeEvent
    public static void handleBeeInfo(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if(stack.is(Items.BEE_NEST) || stack.is(Items.BEEHIVE)) {
            if(!ClientConfig.HANDLER.instance().enableBeeInfo) return;

            DataComponentMap dataComponents = stack.getComponents();
            BlockItemStateProperties blockItemStateProperties = dataComponents.getOrDefault(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY);
            int i = Objects.requireNonNullElse(blockItemStateProperties.get(BeehiveBlock.HONEY_LEVEL), 0);
            int j = dataComponents.getOrDefault(DataComponents.BEES, List.of()).size();
            List<Component> tooltip = event.getToolTip();
            tooltip.add(Component.translatable("container.beehive.bees", j, 3).withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.translatable("container.beehive.honey", i, 5).withStyle(ChatFormatting.GRAY));
        }
    }
}
