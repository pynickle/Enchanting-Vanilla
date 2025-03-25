package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.api.ICustomItemFrame;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemFrame.class)
public abstract class InvisibleItemFrame extends HangingEntity implements ICustomItemFrame {
    @Shadow public abstract ItemStack getItem();

    @Shadow public abstract void setItem(ItemStack stack);

    @Unique
    private boolean enc_vanilla$isInvisible;

    protected InvisibleItemFrame(EntityType<? extends HangingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "setItem(Lnet/minecraft/world/item/ItemStack;Z)V", at = @At("TAIL"))
    private void setHeldItem(ItemStack value, boolean update, CallbackInfo ci) {
        if (this.enc_vanilla$isInvisible)
            ((ItemFrame) (Object) this).setInvisible(true);
    }

    @Inject(method = "removeFramedMap", at = @At("TAIL"))
    private void removeFromFrameMixin(ItemStack stack, CallbackInfo ci) {
        ((ItemFrame) (Object) this).setInvisible(false);
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void addAdditionalSaveDataInject(CompoundTag nbt, CallbackInfo ci) {
        nbt.putBoolean("isInvisible", this.enc_vanilla$isInvisible);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void readAdditionalSaveDataInject(CompoundTag nbt, CallbackInfo ci) {
        if (nbt.contains("isInvisible")) {
            this.enc_vanilla$isInvisible = nbt.getBoolean("isInvisible");
        }
    }

    @Override
    public boolean enc_vanilla$getIsInvisible() {
        return enc_vanilla$isInvisible;
    }

    @Override
    public void enc_vanilla$setIsInvisible(boolean isInvisible) {
        this.enc_vanilla$isInvisible = isInvisible;
        if(!this.getItem().isEmpty()) {
            setItem(this.getItem());
        }
    }
}