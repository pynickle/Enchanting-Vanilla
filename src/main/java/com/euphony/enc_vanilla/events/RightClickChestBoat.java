package com.euphony.enc_vanilla.events;

import com.euphony.enc_vanilla.EncVanilla;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import tschipp.carryon.common.carry.CarryOnData;
import tschipp.carryon.common.carry.CarryOnDataManager;
import tschipp.carryon.common.carry.PickupHandler;

@EventBusSubscriber(modid = EncVanilla.MODID)
public class RightClickChestBoat {
    @SubscribeEvent
    public static void rightClickChestBoat(PlayerInteractEvent.EntityInteract event) {
        Level level = event.getLevel();
        if(level.isClientSide) return;

        BlockPos pos = event.getPos();
        Entity entity = event.getTarget();

        Player player = event.getEntity();
        ItemStack stack = event.getItemStack();
        if (entity instanceof Boat boat) {
            if (player.isShiftKeyDown() && stack.is(Items.CHEST)) {
                ChestBoat newBoat = new ChestBoat(level, pos.getX(), pos.getY(), pos.getZ());

                newBoat.setXRot(boat.getXRot());
                newBoat.setYRot(boat.getYRot());
                newBoat.setPos(boat.position());

                boat.discard();
                stack.consume(1, player);
                level.addFreshEntity(newBoat);
            } else {
                if (!player.isShiftKeyDown()) {
                    CarryOnData carry = CarryOnDataManager.getCarryData(player);
                    if (carry.isCarrying()) {
                        if (carry.isCarrying(CarryOnData.CarryType.BLOCK)) {
                            BlockState state = carry.getBlock();
                            if (state.is(Blocks.CHEST)) {

                            }
                        }
                    }
                }
            }
        }
    }
}
