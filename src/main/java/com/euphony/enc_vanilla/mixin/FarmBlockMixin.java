package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FarmBlock.class)
public class FarmBlockMixin {

    @Inject(method = "turnToDirt", at = @At("HEAD"), cancellable = true)
    private static void setToDirtMixin(Entity entity, BlockState state, Level world, BlockPos pos, CallbackInfo ci) {

        if (!QolConfig.HANDLER.instance().enableFarmlandTramplingPrevention)
            return;

        if (entity instanceof LivingEntity livingEntity) {
            if (livingEntity.hasEffect(MobEffects.SLOW_FALLING)) {
                ci.cancel();
            }
        }
    }

}