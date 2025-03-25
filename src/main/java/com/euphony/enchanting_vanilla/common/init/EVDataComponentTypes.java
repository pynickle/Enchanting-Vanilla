package com.euphony.enchanting_vanilla.common.init;

import com.euphony.enchanting_vanilla.EnchantingVanilla;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class EVDataComponentTypes {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, EnchantingVanilla.MODID);

    // public static final Supplier<DataComponentType<Boolean>> IS_ACTIVE = DATA_COMPONENTS.registerComponentType("is_active", builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));
}
