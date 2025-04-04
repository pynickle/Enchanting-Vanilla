package com.euphony.enc_vanilla.events;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber(modid = EncVanilla.MODID)
public class CakeDropEvent {
    @SubscribeEvent
    public static void process(BlockEvent.BreakEvent event) {
        if (!QolConfig.HANDLER.instance().enableCakeDrop) return;

        if (event.getPlayer().isCreative()) return;

        var state = event.getState();
        Level level = event.getPlayer().level();
        BlockPos pos = event.getPos();

        if (state.is(BlockTags.CANDLE_CAKES)) {
            dropCake(level, pos);
            return;
        }

        if (state.hasProperty(BlockStateProperties.BITES)) {
            int bites = state.getValue(BlockStateProperties.BITES);
            if (bites == 0) {
                dropCake(level, pos);
            }
        }

    }

    private static void dropCake(Level level, BlockPos pos) {
        Containers.dropItemStack(level, pos.getX(), pos.getY() + 0.5, pos.getZ(), new ItemStack(Items.CAKE));
    }
}
