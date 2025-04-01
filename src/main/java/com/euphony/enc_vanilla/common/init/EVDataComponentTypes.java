package com.euphony.enc_vanilla.common.init;

import com.euphony.enc_vanilla.EncVanilla;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Unit;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EVDataComponentTypes {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, EncVanilla.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ResourceKey<Biome>>> BIOME = DATA_COMPONENTS.register("biome", () -> DataComponentType.<ResourceKey<Biome>>builder().persistent(ResourceKey.codec(Registries.BIOME)).networkSynchronized(ResourceKey.streamCodec(Registries.BIOME)).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> COMPASS_STATE = DATA_COMPONENTS.register("compass_state", () -> DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> FOUND_X = DATA_COMPONENTS.register("found_x", () -> DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> FOUND_Z = DATA_COMPONENTS.register("found_z", () -> DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.VAR_INT).build());
}
