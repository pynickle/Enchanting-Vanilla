package com.euphony.enc_vanilla;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.DoubleValue;
import net.neoforged.neoforge.common.ModConfigSpec.EnumValue;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;

import java.util.List;


public class EVConfig {
    private final ClientConfig client = new ClientConfig();
    private final CommonConfig common = new CommonConfig();

    private static EVConfig instance;

    private EVConfig(ModContainer container) {
        container.registerConfig(ModConfig.Type.CLIENT, client.spec);
        container.registerConfig(ModConfig.Type.COMMON, common.spec);
    }

    public static void register(ModContainer container) {
        if (!container.getModId().equals(EncVanilla.MODID)) {
            throw new IllegalArgumentException();
        }
        instance = new EVConfig(container);
    }

    public static EVConfig instance() {
        return instance;
    }

    public boolean enabledVillagerAttraction() {
        return common.enabledVillagerAttraction.getAsBoolean();
    }

    public boolean enabledInvisibleItemFrame() {
        return common.enabledInvisibleItemFrame.getAsBoolean();
    }

    public boolean enabledBlocksOnLilyPad() {
        return common.enabledBlocksOnLilyPad.getAsBoolean();
    }

    public boolean enabledSlimeChunkDetecting() {
        return common.enabledSlimeChunkDetecting.getAsBoolean();
    }

    public boolean enabledPaintingSwitching() {
        return common.enabledPaintingSwitching.getAsBoolean();
    }

    public boolean enabledCutVine() {
        return common.enabledCutVine.getAsBoolean();
    }

    public boolean enabledStopGrowing() {
        return common.enabledStopGrowing.getAsBoolean();
    }

    public boolean enabledCompressSlimeBlock() {
        return common.enabledCompressSlimeBlock.getAsBoolean();
    }

    public boolean enabledTorchHit() {
        return common.enabledTorchHit.getAsBoolean();
    }

    public boolean enabledMobTorchHit() {
        return common.enabledMobTorchHit.getAsBoolean();
    }

    public int torchHitFireChance() {
        return common.torchHitFireChance.getAsInt();
    }

    public double torchHitFireDuration() {
        return common.torchHitFireDuration.getAsDouble();
    }

    public List<? extends String> getExtraTorchItems() {
        return common.extraTorchItems.get();
    }

    public List<? extends String> getExtraSoulTorchItems() {
        return common.extraSoulTorchItems.get();
    }

    public boolean enabledSlabsToBlocks() {
        return common.enabledSlabsToBlocks.getAsBoolean();
    }

    public boolean enabledSpongePlace() {
        return common.enabledSpongePlace.getAsBoolean();
    }

    public boolean enabledSculkCompass() {
        return common.enabledSculkCompass.getAsBoolean();
    }

    public boolean enabledFarmlandTramplingPrevention() {
        return common.enabledFarmlandTramplingPrevention.getAsBoolean();
    }

    public void save() {
        common.spec.save();
        client.spec.save();
    }

    private static class ClientConfig {
        private final ModConfigSpec spec;

        public ClientConfig() {
            var builder = new ModConfigSpec.Builder();
            this.spec = builder.build();
        }

    }

    private static class CommonConfig {
        private final ModConfigSpec spec;

        public final BooleanValue enabledVillagerAttraction;
        public final BooleanValue enabledInvisibleItemFrame;
        public final BooleanValue enabledBlocksOnLilyPad;
        public final BooleanValue enabledSlimeChunkDetecting;
        public final BooleanValue enabledPaintingSwitching;
        public final BooleanValue enabledCutVine;
        public final BooleanValue enabledStopGrowing;
        public final BooleanValue enabledCompressSlimeBlock;

        public final BooleanValue enabledTorchHit;
        public final BooleanValue enabledMobTorchHit;
        public IntValue torchHitFireChance;
        public DoubleValue torchHitFireDuration;
        private ModConfigSpec.ConfigValue<List<? extends String>> extraTorchItems;
        private ModConfigSpec.ConfigValue<List<? extends String>> extraSoulTorchItems;

        public final BooleanValue enabledSlabsToBlocks;
        public final BooleanValue enabledSpongePlace;
        public final BooleanValue enabledSculkCompass;
        public final BooleanValue enabledFarmlandTramplingPrevention;

        public CommonConfig() {
            var builder = new ModConfigSpec.Builder();

            builder.push("general");
            enabledVillagerAttraction = builder.define("enabledVillagerAttraction", true);
            enabledInvisibleItemFrame = builder.define("enabledInvisibleItemFrame", true);
            enabledBlocksOnLilyPad = builder.define("enabledBlocksOnLilyPad", true);
            enabledSlimeChunkDetecting = builder.define("enabledSlimeChunkDetecting", true);
            enabledPaintingSwitching = builder.define("enabledPaintingSwitching", true);
            enabledCutVine = builder.define("enabledCutVine", true);
            enabledStopGrowing = builder.define("enabledStopGrowing", true);
            enabledCompressSlimeBlock = builder.define("enabledCompressSlimeBlock", true);

            enabledTorchHit = builder.define("enabledTorchHit", true);
            enabledMobTorchHit = builder
                    .comment("Whether or not an attack by a mob with a torch can set an enemy on fire.")
                    .define("enabledMobTorchHit", true);
            torchHitFireChance = builder
                    .comment("The chance of a torch hit setting an enemy on fire.")
                            .defineInRange("torchHitFireChance", 50, 0, 100);
            torchHitFireDuration = builder
                    .comment("The duration of the fire effect in seconds.")
                    .defineInRange("torchHitFireDuration", 3.0, 0, 10);
            extraTorchItems = builder.comment(" List of item ids that should be considered as a Torch.").defineListAllowEmpty(
                    "extra torch items",
                    () -> List.of(
                            "bonetorch:bonetorch",
                            "torchmaster:megatorch",
                            "hardcore_torches:lit_torch",
                            "magnumtorch:diamond_magnum_torch",
                            "magnumtorch:emerald_magnum_torch",
                            "magnumtorch:amethyst_magnum_torch",
                            "magical_torches:mega_torch",
                            "magical_torches:grand_torch",
                            "magical_torches:medium_torch",
                            "magical_torches:small_torch",
                            "pgwbandedtorches:banded_torch_white",
                            "pgwbandedtorches:banded_torch_orange",
                            "pgwbandedtorches:banded_torch_magenta",
                            "pgwbandedtorches:banded_torch_light_blue",
                            "pgwbandedtorches:banded_torch_yellow",
                            "pgwbandedtorches:banded_torch_lime",
                            "pgwbandedtorches:banded_torch_pink",
                            "pgwbandedtorches:banded_torch_gray",
                            "pgwbandedtorches:banded_torch_light_gray",
                            "pgwbandedtorches:banded_torch_cyan",
                            "pgwbandedtorches:banded_torch_purple",
                            "pgwbandedtorches:banded_torch_blue",
                            "pgwbandedtorches:banded_torch_brown",
                            "pgwbandedtorches:banded_torch_green",
                            "pgwbandedtorches:banded_torch_red",
                            "pgwbandedtorches:banded_torch_black"
                    ),
                    this::stringListValidator
            );
            extraTorchItems = builder
                    .comment("Items that could be considered torches")
                    .defineListAllowEmpty("extraTorchItems", List.of(
                            "torchmaster:megatorch",
                            "magnumtorch:diamond_magnum_torch",
                            "magnumtorch:emerald_magnum_torch",
                            "magnumtorch:amethyst_magnum_torch",
                            "bonetorch:bonetorch"
                    ), () -> "", this::stringListValidator);
            extraSoulTorchItems = builder
                    .comment("Items that could be considered soul torches")
                    .defineListAllowEmpty("extraSoulTorchItems", List::of, () -> "", this::stringListValidator);

            enabledSlabsToBlocks = builder.define("enabledSlabsToBlocks", true);
            enabledSpongePlace = builder.define("enabledSpongePlace", true);
            enabledSculkCompass = builder.define("enabledSculkCompass", true);
            enabledFarmlandTramplingPrevention = builder.define("enabledFarmlandTramplingPrevention", true);
            builder.pop();

            spec = builder.build();
        }

        public boolean stringListValidator(Object element) {
            return element instanceof String string && !string.isBlank();
        }
    }
}
