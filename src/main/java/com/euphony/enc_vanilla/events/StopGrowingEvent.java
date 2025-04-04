package com.euphony.enc_vanilla.events;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.common.init.EVBlocks;
import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = EncVanilla.MODID)
public class StopGrowingEvent {
    @SubscribeEvent
    public static void blockInteract(PlayerInteractEvent.RightClickBlock event) {
        if(!QolConfig.HANDLER.instance().enableStopGrowing) return;

        Level level = event.getLevel();
        if (level.isClientSide) {
            return;
        }

        BlockPos blockPos = event.getPos();
        BlockState state = level.getBlockState(blockPos);

        if(event.getItemStack().is(Items.SHEARS) ) {
            Player player = event.getEntity();
            InteractionHand hand = event.getHand();
            ItemStack item = event.getItemStack();

            if(state.is(Blocks.SUGAR_CANE)) {
                BlockPos testPos = blockPos.above();
                BlockState testState = level.getBlockState(testPos);
                while(testState.is(Blocks.SUGAR_CANE)) {
                    testPos = testPos.above();
                    testState = level.getBlockState(testPos);
                }
                if(!level.getBlockState(testPos).is(EVBlocks.CUT_SUGAR_CANE.get())) {
                    level.setBlockAndUpdate(testPos.below(), EVBlocks.CUT_SUGAR_CANE.get().defaultBlockState());
                    item.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
                    player.swing(hand, true);

                    event.setCanceled(true);
                }
            } else if(state.is(Blocks.BAMBOO_SAPLING)) {
                level.setBlockAndUpdate(blockPos, EVBlocks.CUT_BAMBOO_SAPLING.get().defaultBlockState());
                item.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
                player.swing(hand, true);

                event.setCanceled(true);
            }
        } else if(event.getItemStack().is(ItemTags.AXES) ) {
            if(state.is(Blocks.BAMBOO)) {
                Player player = event.getEntity();
                InteractionHand hand = event.getHand();
                ItemStack item = event.getItemStack();

                BlockPos testPos = blockPos.above();
                BlockState testState = level.getBlockState(testPos);
                while(testState.is(Blocks.BAMBOO)) {
                    testPos = testPos.above();
                    testState = level.getBlockState(testPos);
                }

                if(level.getBlockState(testPos.below()).getValue(BlockStateProperties.STAGE) == 0) {
                    BlockState newState = level.getBlockState(testPos.below()).setValue(BlockStateProperties.STAGE, 1);
                    level.setBlockAndUpdate(testPos.below(), Blocks.BAMBOO.withPropertiesOf(newState));
                    item.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
                    player.swing(hand, true);

                    event.setCanceled(true);
                }
            }
        }
    }
}
