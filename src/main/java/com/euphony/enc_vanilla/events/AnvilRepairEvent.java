package com.euphony.enc_vanilla.events;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = EncVanilla.MODID)
public class AnvilRepairEvent {
    @SubscribeEvent
    public static void onAnvilClick(PlayerInteractEvent.RightClickBlock event) {
        if(!QolConfig.HANDLER.instance().enableAnvilRepair) return;

        Level level = event.getLevel();
        if (level.isClientSide) return;

        Player player = event.getEntity();
        if(!player.isShiftKeyDown()) return;

        InteractionHand hand = event.getHand();

        ItemStack itemStack = player.getItemInHand(hand);
        Item item = itemStack.getItem();

        if (!item.equals(QolConfig.HANDLER.instance().anvilRepairMaterial)) return;

        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();

        BlockState newState;
        if (block.equals(Blocks.CHIPPED_ANVIL)) {
            newState = Blocks.ANVIL.defaultBlockState();
        } else if (block.equals(Blocks.DAMAGED_ANVIL)) {
            newState = Blocks.CHIPPED_ANVIL.defaultBlockState();
        } else {
            return;
        }

        Direction facing = state.getValue(AnvilBlock.FACING);
        level.setBlockAndUpdate(pos, newState.setValue(AnvilBlock.FACING, facing));

        itemStack.consume(1, player);
        level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ANVIL_PLACE, SoundSource.BLOCKS, 0.5F, 1.0F);
    }
}