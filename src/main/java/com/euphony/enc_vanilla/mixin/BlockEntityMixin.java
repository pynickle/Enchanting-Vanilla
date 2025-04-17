package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.utils.BlockEntityMap;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Mixin(value = BlockEntity.class)
public class BlockEntityMixin {
    @Shadow @Final private BlockEntityType<?> type;
    @Shadow protected Level level;

    @Inject(method = "setLevel(Lnet/minecraft/world/level/Level;)V", at = @At(value = "TAIL"))
    public void setLevel(Level level, CallbackInfo ci) {
        if (BlockEntityMap.cachedBlockEntities.containsKey(type)) {
            if (!BlockEntityMap.cachedBlockEntities.get(type).containsKey(level)) {
                BlockEntityMap.cachedBlockEntities.get(type).put(level, new ArrayList<>());
            }

            BlockEntity blockEntity = (BlockEntity) (Object) this;

            BlockEntityMap.cachedBlockEntities.get(type).get(level).add(blockEntity);
        }
    }

    @Inject(method = "setRemoved()V", at = @At(value = "TAIL"))
    public void setRemoved(CallbackInfo ci) {
        if (BlockEntityMap.cachedBlockEntities.containsKey(type)) {
            if (BlockEntityMap.cachedBlockEntities.get(type).containsKey(level)) {
                BlockEntity blockEntity = (BlockEntity) (Object) this;

                BlockEntityMap.cachedBlockEntities.get(type).get(level).remove(blockEntity);
            }
        }
    }
}