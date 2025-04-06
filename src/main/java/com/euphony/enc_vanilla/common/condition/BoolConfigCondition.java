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
        return switch (boolConfig) {
            case "moreCompostable" -> RecipesConfig.HANDLER.instance().enableMoreCompostable;
            case "sculkCompass" -> ToolsConfig.HANDLER.instance().enableSculkCompass;
            case "spongeCampfire" -> RecipesConfig.HANDLER.instance().enableSpongeCampfire;
            default -> true;
        };
    }

    @Override
    public MapCodec<? extends ICondition> codec() {
        return CODEC;
    }
}
