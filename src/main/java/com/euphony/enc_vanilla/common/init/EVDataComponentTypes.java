package com.euphony.enc_vanilla.common.init;

import com.euphony.enc_vanilla.EnchantingVanilla;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EVDataComponentTypes {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, EnchantingVanilla.MODID);
}
