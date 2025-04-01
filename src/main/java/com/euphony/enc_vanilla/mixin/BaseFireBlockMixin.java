package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.common.init.EVItems;
import com.euphony.enc_vanilla.data.tag.ItemTagGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BaseFireBlock.class)
public abstract class BaseFireBlockMixin extends Block {
    public BaseFireBlockMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "entityInside", at = @At("HEAD"), cancellable = true)
    protected void entityInsideInject(BlockState state, Level level, BlockPos pos, Entity entity, CallbackInfo ci) {
        if(state.is(Blocks.FIRE)) {
            if (entity instanceof ItemEntity item) {
                if (item.getItem().is(EVItems.FROZEN_BIOME_CRYSTAL_ITEM)) {
                    item.discard();
                    Containers.dropItemStack(level, pos.getX(), pos.getY() + 0.5, pos.getZ(), EVItems.BIOME_CRYSTAL_ITEM.toStack());
                    ci.cancel();
                }
            }
        } else if(state.is(Blocks.SOUL_FIRE)) {
            if (entity instanceof ItemEntity item) {
                if (item.getItem().is(EVItems.SCULK_COMPASS_ITEM) || item.getItem().is(ItemTagGenerator.BIOME_CRYSTAL)) {
                    item.discard();
                    Containers.dropItemStack(level, pos.getX(), pos.getY() + 0.5, pos.getZ(), new ItemStack(item.getItem().getItem(), 1));
                    ci.cancel();
                }
            }
        }
    }
}
