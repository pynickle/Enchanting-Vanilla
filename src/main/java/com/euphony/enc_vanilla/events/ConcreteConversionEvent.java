package com.euphony.enc_vanilla.events;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.api.IConcretePowderBlock;
import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ConcretePowderBlock;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = EncVanilla.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ConcreteConversionEvent {
    @SubscribeEvent
    public static void itemTossEvent(ItemTossEvent event) {
        ItemEntity entity = event.getEntity();
        if (!entity.getCommandSenderWorld().isClientSide()) {
            addPlayerThrownItemEntity(entity);
        }
    }

    @SubscribeEvent
    public static void worldTickEvent(LevelTickEvent.Pre event) {
        Level level = event.getLevel();
        if (!level.isClientSide()) {
            itemEntityCheck();
        }
    }

    private static final List<ItemEntity> entities = new ArrayList<>();

    private static void addPlayerThrownItemEntity(ItemEntity entity) {
        if (QolConfig.HANDLER.instance().enableWaterConversion && (isConcretePowder(entity) || (QolConfig.HANDLER.instance().enableMudConversion && isConvertableToMud(entity))))
            if(!entities.contains(entity)) entities.add(entity);
    }

    private static boolean isConcretePowder(ItemEntity entity) {
        return Block.byItem(entity.getItem().getItem()) instanceof ConcretePowderBlock;
    }

    private static boolean isConvertableToMud(ItemEntity entity) {
        return Block.byItem(entity.getItem().getItem()).defaultBlockState().is(BlockTags.CONVERTABLE_TO_MUD);
    }

    private static void itemEntityCheck() {
        entities.forEach(entity -> {
            if (entity.isAlive() && entity.isInWater()) {
                convertItemEntity(entity);
            }
        });
    }

    private static void convertItemEntity(ItemEntity entity) {
        ItemStack stack = entity.getItem();
        Block block = null;
        if (isConcretePowder(entity))
            block = ((IConcretePowderBlock)(Block.byItem(stack.getItem()))).enc_vanilla$getConcrete();
        else if (QolConfig.HANDLER.instance().enableMudConversion && isConvertableToMud(entity))
            block = Blocks.MUD;

        if (block != null)
            entity.setItem(new ItemStack(block, stack.getCount()));
    }
}