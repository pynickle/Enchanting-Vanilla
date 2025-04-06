package com.euphony.enc_vanilla.events.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.EntityEvent;
import org.jetbrains.annotations.NotNull;


public class AnvilFallOnLandEvent extends EntityEvent {
    private final FallingBlockEntity entity;
    private final float fallDistance;
    private final Level level;
    private final BlockPos pos;

    public Level getLevel() {
        return level;
    }

    public BlockPos getPos() {
        return pos;
    }

    public AnvilFallOnLandEvent(Level level, BlockPos pos, FallingBlockEntity entity, float fallDistance) {
        super(entity);
        this.entity = entity;
        this.level = level;
        this.pos = pos;
        this.fallDistance = fallDistance;
    }

    public float getFallDistance() {
        return fallDistance;
    }

    @Override
    public @NotNull FallingBlockEntity getEntity() {
        return entity;
    }
}