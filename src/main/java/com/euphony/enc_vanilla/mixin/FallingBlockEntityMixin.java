package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.config.categories.ToolsConfig;
import com.euphony.enc_vanilla.events.custom.AnvilFallOnLandEvent;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.NeoForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FallingBlockEntity.class)
abstract class FallingBlockEntityMixin extends Entity {
    @Shadow
    private BlockState blockState;

    @Unique
    private float enc_vanilla$fallDistance;

    public FallingBlockEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(
            method = "tick",
            at =
            @At(
                    value = "INVOKE",
                    ordinal = 0,
                    target =
                            "Lnet/minecraft/world/entity/item/FallingBlockEntity;level()Lnet/minecraft/world/level/Level;")
    )
    private void anvilPerFallOnGround(CallbackInfo ci) {
        if (this.level().isClientSide()) return;
        if (this.onGround()) return;
        this.enc_vanilla$fallDistance = this.fallDistance;
    }

    @Inject(
            method = "tick",
            at =
            @At(
                    value = "INVOKE",
                    target =
                            "Lnet/minecraft/world/level/block/Fallable;onLand(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/entity/item/FallingBlockEntity;)V")
    )
    private void anvilFallOnGround(CallbackInfo ci, @Local BlockPos blockPos) {
        if(!ToolsConfig.HANDLER.instance().enableCompressedSlimeBlock) return;
        if (this.level().isClientSide()) return;
        if (!this.blockState.is(BlockTags.ANVIL)) return;
        FallingBlockEntity entity = (FallingBlockEntity) (Object) this;
        AnvilFallOnLandEvent event = new AnvilFallOnLandEvent(this.level(), blockPos, entity, this.enc_vanilla$fallDistance);
        NeoForge.EVENT_BUS.post(event);
    }
}
