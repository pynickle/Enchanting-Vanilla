package com.euphony.enc_vanilla.events;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.common.init.EVItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = EncVanilla.MODID)
public class BiomeCrystalEvent {
    @SubscribeEvent
    public static void onWater(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState blockState = level.getBlockState(pos);
        if(event.getItemStack().is(EVItems.HEATED_BIOME_CRYSTAL_ITEM) && blockState.is(Blocks.WATER_CAULDRON)) {
            LayeredCauldronBlock.lowerFillLevel(blockState, level, pos);
            event.getItemStack().consume(1, event.getEntity());
            if (event.getEntity().getInventory().getFreeSlot() == -1) {
                event.getEntity().drop(EVItems.BIOME_CRYSTAL_ITEM.toStack(), false);
            } else {
                event.getEntity().addItem(EVItems.BIOME_CRYSTAL_ITEM.toStack());
            }
            event.setCanceled(true);
        }
    }
}
