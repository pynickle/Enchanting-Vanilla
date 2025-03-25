package com.euphony.enchanting_vanilla.events;

import com.euphony.enchanting_vanilla.common.init.EVItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = "enchanting_vanilla")
public class FrogBucketEvent {
    @SubscribeEvent
    public static void entityInteract(PlayerInteractEvent.EntityInteract event) {
        Entity entity = event.getTarget();
        Player player = event.getEntity();
        InteractionHand hand = event.getHand();

        ItemStack itemstack = player.getItemInHand(hand);
        if (entity.getType() == EntityType.FROG && itemstack.getItem() == Items.WATER_BUCKET && entity.isAlive()) {
            Frog frog = (Frog) entity;
            frog.playSound(SoundEvents.BUCKET_FILL_FISH, 1.0F, 1.0F);
            ItemStack itemstack1 = new ItemStack(EVItems.FROG_BUCKET_ITEM.get());
            CustomData.update(DataComponents.BUCKET_ENTITY_DATA, itemstack1, (p_330644_) -> {
                p_330644_.putString("variant", frog.getVariant().getRegisteredName());
                p_330644_.putFloat("Health", frog.getHealth());
            });
            ItemStack itemstack2 = ItemUtils.createFilledResult(itemstack, player, itemstack1, false);
            player.setItemInHand(hand, itemstack2);

            frog.discard();
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.sidedSuccess(player.level().isClientSide));
        }
    }
}
