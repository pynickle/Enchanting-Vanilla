package com.euphony.enc_vanilla.utils;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.WeakHashMap;

public class BlockEntityMap {
    public static HashMap<BlockEntityType<?>, WeakHashMap<Level, ArrayList<BlockEntity>>> cachedBlockEntities = new HashMap<>();

    public static void addBlockEntity(BlockEntityType<?> blockEntityType) {
        if (!cachedBlockEntities.containsKey(blockEntityType)) {
            cachedBlockEntities.put(blockEntityType, new WeakHashMap<>());
        }
    }
}
