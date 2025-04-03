package com.euphony.enc_vanilla.data.loots;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.common.init.EVItems;
import com.euphony.enc_vanilla.common.loot.AddItemModifier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.concurrent.CompletableFuture;

public class GlobalLootModifierGenerator extends GlobalLootModifierProvider {
    public GlobalLootModifierGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, EncVanilla.MODID);
    }

    @Override
    protected void start() {
        this.add("damaged_sculk_compass",
                new AddItemModifier(new LootItemCondition[] {
                        new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("chests/ancient_city")).build(),
                        LootItemRandomChanceCondition.randomChance(0.1f).build()
                }, EVItems.DAMAGED_SCULK_COMPASS_ITEM.get()));
        this.add("biome_crystal",
                new AddItemModifier(new LootItemCondition[] {
                        new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("archaeology/trail_ruins_rare")).build(),
                        LootItemRandomChanceCondition.randomChance(0.2f).build()
                }, EVItems.BIOME_CRYSTAL_ITEM.get()));
        this.add("heated_biome_crystal",
                new AddItemModifier(new LootItemCondition[] {
                        new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("archaeology/ocean_ruin_warm"))
                                .or(new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("archaeology/desert_pyramid")))
                                .or(new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("archaeology/desert_well"))).build(),
                        LootItemRandomChanceCondition.randomChance(0.2f).build()
                }, EVItems.HEATED_BIOME_CRYSTAL_ITEM.get()));
        this.add("frozen_biome_crystal",
                new AddItemModifier(new LootItemCondition[] {
                        new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("archaeology/ocean_ruin_cold")).build(),
                        LootItemRandomChanceCondition.randomChance(0.2f).build()
                }, EVItems.FROZEN_BIOME_CRYSTAL_ITEM.get()));
    }
}