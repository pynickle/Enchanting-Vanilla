package com.euphony.enc_vanilla.common.item;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BiomeCrystalMap {
    private static final Map<String, Map<List<Item>, ResourceKey<Biome>>> biomeCrystalMap = new HashMap<>();

    static {
        Map<List<Item>, ResourceKey<Biome>> heatedMap = new HashMap<>();
        heatedMap.put(List.of(Items.TERRACOTTA), Biomes.BADLANDS);
        heatedMap.put(List.of(Items.COARSE_DIRT, Items.TERRACOTTA), Biomes.WOODED_BADLANDS);
        heatedMap.put(List.of(Items.OAK_LOG), Biomes.SAVANNA);
        heatedMap.put(List.of(Items.COARSE_DIRT, Items.OAK_LOG), Biomes.WINDSWEPT_SAVANNA);
        heatedMap.put(List.of(Items.SAND), Biomes.DESERT);
        biomeCrystalMap.put("HEATED", heatedMap);

        Map<List<Item>, ResourceKey<Biome>> normalMap = new HashMap<>();
        normalMap.put(List.of(Items.STONE), Biomes.STONY_PEAKS);
        normalMap.put(List.of(Items.MELON_SLICE, Items.FEATHER), Biomes.JUNGLE);
        normalMap.put(List.of(Items.MELON_SLICE), Biomes.SPARSE_JUNGLE);
        normalMap.put(List.of(Items.MELON_SLICE, Items.PODZOL), Biomes.BAMBOO_JUNGLE);
        normalMap.put(List.of(Items.BROWN_MUSHROOM), Biomes.MUSHROOM_FIELDS);
        normalMap.put(List.of(Items.GRASS_BLOCK), Biomes.PLAINS);
        normalMap.put(List.of(Items.WATER_BUCKET, Items.SAND), Biomes.BEACH);
        normalMap.put(List.of(Items.GRASS_BLOCK, Items.YELLOW_DYE), Biomes.SUNFLOWER_PLAINS);
        normalMap.put(List.of(Items.DEEPSLATE), Biomes.DEEP_DARK);
        normalMap.put(List.of(Items.WATER_BUCKET, Items.COPPER_INGOT), Biomes.DRIPSTONE_CAVES);
        normalMap.put(List.of(Items.MUD), Biomes.MANGROVE_SWAMP);
        normalMap.put(List.of(Items.WATER_BUCKET, Items.OAK_LOG), Biomes.SWAMP);
        normalMap.put(List.of(Items.OAK_LOG, Items.BIRCH_LOG), Biomes.FOREST);
        normalMap.put(List.of(Items.DANDELION), Biomes.FLOWER_FOREST);
        normalMap.put(List.of(Items.OAK_LOG, Items.BROWN_MUSHROOM), Biomes.DARK_FOREST);
        // normalMap.put(List.of(Items.GRAY_DYE), Biomes.PALE_GARDEN);
        normalMap.put(List.of(Items.BIRCH_LOG), Biomes.BIRCH_FOREST);
        normalMap.put(List.of(Items.BIRCH_LOG, Items.BIRCH_LOG), Biomes.OLD_GROWTH_BIRCH_FOREST);
        normalMap.put(List.of(Items.WATER_BUCKET, Items.BLUE_DYE), Biomes.WARM_OCEAN);
        normalMap.put(List.of(Items.WATER_BUCKET, Items.MAGMA_BLOCK), Biomes.DEEP_LUKEWARM_OCEAN);
        normalMap.put(List.of(Items.WATER_BUCKET, Items.TROPICAL_FISH), Biomes.LUKEWARM_OCEAN);
        normalMap.put(List.of(Items.WATER_BUCKET), Biomes.OCEAN);
        normalMap.put(List.of(Items.WATER_BUCKET, Items.PRISMARINE), Biomes.DEEP_OCEAN);
        normalMap.put(List.of(Items.WATER_BUCKET, Items.GRAVEL), Biomes.COLD_OCEAN);
        normalMap.put(List.of(Items.WATER_BUCKET, Items.PRISMARINE), Biomes.DEEP_COLD_OCEAN);
        normalMap.put(List.of(Items.WATER_BUCKET, Items.CLAY), Biomes.RIVER);
        normalMap.put(List.of(Items.WATER_BUCKET, Items.ICE), Biomes.DEEP_FROZEN_OCEAN);
        normalMap.put(List.of(Items.WATER_BUCKET, Items.VINE), Biomes.LUSH_CAVES);
        normalMap.put(List.of(Items.SHORT_GRASS, Items.BEE_NEST), Biomes.MEADOW);
        normalMap.put(List.of(Items.GRASS_BLOCK, Items.PINK_DYE), Biomes.CHERRY_GROVE);
        normalMap.put(List.of(Items.SPRUCE_LOG), Biomes.TAIGA);
        normalMap.put(List.of(Items.SPRUCE_LOG, Items.MOSSY_COBBLESTONE), Biomes.OLD_GROWTH_SPRUCE_TAIGA);
        normalMap.put(List.of(Items.GRAVEL), Biomes.WINDSWEPT_GRAVELLY_HILLS);
        normalMap.put(List.of(Items.OAK_LOG, Items.SPRUCE_LOG), Biomes.WINDSWEPT_FOREST);
        normalMap.put(List.of(Items.GRASS_BLOCK, Items.STONE), Biomes.WINDSWEPT_HILLS);
        normalMap.put(List.of(Items.WATER_BUCKET, Items.STONE), Biomes.STONY_SHORE);
        normalMap.put(List.of(Items.SAND, Items.SNOW), Biomes.SNOWY_BEACH);
        biomeCrystalMap.put("NORMAL", normalMap);

        Map<List<Item>, ResourceKey<Biome>> frozenMap = new HashMap<>();
        frozenMap.put(List.of(Items.WATER_BUCKET), Biomes.FROZEN_RIVER);
        frozenMap.put(List.of(Items.WATER_BUCKET, Items.KELP), Biomes.FROZEN_OCEAN);
        frozenMap.put(List.of(Items.SNOW, Items.GRASS_BLOCK), Biomes.SNOWY_PLAINS);
        frozenMap.put(List.of(Items.PACKED_ICE), Biomes.ICE_SPIKES);
        frozenMap.put(List.of(Items.SPRUCE_LOG), Biomes.GROVE);
        frozenMap.put(List.of(Items.SNOW), Biomes.SNOWY_SLOPES);
        frozenMap.put(List.of(Items.SPRUCE_LOG, Items.SHORT_GRASS), Biomes.SNOWY_TAIGA);
        frozenMap.put(List.of(Items.PACKED_ICE, Items.STONE), Biomes.FROZEN_PEAKS);
        frozenMap.put(List.of(Items.STONE), Biomes.JAGGED_PEAKS);
        biomeCrystalMap.put("FROZEN", frozenMap);
    }
    public static Map<List<Item>, ResourceKey<Biome>> getRecipeMap(String type){
        return biomeCrystalMap.get(type);
    }
}
