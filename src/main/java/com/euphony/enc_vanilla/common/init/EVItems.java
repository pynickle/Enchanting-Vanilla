package com.euphony.enc_vanilla.common.init;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.common.item.FrogBucketItem;
import com.euphony.enc_vanilla.common.item.SculkCompassItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

public class EVItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(EncVanilla.MODID);

    public static final DeferredItem<FrogBucketItem> FROG_BUCKET_ITEM = register("frog_bucket",
            FrogBucketItem::new);

    public static final DeferredItem<SculkCompassItem> SCULK_COMPASS_ITEM = register("sculk_compass",
            SculkCompassItem::new);

    static <T extends Item> DeferredItem<T> register(String name, Function<Item.Properties, T> item) {
        return ITEMS.registerItem(name, item);
    }

    static DeferredItem<BlockItem> register(String name, Supplier<? extends Block> block) {
        return ITEMS.registerSimpleBlockItem(name, block, new Item.Properties());
    }
}
