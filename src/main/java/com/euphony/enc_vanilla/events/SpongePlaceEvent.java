package com.euphony.enc_vanilla.events;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickItem;

@EventBusSubscriber(modid = EncVanilla.MODID)
public class SpongePlaceEvent {
    @SubscribeEvent
    public static void onSpongePlaceEvent(RightClickItem event) {
        boolean shouldPlace = !QolConfig.HANDLER.instance().enableSpongePlacingSneaking || event.getEntity().isShiftKeyDown();

        if(!QolConfig.HANDLER.instance().enableSpongePlacing || !shouldPlace) return;

        ItemStack stack = event.getItemStack();
        if(stack.is(Items.SPONGE)) {
            Player player = event.getEntity();
            Level level = event.getLevel();
            InteractionHand hand = event.getHand();

            BlockHitResult blockHitResult = Item.getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
            BlockPos pos = blockHitResult.getBlockPos();

            if(level.getBlockState(pos).is(Blocks.WATER)) {
                BlockHitResult blockHitResult1 = blockHitResult.withPosition(pos);
                InteractionResult result = Items.SPONGE.useOn(new UseOnContext(player, hand, blockHitResult1));
                if(result != InteractionResult.PASS) {
                    event.setCanceled(true);
                    event.setCancellationResult(result);
                }
            }
        }
    }
}
