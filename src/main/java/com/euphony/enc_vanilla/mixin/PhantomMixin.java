package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.common.init.EVAttachmentTypes;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Phantom.class)
public abstract class PhantomMixin extends FlyingMob implements Enemy {
    protected PhantomMixin(EntityType<? extends FlyingMob> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tickInject(CallbackInfo ci) {
        if(!this.level().isClientSide) {
            if(this.getData(EVAttachmentTypes.BELL_TIME) > 0) {
                this.setData(EVAttachmentTypes.BELL_TIME, this.getData(EVAttachmentTypes.BELL_TIME) - 1);
                ServerLevel serverLevel = (ServerLevel) this.level();
                float f = Mth.cos((float) (this.getId() * 3 + this.tickCount) * 7.448451F * ((float) Math.PI / 180F) + (float) Math.PI);
                float f2 = this.getBbWidth() * 1.48F;
                float f3 = Mth.cos(this.getYRot() * ((float) Math.PI / 180F)) * f2;
                float f4 = Mth.sin(this.getYRot() * ((float) Math.PI / 180F)) * f2;
                float f5 = (0.3F + f * 0.45F) * this.getBbHeight() * 2.5F;
                serverLevel.sendParticles(ParticleTypes.PORTAL, this.getX() + f3, this.getY() + f5, this.getZ() + f4, 1, 0.0D, 0.0D, 0.0D, 0.0D);
                serverLevel.sendParticles(ParticleTypes.PORTAL, this.getX() - f3, this.getY() + f5, this.getZ() - f4, 1, 0.0D, 0.0D, 0.0D, 0.0D);
            }
        }
    }
}
