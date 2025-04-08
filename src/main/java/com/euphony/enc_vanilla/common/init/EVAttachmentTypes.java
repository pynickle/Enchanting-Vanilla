package com.euphony.enc_vanilla.common.init;

import com.euphony.enc_vanilla.EncVanilla;
import com.mojang.serialization.Codec;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class EVAttachmentTypes {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, EncVanilla.MODID);

    public static final Supplier<AttachmentType<Integer>> BELL_TIME = ATTACHMENT_TYPES.register(
            "bell_time", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build()
    );
}
