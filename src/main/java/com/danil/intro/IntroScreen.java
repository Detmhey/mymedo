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
    private static final int FRAME_COUNT = 2231;
    private static final long FRAME_TIME_MS = 33L;
    private long startedAt;
    private boolean soundStarted;

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
        int frame = (int) (elapsed / FRAME_TIME_MS) + 1;

        if (!soundStarted) {
            soundStarted = true;
            IntroAnimMod.playSound(); // Запускает твою музыку и глушит меню
        }

        if (frame > FRAME_COUNT) {
            Minecraft.getInstance().setScreen(new TitleScreen());
            return;
        }

        String number = String.format("%04d", frame);
        ResourceLocation texture = new ResourceLocation("introanim", "frame_" + number);

        TextureManager textureManager = Minecraft.getInstance().getTextureManager();
        textureManager.getTexture(texture);

        graphics.fill(0, 0, this.width, this.height, 0xFF000000);

        int drawW = this.width;
        int drawH = drawW * 240 / 426;
        if (drawH > this.height) {
            drawH = this.height;
            drawW = drawH * 426 / 240;
        }
        int x = (this.width - drawW) / 2;
        int y = (this.height - drawH) / 2;

        RenderSystem.setShaderTexture(0, texture);
        graphics.blit(texture, x, y, 0.0F, 0.0F, drawW, drawH, 426, 240);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
}
