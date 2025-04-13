package com.euphony.enc_vanilla.events;

import com.euphony.enc_vanilla.EncVanilla;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = EncVanilla.MODID)
public class RightClickChestBoat {
    @SubscribeEvent
    public static void rightClickChestBoat(PlayerInteractEvent.EntityInteract event) {
        Level level = event.getLevel();
        if(level.isClientSide) return;

        Player player = event.getEntity();
        ItemStack stack = event.getItemStack();
        if(!player.isShiftKeyDown() || !stack.is(Items.CHEST)) return;

        BlockPos pos = event.getPos();
        Entity entity = event.getTarget();
        if(entity instanceof Boat boat) {
            ChestBoat newBoat = new ChestBoat(level, pos.getX(), pos.getY(), pos.getZ());

            newBoat.setXRot(boat.getXRot());
            newBoat.setYRot(boat.getYRot());
            newBoat.setPos(boat.position());

            boat.discard();
            stack.consume(1, player);
            level.addFreshEntity(newBoat);
        }
    }
}
