package com.euphony.enchanting_vanilla.mixin;

import com.euphony.enchanting_vanilla.common.init.EVBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(WaterlilyBlock.class)
public abstract class WaterlilyBlockMixin extends Block {
    @Shadow protected abstract boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos);

    public WaterlilyBlockMixin(Properties properties) {
        super(properties);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        Item item = stack.getItem();

        if(!enchanting_vanilla$canPlaceBlock(player, pos, stack)) {
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
    private static boolean enchanting_vanilla$canPlaceBlock(Player player, BlockPos pos, ItemStack stack) {
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
