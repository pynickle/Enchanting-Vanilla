package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.common.init.EVItems;
import com.euphony.enc_vanilla.utils.AABBUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PowderSnowBlock.class)
public abstract class PowderSnowBlockMixin {
    @Inject(method = "entityInside", at = @At("HEAD"), cancellable = true)
    protected void entityInsideInject(BlockState state, Level level, BlockPos pos, Entity entity, CallbackInfo ci) {
        if(entity instanceof ItemEntity item && item.getItem().is(EVItems.BIOME_CRYSTAL_ITEM)) {
            AABB aabb = item.getBoundingBox();
            aabb = aabb.setMaxY(aabb.maxY + 0.4D);
            aabb = aabb.inflate(0.1D, 0.0D, 0.1D);
            if(AABBUtils.contains(new AABB(pos), aabb)) {
                item.setItem(EVItems.FROZEN_BIOME_CRYSTAL_ITEM.toStack());
                ci.cancel();
            }
        }
    }
}
