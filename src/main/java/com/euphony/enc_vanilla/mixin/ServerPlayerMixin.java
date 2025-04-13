package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import com.mojang.datafixers.util.Either;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Unit;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Player.BedSleepingProblem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ServerPlayer.class})
public abstract class ServerPlayerMixin extends Entity {
    public ServerPlayerMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Inject(method = {"startSleepInBed"}, at = @At("RETURN"))
    public void highlightMobs(BlockPos at, CallbackInfoReturnable<Either<BedSleepingProblem, Unit>> cir) {
        if(!QolConfig.HANDLER.instance().enableHighlightMobs) return;

        Optional<Player.BedSleepingProblem> reason = (cir.getReturnValue()).left();
        if (reason.isPresent() && reason.get() == BedSleepingProblem.NOT_SAFE) {
            Vec3 vec3 = Vec3.atBottomCenterOf(at);

            int duration = (int) (QolConfig.HANDLER.instance().highlightDuration * 20);

            for(Monster entity : this.level().getEntitiesOfClass(Monster.class, new AABB(vec3.x - (double)8.0F, vec3.y - (double)5.0F, vec3.z - (double)8.0F, vec3.x + (double)8.0F, vec3.y + (double)5.0F, vec3.z + (double)8.0F), (hostileEntity) -> hostileEntity.isPreventingPlayerRest((Player)(Object) this))) {
                entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, duration, 1, false, false));
            }
        }

    }
}