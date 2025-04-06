package com.euphony.enc_vanilla.client;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.common.init.EVBlocks;
import com.euphony.enc_vanilla.common.init.EVItems;
import com.euphony.enc_vanilla.common.item.SculkCompassItem;
import com.euphony.enc_vanilla.config.client.EVConfigScreen;
import com.euphony.enc_vanilla.utils.CompassState;
import com.euphony.enc_vanilla.utils.Utils;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;


@EventBusSubscriber(modid = EncVanilla.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EVClient {
    @SubscribeEvent
    public static void registerBlockColor(RegisterColorHandlersEvent.Block event) {
        event.register(
                (state, level, pos, tintIndex) -> 2129968,
                EVBlocks.WATERLOGGED_LILY_PAD.get()
        );
        event.register(
                (state, level, pos, tintIndex) ->
                        level != null && pos != null ? BiomeColors.getAverageFoliageColor(level, pos) : FoliageColor.getDefaultColor(),
                EVBlocks.CUT_VINE.get()
        );
        event.register(
                (state, level, pos, tintIndex) -> level != null && pos != null ? BiomeColors.getAverageGrassColor(level, pos) : -1,
                EVBlocks.CUT_SUGAR_CANE.get()
        );
    }

    @SubscribeEvent
    public static void clientInit(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            if (Utils.isModLoaded("yet_another_config_lib_v3")) {
                ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> (client, screen) -> new EVConfigScreen(screen));
            }
            ItemProperties.register(EVItems.SCULK_COMPASS_ITEM.get(), ResourceLocation.fromNamespaceAndPath(EncVanilla.MODID, "angle"), new ClampedItemPropertyFunction() {
                private final CompassWobble wobble = new CompassWobble();
                private final CompassWobble wobbleRandom = new CompassWobble();

                public float unclampedCall(@NotNull ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity p_entity, int seed) {
                    Entity entity = p_entity != null ? p_entity : stack.getEntityRepresentation();
                    if (entity == null) {
                        return 0.0F;
                    } else {
                        level = this.tryFetchLevelIfMissing(entity, level);
                        return level == null ? 0.0F : this.getCompassRotation(stack, level, seed, entity);
                    }
                }

                private float getCompassRotation(ItemStack stack, ClientLevel level, int seed, Entity entity) {
                    SculkCompassItem compassTarget = (SculkCompassItem) stack.getItem();
                    BlockPos blockPos = new BlockPos(compassTarget.getFoundBiomeX(stack), 0, compassTarget.getFoundBiomeZ(stack));
                    long i = level.getGameTime();
                    return compassTarget.getState(stack) == CompassState.FOUND ? this.getRotationTowardsCompassTarget(entity, i, blockPos) : this.getRandomlySpinningRotation(seed, i);
                }

                private float getRandomlySpinningRotation(int seed, long ticks) {
                    if (this.wobbleRandom.shouldUpdate(ticks)) {
                        this.wobbleRandom.update(ticks, Math.random());
                    }

                    double d0 = this.wobbleRandom.rotation + (double)((float)this.hash(seed) / (float)Integer.MAX_VALUE);
                    return Mth.positiveModulo((float)d0, 1.0F);
                }

                private float getRotationTowardsCompassTarget(Entity entity, long ticks, BlockPos pos) {
                    double d0 = this.getAngleFromEntityToPos(entity, pos);
                    double d1 = this.getWrappedVisualRotationY(entity);
                    if (entity instanceof Player player) {
                        if (player.isLocalPlayer() && player.level().tickRateManager().runsNormally()) {
                            if (this.wobble.shouldUpdate(ticks)) {
                                this.wobble.update(ticks, (double)0.5F - (d1 - (double)0.25F));
                            }

                            double d3 = d0 + this.wobble.rotation;
                            return Mth.positiveModulo((float)d3, 1.0F);
                        }
                    }

                    double d2 = (double)0.5F - (d1 - (double)0.25F - d0);
                    return Mth.positiveModulo((float)d2, 1.0F);
                }

                @Nullable
                private ClientLevel tryFetchLevelIfMissing(Entity entity, @Nullable ClientLevel level) {
                    return level == null && entity.level() instanceof ClientLevel ? (ClientLevel)entity.level() : level;
                }

                private double getAngleFromEntityToPos(Entity entity, BlockPos pos) {
                    Vec3 vec3 = Vec3.atCenterOf(pos);
                    return Math.atan2(vec3.z() - entity.getZ(), vec3.x() - entity.getX()) / (double)((float)Math.PI * 2F);
                }

                private double getWrappedVisualRotationY(Entity entity) {
                    return Mth.positiveModulo(entity.getVisualRotationYInDegrees() / 360.0F, (double)1.0F);
                }

                private int hash(int value) {
                    return value * 1327217883;
                }

                @OnlyIn(Dist.CLIENT)
                static class CompassWobble {
                    double rotation;
                    private double deltaRotation;
                    private long lastUpdateTick;

                    CompassWobble() {
                    }

                    boolean shouldUpdate(long ticks) {
                        return this.lastUpdateTick != ticks;
                    }

                    void update(long ticks, double rotation) {
                        this.lastUpdateTick = ticks;
                        double d0 = rotation - this.rotation;
                        d0 = Mth.positiveModulo(d0 + (double)0.5F, 1.0F) - (double)0.5F;
                        this.deltaRotation += d0 * 0.1;
                        this.deltaRotation *= 0.8;
                        this.rotation = Mth.positiveModulo(this.rotation + this.deltaRotation, 1.0F);
                    }
                }
            });
        });
    }
}
