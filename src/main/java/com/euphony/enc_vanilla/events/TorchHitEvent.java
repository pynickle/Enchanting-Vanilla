package com.euphony.enc_vanilla.events;

import com.euphony.enc_vanilla.EVConfigRemoved;
import com.euphony.enc_vanilla.utils.ItemUtils;
import com.euphony.enc_vanilla.utils.Utils;
import it.crystalnest.soul_fire_d.api.FireManager;
import it.crystalnest.soul_fire_d.api.type.FireTyped;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.jetbrains.annotations.Nullable;


@EventBusSubscriber
public final class TorchHitEvent {
    private TorchHitEvent() {}

    @SubscribeEvent
    public static void handle(LivingIncomingDamageEvent event) {
        if(!EVConfigRemoved.instance().enabledTorchHit()) return;
        Entity entity = event.getSource().getEntity();
        Entity directEntity = event.getSource().getDirectEntity();
        LivingEntity target = event.getEntity();

        if (entity instanceof LivingEntity attacker && entity.equals(directEntity) && !entity.level().isClientSide && !entity.isSpectator() && canAttack(attacker, target)) {
            InteractionHand interactionHand = getInteractionHand(attacker);
            if (interactionHand != null && !target.fireImmune()) {
                ItemStack item = attacker.getItemInHand(interactionHand);
                if (interactionHand == InteractionHand.MAIN_HAND) {
                    attack(target, item);
                }
            }
        }
    }

    private static void attack(Entity target, ItemStack item) {
        double seconds = getFireSeconds(item, target, EVConfigRemoved.instance().torchHitFireDuration());
        if (seconds > 0) {
            if (Utils.isModLoaded("soul_fire_d")) {
                setOnFire(item, target, seconds);
            } else {
                target.igniteForSeconds((float) seconds);
            }
        }
    }

    private static double getFireSeconds(ItemStack item, Entity target, double seconds) {
        if (Math.random() * 100 < EVConfigRemoved.instance().torchHitFireChance()) {
            return seconds;
        }
        return 0;
    }

    @Nullable
    private static InteractionHand getInteractionHand(LivingEntity attacker) {
        if (isTorch(attacker.getMainHandItem())) {
            return InteractionHand.MAIN_HAND;
        } else if (isTorch(attacker.getOffhandItem())) {
            return InteractionHand.OFF_HAND;
        }
        return null;
    }

    private static boolean isTorch(ItemStack item) {
        return item.is(Items.TORCH) || EVConfigRemoved.instance().getExtraTorchItems().contains(ItemUtils.getKey(item.getItem()).toString()) || isSoulTorch(item);
    }

    private static boolean isSoulTorch(ItemStack item) {
        return item.is(Items.SOUL_TORCH) || EVConfigRemoved.instance().getExtraSoulTorchItems().contains(ItemUtils.getKey(item.getItem()).toString());
    }

    private static boolean canAttack(LivingEntity attacker, LivingEntity target) {
        return (attacker instanceof Player || EVConfigRemoved.instance().enabledMobTorchHit()) && attacker.canAttack(target) && (!(attacker instanceof Player attackerPlayer && target instanceof Player targetPlayer) || attackerPlayer.canHarmPlayer(targetPlayer));
    }

    public static void setOnFire(ItemStack item, Entity entity, double seconds) {
        if (item.getItem() instanceof StandingAndWallBlockItem torch && torch.getBlock() instanceof FireTyped fireTypedTorch) {
            FireManager.setOnFire(entity, (float) seconds, fireTypedTorch.getFireType());
        } else if (isSoulTorch(item)) {
            FireManager.setOnFire(entity, (float) seconds, FireManager.SOUL_FIRE_TYPE);
        } else {
            FireManager.setOnFire(entity, (float) seconds, FireManager.DEFAULT_FIRE_TYPE);
        }
    }
}
