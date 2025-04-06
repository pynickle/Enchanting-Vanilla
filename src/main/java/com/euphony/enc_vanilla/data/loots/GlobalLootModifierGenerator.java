package com.euphony.enc_vanilla.data.loots;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.common.condition.BoolConfigCondition;
import com.euphony.enc_vanilla.common.init.EVItems;
import com.euphony.enc_vanilla.common.loot.AddItemModifier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
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
                        new LootTableIdCondition.Builder(BuiltInLootTables.ANCIENT_CITY.location()).build(),
                        LootItemRandomChanceCondition.randomChance(0.05f).build()
                }, EVItems.DAMAGED_SCULK_COMPASS_ITEM.get()));
        this.add("biome_crystal",
                new AddItemModifier(new LootItemCondition[] {
                        new LootTableIdCondition.Builder(BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_RARE.location()).build(),
                        LootItemRandomChanceCondition.randomChance(0.15f).build()
                }, EVItems.BIOME_CRYSTAL_ITEM.get()));
        this.add("heated_biome_crystal",
                new AddItemModifier(new LootItemCondition[] {
                        new LootTableIdCondition.Builder(BuiltInLootTables.OCEAN_RUIN_WARM_ARCHAEOLOGY.location())
                                .or(new LootTableIdCondition.Builder(BuiltInLootTables.DESERT_PYRAMID.location()))
                                .or(new LootTableIdCondition.Builder(BuiltInLootTables.DESERT_WELL_ARCHAEOLOGY.location())).build(),
                        LootItemRandomChanceCondition.randomChance(0.15f).build()
                }, EVItems.HEATED_BIOME_CRYSTAL_ITEM.get()));
        this.add("frozen_biome_crystal",
                new AddItemModifier(new LootItemCondition[] {
                        new LootTableIdCondition.Builder(BuiltInLootTables.OCEAN_RUIN_COLD_ARCHAEOLOGY.location()).build(),
                        LootItemRandomChanceCondition.randomChance(0.15f).build()
                }, EVItems.FROZEN_BIOME_CRYSTAL_ITEM.get()));
        this.add("lodestone",
                new AddItemModifier(new LootItemCondition[] {
                        new LootTableIdCondition.Builder(BuiltInLootTables.RUINED_PORTAL.location()).build(),
                        LootItemRandomChanceCondition.randomChance(0.005f).build()
                }, Items.LODESTONE, 1, 2), new BoolConfigCondition("betterLodestone"));
    }
}