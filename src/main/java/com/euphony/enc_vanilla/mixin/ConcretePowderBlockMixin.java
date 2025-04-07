package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.api.IConcretePowderBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ConcretePowderBlock;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ConcretePowderBlock.class)
public abstract class ConcretePowderBlockMixin implements IConcretePowderBlock {
    @Shadow
    @Final
    private Block concrete;

    @Override
    public Block enc_vanilla$getConcrete() {
        return concrete;
    }
}
