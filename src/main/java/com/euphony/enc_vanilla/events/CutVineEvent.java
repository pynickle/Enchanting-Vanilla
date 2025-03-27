package com.euphony.enc_vanilla.events;

import com.euphony.enc_vanilla.EVConfig;
import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.common.init.EVBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.Map;

@EventBusSubscriber(modid = EncVanilla.MODID)
public class CutVineEvent {
    @SubscribeEvent
    public static void blockInteract(PlayerInteractEvent.RightClickBlock event) {
        if(!EVConfig.instance().enabledCutVine()) {
            return;
        }

        Level level = event.getLevel();
        if (level.isClientSide) {
            return;
        }

        BlockPos blockPos = event.getPos();
        BlockState state = level.getBlockState(blockPos);

        Player player = event.getEntity();
        InteractionHand hand = event.getHand();
        ItemStack item = player.getItemInHand(hand);
        if(event.getItemStack().is(Items.SHEARS) && state.is(Blocks.VINE)) {
            BlockState newState = EVBlocks.CUT_VINE.get().withPropertiesOf(state);
            Map<Direction, BooleanProperty> map = VineBlock.PROPERTY_BY_DIRECTION;
            for(Direction d : map.keySet()) {
                BooleanProperty prop = map.get(d);
                newState = newState.setValue(prop, state.getValue(prop));
            }

            level.setBlockAndUpdate(blockPos, newState);

            BlockPos testPos = blockPos.below();
            BlockState testState = level.getBlockState(testPos);
            while(testState.is(Blocks.VINE) || testState.is(EVBlocks.CUT_VINE.get())) {
                level.removeBlock(testPos, false);
                testPos = testPos.below();
                testState = level.getBlockState(testPos);
            }

            level.playSound(player, blockPos, SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS, 1.0F, 1.0F);
            item.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
            player.swing(hand, true);

            event.setCanceled(true);
        }
    }
}
