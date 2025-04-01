package com.euphony.enc_vanilla.utils;

import net.minecraft.world.phys.AABB;

public class AABBUtils {
    public static boolean contains(AABB aabb, AABB target) {
        return aabb.minX < target.minX && aabb.maxX > target.maxX && aabb.minY < target.minY && aabb.maxY > target.maxY && aabb.minZ < target.minZ && aabb.maxZ > target.maxZ;
    }
}
