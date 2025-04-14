package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Monster.class)
public abstract class MonsterMixin extends PathfinderMob implements Enemy {
    protected MonsterMixin(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @ModifyReturnValue(method = "shouldDespawnInPeaceful", at = @At("RETURN"))
    protected boolean shouldDespawnInPeaceful(boolean original) {
        if(QolConfig.HANDLER.instance().enableNameTagDespawn) {
            return !this.hasCustomName();
        }
        return true;
    }
}