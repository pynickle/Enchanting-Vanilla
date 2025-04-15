package com.euphony.enc_vanilla.client.events;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.config.categories.ClientConfig;
import com.euphony.enc_vanilla.utils.Utils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;

@EventBusSubscriber(modid = EncVanilla.MODID, value = Dist.CLIENT, bus = Bus.GAME)
public class BiomeTitleEvent {
    public static Biome previousBiome;
    public static ResourceKey<Biome> displayBiome;
    public static int displayTime = 0;
    public static int alpha = 0;
    public static int cooldownTime = 0;
    public static int fadeTimer = 0;
    public static boolean complete = false;
    public static boolean fadingIn = false;
    public static final Map<ResourceKey<Biome>, Component> NAME_CACHE = new HashMap<>();

    private BiomeTitleEvent() {}

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Pre event) {
        if (complete) {
            if (!fadingIn) {
                if (displayTime > 0) {
                    displayTime--;
                }
                else if (fadeTimer > 0) {
                    fadeTimer--;
                    alpha = (int) (255F / (float) ClientConfig.HANDLER.instance().fadeOutTime * fadeTimer);
                }
                else {
                    if (cooldownTime > 0) {
                        cooldownTime--;
                    }
                }
            }
            else {
                if(fadeTimer < ClientConfig.HANDLER.instance().fadeInTime) {
                    fadeTimer++;
                    alpha = (int) (255F / (float) ClientConfig.HANDLER.instance().fadeInTime * fadeTimer);
                } else {
                    fadeTimer = ClientConfig.HANDLER.instance().fadeOutTime;
                    fadingIn = false;
                    displayTime = (int) (ClientConfig.HANDLER.instance().displayDuration * 20);
                    alpha = 255;
                }
            }
        }
    }

    public static void renderBiomeInfo(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        if (complete && ClientConfig.HANDLER.instance().enableBiomeTitle) {
            Minecraft mc = Minecraft.getInstance();

            if (hideInF1(mc) || hideInF3(mc))
                return;

            Entity player = mc.getCameraEntity();

            if(player == null)
                return;
            BlockPos pos = player.getOnPos();

            if (mc.level != null && mc.level.isLoaded(pos)) {
                Holder<Biome> biomeHolder = mc.level.getBiome(pos);

                if (!biomeHolder.isBound())
                    return;

                Biome biome = biomeHolder.value();

                boolean isPlayerUnderground = mc.level.dimensionType().hasSkyLight() && !mc.level.canSeeSky(pos);
                boolean shouldUpdate = ClientConfig.HANDLER.instance().enableUndergroundUpdate || !isPlayerUnderground;

                if (previousBiome != biome) {
                    previousBiome = biome;
                    if(cooldownTime == 0 && shouldUpdate) {
                        biomeHolder.unwrapKey().ifPresent(key -> {
                            cooldownTime = (int) (ClientConfig.HANDLER.instance().cooldownTime * 20);
                            displayBiome = key;

                            displayTime = 0;
                            alpha = 0;
                            fadingIn = true;
                        });
                    }
                }

                if (alpha > 0) {
                    Font font = mc.font;
                    float scale = (float) ClientConfig.HANDLER.instance().scale;

                    PoseStack pose = guiGraphics.pose();
                    pose.pushPose();
                    pose.translate(guiGraphics.guiWidth() / 2D, guiGraphics.guiHeight() / 2D, 0);
                    pose.scale(scale, scale, scale);

                    Component biomeName = getBiomeName(displayBiome);
                    int textWidth = font.width(biomeName);

                    int y = - font.wordWrapHeight(biomeName.getString(), 999) / 2 + ClientConfig.HANDLER.instance().yOffset;

                    guiGraphics.drawString(font, biomeName, (-textWidth / 2), y, 0xffffff | (alpha << 24), true);
                    pose.popPose();
                }
            }
        }
    }

    private static Component getBiomeName(ResourceKey<Biome> key) {
        return NAME_CACHE.computeIfAbsent(key, k -> {
            ResourceLocation location = key.location();
            String translationKey = Util.makeDescriptionId("biome", location);
            MutableComponent biomeName = Component.translatable(translationKey);
            MutableComponent displayName = biomeName;

            String displayedText = biomeName.getString();

            if (displayedText.equals(translationKey)) {
                String biomePath = key.location().getPath();
                String formattedBiomeName = snakeCaseToEnglish(biomePath);

                displayName = Component.literal(formattedBiomeName);
            }

            if (ClientConfig.HANDLER.instance().enableModName) {
                String modName = getModName(location);

                if (modName != null)
                    displayName = displayName.append(Component.literal(String.format(" (%s)", modName)));
            }

            return displayName;
        });
    }

    private static boolean hideInF1(Minecraft mc) {
        return mc.options.hideGui && ClientConfig.HANDLER.instance().hideInF1;
    }

    private static boolean hideInF3(Minecraft mc) {
        return mc.getDebugOverlay().showDebugScreen() && ClientConfig.HANDLER.instance().hideInF3;
    }

    private static String snakeCaseToEnglish(String biomePath) {
        String[] words = biomePath.split("_");
        StringJoiner formatted = new StringJoiner(" ");

        for (String word : words) {
            formatted.add(StringUtils.capitalize(word));
        }

        return formatted.toString();
    }

    private static String getModName(ResourceLocation location) {
        String modid = location.getNamespace();

        String displayName = Utils.getModDisplayName(modid);

        return displayName == null ? snakeCaseToEnglish(modid) : displayName;
    }

    public static void setComplete(boolean complete) {
        BiomeTitleEvent.complete = complete;
    }
}