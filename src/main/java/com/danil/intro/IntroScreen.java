package com.danil.intro;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class IntroScreen extends Screen {
    private long startedAt;
    private static final long INTRO_DURATION_MS = 74000; // 74 секунды

    public IntroScreen() {
        super(Component.empty());
    }

    @Override
    protected void init() {
        this.startedAt = System.currentTimeMillis();
        IntroAnimMod.playSound(); // Запускаем звук
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        long elapsed = System.currentTimeMillis() - this.startedAt;

        if (elapsed > INTRO_DURATION_MS) {
            Minecraft.getInstance().setScreen(new TitleScreen());
            return;
        }

        // Загружаем ОДНУ картинку
        ResourceLocation texture = new ResourceLocation(IntroAnimMod.MODID, "textures/intro/frame_0001");
        
        TextureManager textureManager = Minecraft.getInstance().getTextureManager();
        textureManager.getTexture(texture);

        graphics.fill(0, 0, this.width, this.height, 0xFF000000);

        int drawW = this.width;
        int drawH = this.height;

        graphics.blit(texture, 0, 0, 0.0F, 0.0F, drawW, drawH, 1920, 1080);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
}
