package com.euphony.enc_vanilla.common.condition;

import com.euphony.enc_vanilla.config.categories.RecipesConfig;
import com.euphony.enc_vanilla.config.categories.ToolsConfig;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.jetbrains.annotations.NotNull;

public record BoolConfigCondition(String boolConfig) implements ICondition {
    public static final MapCodec<BoolConfigCondition> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Codec.STRING.fieldOf("boolConfig").forGetter(BoolConfigCondition::boolConfig)
    ).apply(inst, BoolConfigCondition::new));

    @Override
    public boolean test(ICondition.@NotNull IContext context) {
        switch (boolConfig) {
            default:
                return true;
            case "moreCompostable":
                return RecipesConfig.HANDLER.instance().enableMoreCompostable;
            case "sculkCompass":
                return ToolsConfig.HANDLER.instance().enableSculkCompass;
            case "spongeCampfire":
                return RecipesConfig.HANDLER.instance().enableSpongeCampfire;
        }
    }

    @Override
    public MapCodec<? extends ICondition> codec() {
        return CODEC;
    }
}
