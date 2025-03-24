package com.euphony.enchanting_vanilla.mixin;

import com.euphony.enchanting_vanilla.api.ICustomItemFrame;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ThrownPotion.class)
public class ThrownPotionMixin {
    @Inject(method = "applySplash(Ljava/lang/Iterable;Lnet/minecraft/world/entity/Entity;)V", at = @At("HEAD"))
    public void applySplash(Iterable<MobEffectInstance> effectInstanceList, @Nullable Entity entity, CallbackInfo ci) {
        ThrownPotion potion = (ThrownPotion) (Object) this;
        enchanting_vanilla$handleSplash(effectInstanceList, potion);
    }

    @Inject(method = "applyWater()V", at = @At("HEAD"))
    private void applyWater(CallbackInfo ci) {
        ThrownPotion potion = (ThrownPotion) (Object) this;
        enchanting_vanilla$handleWater(potion);
    }

    @Unique
    private static void enchanting_vanilla$handleSplash(Iterable<MobEffectInstance> effectInstanceList, ThrownPotion thrownPotion) {
        AABB checkBox = thrownPotion.getBoundingBox().inflate(4.0D, 2.0D, 4.0D);
        effectInstanceList.forEach(instance -> {
            if (instance.getEffect() == MobEffects.INVISIBILITY) {
                List<ItemFrame> itemFrames = thrownPotion.level().getEntitiesOfClass(ItemFrame.class, checkBox);
                for (ItemFrame frame : itemFrames) {
                    ICustomItemFrame itemFrame = (ICustomItemFrame) frame;
                    if (!itemFrame.enchanting_vanilla$getIsInvisible())
                        itemFrame.enchanting_vanilla$setIsInvisible(true);
                }
            }
        });
    }

    @Unique
    private static void enchanting_vanilla$handleWater(ThrownPotion thrownPotion) {
        AABB checkBox = thrownPotion.getBoundingBox().inflate(4.0D, 2.0D, 4.0D);
        List<ItemFrame> itemFrames = thrownPotion.level().getEntitiesOfClass(ItemFrame.class, checkBox);
        for (ItemFrame frame : itemFrames) {
            ICustomItemFrame itemFrame = (ICustomItemFrame) frame;
            if (itemFrame.enchanting_vanilla$getIsInvisible())
                itemFrame.enchanting_vanilla$setIsInvisible(false);
        }
    }
}