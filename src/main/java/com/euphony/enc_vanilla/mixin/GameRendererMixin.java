package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.config.categories.ClientConfig;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @ModifyReturnValue(method = "getNightVisionScale", at = @At("RETURN"))
    private static float getNightVisionScaleModify(float original, LivingEntity livingEntity, float pNanoTime) {
        if(!ClientConfig.HANDLER.instance().enableFadingNightVision) return original;

        MobEffectInstance mobeffectinstance = livingEntity.getEffect(MobEffects.NIGHT_VISION);
        return !mobeffectinstance.endsWithin(60) ? 1.0F : (1f / (20f * 3.0f) * (mobeffectinstance.getDuration() - pNanoTime));
    }
}
