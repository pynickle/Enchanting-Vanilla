package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.common.init.EVItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.neoforge.event.EventHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(SmallFireball.class)
public abstract class SmallFireballMixin extends Fireball {
    public SmallFireballMixin(EntityType<? extends Fireball> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "onHitEntity", at = @At("HEAD"), cancellable = true)
    protected void onHitEntityInject(EntityHitResult result, CallbackInfo ci) {
        if (result.getEntity() instanceof ItemEntity item) {
            if (item.getItem().is(EVItems.BIOME_CRYSTAL_ITEM)) {
                item.setItem(EVItems.HEATED_BIOME_CRYSTAL_ITEM.toStack());
                ci.cancel();
            }
        }
    }

    @Inject(method = "onHitBlock", at = @At("HEAD"), cancellable = true)
    protected void onHitBlockInject(BlockHitResult result, CallbackInfo ci) {
        if (!this.level().isClientSide) {
            Entity entity = this.getOwner();
            if (!(entity instanceof Mob) || EventHooks.canEntityGrief(this.level(), entity)) {
                BlockPos blockpos = result.getBlockPos().relative(result.getDirection());
                Level level = this.level();
                AABB checkBox = new AABB(blockpos).inflate(0.5D, 0.0D, 0.5D);
                if (level.isEmptyBlock(blockpos)) {
                    List<ItemEntity> items = level.getEntitiesOfClass(ItemEntity.class, checkBox);
                    for(ItemEntity item : items) {
                        if (item.getItem().is(EVItems.BIOME_CRYSTAL_ITEM)) {
                            item.discard();
                            Containers.dropItemStack(level, blockpos.getX(), blockpos.getY() + 0.5, blockpos.getZ(), EVItems.HEATED_BIOME_CRYSTAL_ITEM.toStack());
                            ci.cancel();
                        }
                    }
                }
            }
        }

    }
}
