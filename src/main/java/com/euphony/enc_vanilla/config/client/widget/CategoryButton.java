package com.euphony.enc_vanilla.config.client.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CategoryButton extends Button {
    private final ItemStack icon;
    private final Component text;

    public CategoryButton(int x, int y, int w, int h, Component text, ItemStack icon, OnPress onClick) {
        super(new Button.Builder(Component.literal(""), onClick).pos(x, y).size(w, h));
        this.icon = icon;
        this.text = text;
    }

    @Override
    public void renderWidget(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.renderWidget(guiGraphics, mouseX, mouseY, partialTicks);

        Minecraft mc = Minecraft.getInstance();
        guiGraphics.renderFakeItem(icon, getX() + 5, getY() + 2);

        int iconPad = (16 + 5) / 2;
        guiGraphics.drawCenteredString(mc.font, text, getX() + width / 2 + iconPad, getY() + (height - 8) / 2, getFGColor());
    }
}
