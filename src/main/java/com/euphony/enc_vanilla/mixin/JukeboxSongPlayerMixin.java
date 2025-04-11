package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import net.minecraft.core.Holder;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.item.JukeboxSongPlayer;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(JukeboxSongPlayer.class)
public abstract class JukeboxSongPlayerMixin {
    @Shadow
    private Holder<JukeboxSong> song;
    @Shadow
    private long ticksSinceSongStarted;

    @Shadow public abstract void play(LevelAccessor level, Holder<JukeboxSong> song);

    @Inject(method = "tick", at = @At("HEAD"))
    public void tickInject(LevelAccessor level, BlockState state, CallbackInfo ci) {
        if(!QolConfig.HANDLER.instance().enableJukeboxLoop)
            return;
        if (this.song != null) {
            if (this.song.value().hasFinished(this.ticksSinceSongStarted)) {
                play(level, song);
            }
        }

    }
}
