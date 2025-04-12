package com.euphony.enc_vanilla.events;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.common.init.EVAttachmentTypes;
import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.List;

@EventBusSubscriber(modid = EncVanilla.MODID)
public class BellPhantomEvent {
    @SubscribeEvent
    public static void handle(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        if(level.isClientSide || !QolConfig.HANDLER.instance().enableBellPhantom) return;

        BlockPos pos = event.getPos();
        if(level.getBlockState(pos).is(Blocks.BELL)) {
            int particleTicks = (int) (QolConfig.HANDLER.instance().particleDuration * 20);
            List<Phantom> phantoms = level.getEntitiesOfClass(Phantom.class, new AABB(pos).inflate(24));
            phantoms.forEach(phantom -> {
                phantom.animateHurt(particleTicks);
                phantom.setData(EVAttachmentTypes.BELL_TIME, particleTicks);
            });
        }
    }
}
