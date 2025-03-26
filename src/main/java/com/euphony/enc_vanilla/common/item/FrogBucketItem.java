package com.euphony.enc_vanilla.common.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class FrogBucketItem extends MobBucketItem {
    public FrogBucketItem(Properties properties) {
        super(EntityType.FROG, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, properties.component(DataComponents.CUSTOM_MODEL_DATA, CustomModelData.DEFAULT));
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level world, @NotNull Entity entity, int itemSlot, boolean isSelected) {
        if(world instanceof ServerLevel serverLevel) {
            Vec3 pos = entity.position();
            int x = Mth.floor(pos.x);
            int z = Mth.floor(pos.z);
            boolean isActive = stack.get(DataComponents.CUSTOM_MODEL_DATA).value() == 1;
            if(isActive != isSlimeChunk(serverLevel, x, z))
                stack.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(isActive ? 0 : 1));
        }
    }

    public static boolean isSlimeChunk(ServerLevel world, int x, int z) {
        ChunkPos chunkpos = new ChunkPos(new BlockPos(x, 0, z));
        return WorldgenRandom.seedSlimeChunk(chunkpos.x, chunkpos.z, world.getSeed(), 987234911L).nextInt(10) == 0;
    }
}
