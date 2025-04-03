package com.euphony.enc_vanilla.config.client;

import com.euphony.enc_vanilla.config.categories.ClientConfig;
import com.euphony.enc_vanilla.config.categories.QolConfig;
import com.euphony.enc_vanilla.config.categories.ToolsConfig;
import com.euphony.enc_vanilla.config.client.widget.CategoryButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

public class EVConfigScreen extends Screen {
    private final Screen parent;
    @Nullable
    private Screen qolConfigScreen = null;
    @Nullable
    private Screen toolsConfigScreen = null;
    @Nullable
    private Screen clientConfigScreen = null;

    public EVConfigScreen(@Nullable Screen parent) {
        super(Component.translatable("yacl3.config.enc_vanilla:config"));
        this.parent = parent;
    }

    @Override
    public void onClose() {
        assert this.minecraft != null;
        this.minecraft.setScreen(this.parent);
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        super.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);

        assert this.minecraft != null;
        context.drawCenteredString(this.minecraft.font, Component.translatable("yacl3.config.enc_vanilla:config"), this.width / 2, 10, 0xFFFFFF);
    }

    @Override
    protected void init() {
        super.init();

        int categoryButtonWidth = (this.width - 80) / 3;

        var qolButton = new CategoryButton(30, 50, categoryButtonWidth, 20,
                Component.translatable("yacl3.config.enc_vanilla:config.category.qol"), Items.IRON_PICKAXE.getDefaultInstance(), (btn) -> {
            if(this.qolConfigScreen == null) {
                this.qolConfigScreen = QolConfig.makeScreen().generateScreen(this);
            }

            this.minecraft.setScreen(this.qolConfigScreen);
        });
        var toolsButton = new CategoryButton(30 + categoryButtonWidth + 10, 50, categoryButtonWidth, 20,
                Component.translatable("yacl3.config.enc_vanilla:config.category.tools"), Items.AMETHYST_SHARD.getDefaultInstance(), (btn) -> {
            if(this.toolsConfigScreen == null) {
                this.toolsConfigScreen = ToolsConfig.HANDLER.generateGui().generateScreen(this);
            }

            this.minecraft.setScreen(this.toolsConfigScreen);
        });
        var clientButton = new CategoryButton(30 + (categoryButtonWidth + 10) * 2, 50, categoryButtonWidth, 20,
                Component.translatable("yacl3.config.enc_vanilla:config.category.client"), Items.VINE.getDefaultInstance(), (btn) -> {
            if(this.clientConfigScreen == null) {
                this.clientConfigScreen = ClientConfig.HANDLER.generateGui().generateScreen(this);
            }

            this.minecraft.setScreen(this.clientConfigScreen);
        });

        int doneButtonWidth = this.width - 300;
        var buttonWidget = Button.builder(CommonComponents.GUI_DONE, (btn) -> this.minecraft.setScreen(this.parent)).bounds(this.width / 2 - doneButtonWidth / 2, this.height - 30, doneButtonWidth, 20).build();

        this.addRenderableWidget(qolButton);
        this.addRenderableWidget(toolsButton);
        this.addRenderableWidget(clientButton);
        this.addRenderableWidget(buttonWidget);
    }
}
