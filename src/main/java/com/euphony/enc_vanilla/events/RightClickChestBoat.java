package com.euphony.enc_vanilla.events;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import tschipp.carryon.common.carry.CarryOnData;
import tschipp.carryon.common.carry.CarryOnDataManager;

@EventBusSubscriber(modid = EncVanilla.MODID)
public class RightClickChestBoat {
    @SubscribeEvent
    public static void rightClickChestBoat(PlayerInteractEvent.EntityInteract event) {
        if(!QolConfig.HANDLER.instance().enablePlaceChestOnBoat) return;

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
                                ChestBoat newBoat = new ChestBoat(level, pos.getX(), pos.getY(), pos.getZ());

                                ChestBlockEntity blockEntity = (ChestBlockEntity) carry.getBlockEntity(pos, level.registryAccess());
                                if (blockEntity != null) {
                                    for(int i = 0; i < blockEntity.getContainerSize(); i++) {
                                        ItemStack itemStack = blockEntity.getItem(i);
                                        if (!itemStack.isEmpty()) {
                                            newBoat.setItem(i, itemStack);
                                        }
                                    }

                                    newBoat.setXRot(boat.getXRot());
                                    newBoat.setYRot(boat.getYRot());
                                    newBoat.setPos(boat.position());

                                    boat.discard();
                                    carry.clear();
                                    CarryOnDataManager.setCarryData(player, carry);
                                    player.playSound(state.getSoundType().getPlaceSound(), 1.0f, 0.5f);
                                    level.playSound(null, pos, state.getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0f, 0.5f);
                                    player.swing(InteractionHand.MAIN_HAND, true);
                                    level.addFreshEntity(newBoat);

                                    event.setCanceled(true);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
