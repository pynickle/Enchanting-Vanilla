package com.euphony.enc_vanilla.events;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.common.init.EVBlocks;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.Map;

@EventBusSubscriber(modid = EncVanilla.MODID)
public class CeilingTorchClient {
    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        final Map<Item, Block> placeEntries = ImmutableMap.of(
                Items.TORCH, EVBlocks.CEILING_TORCH.get(),
                Items.REDSTONE_TORCH, EVBlocks.CEILING_REDSTONE_TORCH.get(),
                Items.SOUL_TORCH, EVBlocks.CEILING_SOUL_TORCH.get()
        );
        if (!event.getEntity().isSpectator()) { //because apparently this is a thing
            BlockPos pos = event.getPos();
            Direction face = event.getFace();
            BlockPos placeAt = pos.relative(face);
            Level level = event.getLevel();

            if (face == Direction.DOWN && (level.isEmptyBlock(placeAt) || !level.getFluidState(placeAt).isEmpty())) {
                ItemStack stack = event.getItemStack();
                if(placeEntries.containsKey(stack.getItem())) {
                    placeTorch(event.getEntity(), event.getHand(), stack, placeAt, level, placeEntries.get(stack.getItem()).defaultBlockState());
                }
            }
        }
    }

    public static void placeTorch(Player player, InteractionHand hand, ItemStack stack, BlockPos pos, Level level, BlockState state) {
        if (state.canSurvive(level, pos)) {
            SoundType soundType;

            level.setBlockAndUpdate(pos, state);
            soundType = state.getBlock().getSoundType(state, level, pos, player);
            level.playSound(player, pos.getX(), pos.getY(), pos.getZ(), soundType.getPlaceSound(), SoundSource.BLOCKS, soundType.getVolume(), soundType.getPitch());
            player.swing(hand);

            stack.consume(1, player);
        }
    }
}
