package com.euphony.enchanting_vanilla.common.init;

import com.euphony.enchanting_vanilla.EnchantingVanilla;
import com.euphony.enchanting_vanilla.common.item.FrogBucketItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class EVItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(EnchantingVanilla.MODID);

    public static final DeferredItem<FrogBucketItem> FROG_BUCKET_ITEM = register("frog_bucket",
            FrogBucketItem::new);

    static <T extends Item> DeferredItem<T> register(String name, Function<Item.Properties, T> item) {
        return ITEMS.registerItem(name, item);
    }
}
