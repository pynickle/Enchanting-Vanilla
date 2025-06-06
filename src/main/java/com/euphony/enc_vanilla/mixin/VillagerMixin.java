package com.euphony.enc_vanilla.mixin;

import com.euphony.enc_vanilla.common.entity.DoubleHandedTemptGoal;
import com.euphony.enc_vanilla.config.categories.qol.QolConfig;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.block.entity.BannerPatterns;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Villager.class)
public abstract class VillagerMixin extends AbstractVillager {
    @Unique
    private DoubleHandedTemptGoal enc_vanilla$villagersAttractedGoal;

    @Unique
    private boolean enc_vanilla$villagersAttracted;

    @Unique
    private ItemStack OMINOUS_BANNER = Items.WHITE_BANNER.getDefaultInstance();

    public VillagerMixin(EntityType<? extends AbstractVillager> entityType, Level level) {
        super(entityType, level);
        HolderLookup.RegistryLookup<BannerPattern> bannerPattern = level.registryAccess().lookupOrThrow(Registries.BANNER_PATTERN);

        OMINOUS_BANNER.set(DataComponents.HIDE_ADDITIONAL_TOOLTIP, Unit.INSTANCE);
        OMINOUS_BANNER.set(DataComponents.RARITY, Rarity.UNCOMMON);
        OMINOUS_BANNER.set(DataComponents.ITEM_NAME, Component.literal("{\"translate\":\"block.minecraft.ominous_banner\"}"));
        OMINOUS_BANNER.set(DataComponents.BANNER_PATTERNS, new BannerPatternLayers.Builder()
                .add(bannerPattern.getOrThrow(BannerPatterns.RHOMBUS_MIDDLE), DyeColor.CYAN)
                .add(bannerPattern.getOrThrow(BannerPatterns.STRIPE_BOTTOM), DyeColor.LIGHT_GRAY)
                .add(bannerPattern.getOrThrow(BannerPatterns.STRIPE_CENTER), DyeColor.GRAY)
                .add(bannerPattern.getOrThrow(BannerPatterns.BORDER), DyeColor.LIGHT_GRAY)
                .add(bannerPattern.getOrThrow(BannerPatterns.STRIPE_MIDDLE), DyeColor.BLACK)
                .add(bannerPattern.getOrThrow(BannerPatterns.HALF_HORIZONTAL), DyeColor.LIGHT_GRAY)
                .add(bannerPattern.getOrThrow(BannerPatterns.CIRCLE_MIDDLE), DyeColor.LIGHT_GRAY)
                .add(bannerPattern.getOrThrow(BannerPatterns.BORDER), DyeColor.BLACK)
                .build()
        );
    }

    @Inject(
            method = "<init>(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/npc/VillagerType;)V",
            at = @At(
                    value = "RETURN"
            )
    )
    private void init(EntityType<? extends Villager> entityType, Level level, VillagerType villagerType, CallbackInfo ci) {
        this.enc_vanilla$villagersAttractedGoal = new DoubleHandedTemptGoal(this, 0.5F, Ingredient.of(QolConfig.HANDLER.instance().mainHandItem), Ingredient.of(OMINOUS_BANNER), false);
    }

    @Inject(
            method = "tick",
            at = @At(
                    value = "RETURN"
            )
    )
    private void checkVillagersAttracted(CallbackInfo ci) {
        final boolean configEnabled = QolConfig.HANDLER.instance().enableVillagerAttraction;
        if(!this.enc_vanilla$villagersAttracted && configEnabled) {
            if (!this.enc_vanilla$villagersAttractedGoal.canUse()) {
                this.enc_vanilla$villagersAttractedGoal = new DoubleHandedTemptGoal(this, 0.5F, Ingredient.of(QolConfig.HANDLER.instance().mainHandItem), Ingredient.of(OMINOUS_BANNER), false);
            }
            this.goalSelector.addGoal(0, this.enc_vanilla$villagersAttractedGoal);
            this.enc_vanilla$villagersAttracted = true;
        } else if(this.enc_vanilla$villagersAttracted && !configEnabled) {
            this.goalSelector.removeGoal(this.enc_vanilla$villagersAttractedGoal);
            this.enc_vanilla$villagersAttracted = false;
        }
    }
}
