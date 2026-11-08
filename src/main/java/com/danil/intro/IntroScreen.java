package com.danil.intro;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class IntroScreen extends Screen {
    private long startedAt;

    public IntroScreen() {
        super(Component.empty());
    }

    @Override
    protected void init() {
        this.startedAt = System.currentTimeMillis();
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        long elapsed = System.currentTimeMillis() - this.startedAt;

        // 10 секунд для теста
        if (elapsed > 10000) { 
            Minecraft.getInstance().setScreen(new TitleScreen());
            return;
        }

        // ТЕСТОВЫЙ ПУТЬ: Если файлы в textures/frame_0001.png, то путь будет "frame_0001"
        ResourceLocation texture = new ResourceLocation("introanim", "frame_0001");
        
        // Если файлы лежат в папке intro, то замени строчку выше на:
        // ResourceLocation texture = new ResourceLocation("introanim", "intro/frame_0001");

        TextureManager textureManager = Minecraft.getInstance().getTextureManager();
        textureManager.getTexture(texture);

        graphics.fill(0, 0, this.width, this.height, 0xFF000000);

        RenderSystem.setShaderTexture(0, texture);
        graphics.blit(texture, 0, 0, 0.0F, 0.0F, this.width, this.height, 426, 240);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
}
}
