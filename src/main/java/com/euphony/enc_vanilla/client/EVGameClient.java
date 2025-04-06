package com.euphony.enc_vanilla.client;

import com.euphony.enc_vanilla.EncVanilla;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RecipesUpdatedEvent;

import java.util.Collection;
import java.util.stream.Collectors;

@EventBusSubscriber(modid = EncVanilla.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class EVGameClient {
    @SubscribeEvent
    public static void recipe(RecipesUpdatedEvent event) {
        RecipeManager recipeManager = event.getRecipeManager();
        Collection<RecipeHolder<?>> recipeHolders = recipeManager.getRecipes();
        Collection<RecipeHolder<?>> recipesToKeep = recipeHolders.stream()
                .filter((holder) -> {
                    ResourceLocation id = holder.id();
                    return !id.equals(ResourceLocation.withDefaultNamespace("lodestone"));
                }).collect(Collectors.toList());
        recipeManager.replaceRecipes(recipesToKeep);
    }
}
