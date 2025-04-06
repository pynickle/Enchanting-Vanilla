package com.euphony.enc_vanilla.events;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import net.mehvahdjukaar.fastpaintings.PaintingBlockEntity;
import net.minecraft.ResourceLocationException;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.PaintingVariantTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@EventBusSubscriber(modid = EncVanilla.MODID)
public class SwitchPaintingEvent {
    @SubscribeEvent
    public static void entityInteract(PlayerInteractEvent.EntityInteract event) {
        if(!QolConfig.HANDLER.instance().enablePaintingSwitching) {
            return;
        }

        Level level = event.getLevel();
        if (level.isClientSide) {
            return;
        }

        InteractionHand hand = event.getHand();
        if(hand != InteractionHand.OFF_HAND) {
            return;
        }

        Player player = event.getEntity();
        if (!player.getMainHandItem().isEmpty() || !player.getOffhandItem().isEmpty()) {
            return;
        }

        Entity entity = event.getTarget();
        if(entity.getType() == EntityType.PAINTING) {
            Painting painting = (Painting) entity;
            List<Holder<PaintingVariant>> list = new ArrayList<>();
            level.registryAccess().registryOrThrow(Registries.PAINTING_VARIANT).getTagOrEmpty(PaintingVariantTags.PLACEABLE).forEach(list::add);
            if (!list.isEmpty()) {
                list.removeIf(p_344343_ -> {
                    painting.setVariant((Holder<PaintingVariant>)p_344343_);
                    return !painting.survives();
                });
                list.remove(painting.getVariant());
                if (!list.isEmpty()) {
                    int i = list.stream().mapToInt(SwitchPaintingEvent::variantArea).max().orElse(0);
                    list.removeIf(p_218883_ -> variantArea(p_218883_) < i);
                    Optional<Holder<PaintingVariant>> optional = Util.getRandomSafe(list, painting.getRandom());
                    optional.ifPresent(painting::setVariant);
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void blockInteract(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        if (level.isClientSide) {
            return;
        }

        InteractionHand hand = event.getHand();
        if(hand != InteractionHand.OFF_HAND) {
            return;
        }

        Player player = event.getEntity();
        if (!player.getMainHandItem().isEmpty() || !player.getOffhandItem().isEmpty()) {
            return;
        }

        BlockPos blockPos = event.getPos();
        BlockEntity blockEntity = level.getBlockEntity(blockPos);

        if(blockEntity instanceof PaintingBlockEntity painting) {
            CompoundTag tag = painting.getUpdateTag(level.registryAccess());

            String rawVariantResourceLocation = tag.getString("variant");

            ResourceLocation variantResourceLocation;
            try {
                variantResourceLocation = ResourceLocation.parse(rawVariantResourceLocation);
            }
            catch (ResourceLocationException ex) {
                return;
            }

            Registry<PaintingVariant> paintingVariantRegistry = level.registryAccess().registryOrThrow(Registries.PAINTING_VARIANT);
            Optional<PaintingVariant> optionalPaintingVariant = paintingVariantRegistry.getOptional(variantResourceLocation);
            if (optionalPaintingVariant.isPresent()) {
                Holder<PaintingVariant> paintingVariantHolder = paintingVariantRegistry.wrapAsHolder(optionalPaintingVariant.get());
                List<Holder<PaintingVariant>> list = new ArrayList<>();
                level.registryAccess().registryOrThrow(Registries.PAINTING_VARIANT).getTagOrEmpty(PaintingVariantTags.PLACEABLE).forEach(list::add);
                if (!list.isEmpty()) {
                    list.remove(paintingVariantHolder);
                    if (!list.isEmpty()) {
                        int width = paintingVariantHolder.value().width();
                        int height = paintingVariantHolder.value().height();
                        list.removeIf(p_218883_ -> p_218883_.value().width() != width || p_218883_.value().height() != height);
                        Optional<Holder<PaintingVariant>> optional = Util.getRandomSafe(list, RandomSource.create());
                        optional.ifPresent(painting::setVariant);
                        level.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
                        Direction dir = painting.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
                        for(int x = 0; x < width; x++) {
                            for(int y = 0; y < height; y++) {
                                BlockPos newPos = blockPos.below(y).relative(dir.getCounterClockWise(), x);
                                player.sendSystemMessage(Component.literal(newPos.toString()));
                                Painting painting1 = new Painting(level, newPos, dir, optional.get());
                                BlockPos pos = getBlockPos(painting1, dir);
                                player.sendSystemMessage(Component.literal(pos.toString()));
                                player.sendSystemMessage(Component.empty());
                                if (pos.getX() == blockPos.getX() && pos.getZ() == blockPos.getZ() && pos.getY() == blockPos.getY()) {
                                    player.sendSystemMessage(Component.literal("1"));
                                    level.addFreshEntity(painting1);
                                    event.setCanceled(true);
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static @NotNull BlockPos getBlockPos(Painting painting1, Direction dir) {
        AABB bb = painting1.getBoundingBox();
        BlockPos pos = switch (dir) {
            case SOUTH -> BlockPos.containing(bb.minX, bb.maxY - 0.5, bb.maxZ);
            case WEST -> BlockPos.containing(bb.minX, bb.maxY - 0.5, bb.minZ);
            case EAST -> BlockPos.containing(bb.maxX, bb.maxY - 0.5, bb.maxZ - 0.5);
            default -> BlockPos.containing(bb.maxX - 0.5, bb.maxY - 0.5, bb.minZ);
        };
        return pos;
    }

    private static int variantArea(Holder<PaintingVariant> variant) {
        return variant.value().area();
    }
}
