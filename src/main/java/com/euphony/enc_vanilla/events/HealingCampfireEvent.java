package com.euphony.enc_vanilla.events;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import com.euphony.enc_vanilla.utils.BlockEntityMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = EncVanilla.MODID)
public class HealingCampfireEvent {
    @SubscribeEvent
    public static void handle(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        Level level = player.level();
        if (level.isClientSide || !QolConfig.HANDLER.instance().enableHealingCampfire) return;

        if (player.tickCount % QolConfig.HANDLER.instance().checkEveryTick != 0) return;

        if (!BlockEntityMap.cachedBlockEntities.get(BlockEntityType.CAMPFIRE).containsKey(level)) return;

        BlockPos entityPos = player.blockPosition();
        Vec3i entityVec3i = new Vec3i(entityPos.getX(), entityPos.getY(), entityPos.getZ());

        BlockPos pos = null;

        int radius = QolConfig.HANDLER.instance().healingRadius;

        for (BlockEntity campfireBlockEntity : BlockEntityMap.cachedBlockEntities.get(BlockEntityType.CAMPFIRE).get(level)) {
            BlockPos campfirePos = campfireBlockEntity.getBlockPos();
            if (!campfirePos.closerThan(entityVec3i, radius)) {
                continue;
            }

            BlockState campfireState = campfireBlockEntity.getBlockState();
            if(campfireState.is(Blocks.SOUL_CAMPFIRE)) continue;

            Boolean isLit = campfireState.getValue(CampfireBlock.LIT);
            if (!isLit) {
                continue;
            }

            pos = campfirePos;
            break;
        }

        if (pos == null) return;

        MobEffectInstance effectInstance;
        effectInstance = new MobEffectInstance(MobEffects.REGENERATION, (int) (QolConfig.HANDLER.instance().effectDuration * 20), QolConfig.HANDLER.instance().effectLevel - 1);

        BlockPos playerPos = player.blockPosition();
        if (playerPos.closerThan(pos, radius)) {
            MobEffectInstance effect = player.getEffect(MobEffects.REGENERATION);
            if (effect != null) {
                int duration = effect.getDuration();
                if (duration < (QolConfig.HANDLER.instance().effectDuration * 10)) {
                    player.addEffect(effectInstance);
                }
            } else {
                player.addEffect(effectInstance);
            }
        }
    }
}