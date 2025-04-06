package com.euphony.enc_vanilla.common.init;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.common.condition.BoolConfigCondition;
import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class EVConditions {
    public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITION_CODECS =
            DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS, EncVanilla.MODID);

    public static final Supplier<MapCodec<BoolConfigCondition>> MORE_COMPOSTABLE =
            CONDITION_CODECS.register("bool_config", () -> BoolConfigCondition.CODEC);
}
