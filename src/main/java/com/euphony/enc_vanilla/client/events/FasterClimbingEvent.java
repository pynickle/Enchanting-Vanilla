package com.euphony.enc_vanilla.client.events;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.config.categories.ClientConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = EncVanilla.MODID, value = Dist.CLIENT)
public class FasterClimbingEvent {
    @SubscribeEvent
    private static void adjustClimbingSpeed(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();

        if(!player.level().isClientSide) return;

        if (player.onClimbable() && !player.isCrouching()) {
            Climber climber = new Climber(player);

            if (ClientConfig.HANDLER.instance().enableFasterDownward && climber.isFacingDownward()
                    && !climber.isMovingForward() && !climber.isMovingBackward()) {
                climber.moveDownFaster();
            } else if (ClientConfig.HANDLER.instance().enableFasterUpward && climber.isFacingUpward()
                    && climber.isMovingForward()) {
                climber.moveUpFaster();
            }
        }
    }

    private record Climber(Player player) {
        private boolean isFacingDownward() {
                return player.getXRot() > 0;
            }

            private boolean isFacingUpward() {
                return player.getXRot() < 0;
            }

            private boolean isMovingForward() {
                return player.zza > 0;
            }

            private boolean isMovingBackward() {
                return player.zza < 0;
            }

            private float getSpeed() {
                return (float) (Math.sin(Math.abs(player.getXRot() * Math.PI / 180F)) * ClientConfig.HANDLER.instance().speedMultiplier / 10);
            }

            public void moveUpFaster() {
                float dy = getSpeed();
                Vec3 move = new Vec3(0, dy, 0);
                player.move(MoverType.SELF, move);
            }

            public void moveDownFaster() {
                float dy = - getSpeed();
                Vec3 move = new Vec3(0, dy, 0);
                player.move(MoverType.SELF, move);
            }
        }
}
