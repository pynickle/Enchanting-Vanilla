package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.EVConfig;
import com.euphony.enc_vanilla.common.init.EVBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(WaterlilyBlock.class)
public abstract class WaterlilyBlockMixin extends Block {
    public WaterlilyBlockMixin(Properties properties) {
        super(properties);
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        Item item = stack.getItem();

        if(!enc_vanilla$canPlaceBlock(player, pos, stack) || !EVConfig.instance().enabledBlocksOnLilyPad()) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        if (!stack.isEmpty() && !(item instanceof PlaceOnWaterBlockItem) && !(stack.getItem() instanceof BoneMealItem)) {
            BlockPos below = pos.below();
            if (level.getBlockState(below).is(Blocks.WATER)) {
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
                level.setBlock(below, EVBlocks.WATERLOGGED_LILY_PAD.get().withPropertiesOf(state), 2);
                level.scheduleTick(below, EVBlocks.WATERLOGGED_LILY_PAD.get(), 1);
            }
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Unique
    private static boolean enc_vanilla$canPlaceBlock(Player player, BlockPos pos, ItemStack stack) {
        GameType gameMode;
        if (player instanceof ServerPlayer serverPlayer) {
            gameMode = serverPlayer.gameMode.getGameModeForPlayer();
        } else {
            gameMode = Minecraft.getInstance().gameMode.getPlayerMode();
        }

        boolean result = !player.blockActionRestricted(player.level(), pos, gameMode);
        if (!result) {
            if (gameMode == GameType.ADVENTURE && !stack.isEmpty()) {
                AdventureModePredicate adventureModePredicate = stack.get(DataComponents.CAN_PLACE_ON);
                if (adventureModePredicate != null && adventureModePredicate.test(
                        new BlockInWorld(player.level(), pos, false))) {
                    return true;
                }
            }
        }
        return result;
    }
}
