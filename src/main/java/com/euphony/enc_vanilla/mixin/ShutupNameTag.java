package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.EVConfig;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(Entity.class)
public class ShutupNameTag {
    @Inject(method = "playSound(Lnet/minecraft/sounds/SoundEvent;FF)V", at = @At("HEAD"), cancellable = true)
    private void playSoundInject(SoundEvent sound, float volume, float pitch, CallbackInfo ci) {
        if (!EVConfig.instance().enabledShutupNameTag())
            return;

        if ((Object) this instanceof LivingEntity livingEntity) {
            if (livingEntity.getCustomName() == null)
                return;

            String name = livingEntity.getCustomName().getString().toLowerCase();

            if (Objects.equals(name, "Shutup!")) {
                ci.cancel();
            }
        }


    }
}
