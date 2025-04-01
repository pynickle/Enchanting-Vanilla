package com.euphony.enc_vanilla.data.recipes;

import com.euphony.enc_vanilla.EncVanilla;
import com.euphony.enc_vanilla.common.init.EVDataComponentTypes;
import com.euphony.enc_vanilla.common.init.EVItems;
import com.euphony.enc_vanilla.common.item.BiomeCrystalMap;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class RecipeGenerator extends RecipeProvider {
    public RecipeGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        addSmeltingRecipes(recipeOutput);
        addShapedRecipes(recipeOutput);
        addBiomeCrystalRecipes(recipeOutput);
    }

    protected void addShapedRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, EVItems.SCULK_COMPASS_ITEM.get(), 1)
                .define('S', EVItems.DAMAGED_SCULK_COMPASS_ITEM.get())
                .define('I', Items.ECHO_SHARD)
                .define('A', Items.AMETHYST_SHARD)
                .pattern("IAI")
                .pattern("ISI")
                .pattern("III")
                .unlockedBy("has_item", has(EVItems.DAMAGED_SCULK_COMPASS_ITEM.get()))
                .save(recipeOutput, createKey("sculk_compass"));
    }

    protected void addSmeltingRecipes(RecipeOutput recipeOutput) {
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(Items.WET_SPONGE), RecipeCategory.MISC, Items.SPONGE, 0.15F, 600)
                .unlockedBy("has_item", has(Items.WET_SPONGE))
                .save(recipeOutput, "wet_sponge_to_sponge");
    }

    protected void addBiomeCrystalRecipes(RecipeOutput recipeOutput) {
        for(Map.Entry<List<Item>, ResourceKey<Biome>> entry : BiomeCrystalMap.getRecipeMap("HEATED").entrySet()) {
            List<Item> items = entry.getKey();
            ResourceKey<Biome> biome = entry.getValue();
            ItemStack resultItem = EVItems.HEATED_BIOME_CRYSTAL_ITEM.toStack();
            resultItem.set(EVDataComponentTypes.BIOME, biome);
            shapeless(RecipeCategory.MISC, resultItem, recipeOutput, items, "heated_biome_crystal_" + biome.location().getNamespace() + "_" + biome.location().getPath(), EVItems.HEATED_BIOME_CRYSTAL_ITEM.get());
        }
        for(Map.Entry<List<Item>, ResourceKey<Biome>> entry : BiomeCrystalMap.getRecipeMap("NORMAL").entrySet()) {
            List<Item> items = entry.getKey();
            ResourceKey<Biome> biome = entry.getValue();
            ItemStack resultItem = EVItems.BIOME_CRYSTAL_ITEM.toStack();
            resultItem.set(EVDataComponentTypes.BIOME, biome);
            shapeless(RecipeCategory.MISC, resultItem, recipeOutput, items, "biome_crystal_" + biome.location().getNamespace() + "_" + biome.location().getPath(), EVItems.BIOME_CRYSTAL_ITEM.get());
        }
        for(Map.Entry<List<Item>, ResourceKey<Biome>> entry : BiomeCrystalMap.getRecipeMap("FROZEN").entrySet()) {
            List<Item> items = entry.getKey();
            ResourceKey<Biome> biome = entry.getValue();
            ItemStack resultItem = EVItems.FROZEN_BIOME_CRYSTAL_ITEM.toStack();
            resultItem.set(EVDataComponentTypes.BIOME, biome);
            shapeless(RecipeCategory.MISC, resultItem, recipeOutput, items, "frozen_biome_crystal_" + biome.location().getNamespace() + "_" + biome.location().getPath(), EVItems.FROZEN_BIOME_CRYSTAL_ITEM.get());
        }
    }

    protected void shapeless(RecipeCategory recipeCategory, ItemStack resultItem, RecipeOutput recipeOutput, List<Item> items, String key, Item... additionalItems) {
        ShapelessRecipeBuilder shapeless = ShapelessRecipeBuilder.shapeless(recipeCategory, resultItem);
        for(Item item : additionalItems) {
            shapeless
                    .requires(item)
                    .unlockedBy("has_item", has(item));;
        }
        for(Item item : items) {
            shapeless
                    .requires(item)
                    .unlockedBy("has_item", has(item));;
        }
        shapeless.save(recipeOutput, createKey(key));
    }

    protected void shapeless(RecipeCategory recipeCategory, ItemStack resultItem, RecipeOutput recipeOutput, List<Item> items, String key) {
        ShapelessRecipeBuilder shapeless = ShapelessRecipeBuilder.shapeless(recipeCategory, resultItem);
        for(Item item : items) {
            shapeless
                    .requires(item)
                    .unlockedBy("has_item", has(item));;
        }
        shapeless.save(recipeOutput, createKey(key));
    }

    protected void shapeless(RecipeCategory recipeCategory, ItemLike resultItem, RecipeOutput recipeOutput, List<Item> items, String key) {
        shapeless(recipeCategory, resultItem, 1, recipeOutput, items, key);
    }

    protected void shapeless(RecipeCategory recipeCategory, ItemLike resultItem, int count, RecipeOutput recipeOutput, List<Item> items, String key) {
        ShapelessRecipeBuilder shapeless = ShapelessRecipeBuilder.shapeless(recipeCategory, resultItem, count);
        for(Item item : items) {
            shapeless
                    .requires(item)
                    .unlockedBy("has_item", has(item));;
        }
        shapeless.save(recipeOutput, createKey(key));
    }

    protected ResourceLocation createKey(String name) {
        return EncVanilla.prefix(name);
    }
}