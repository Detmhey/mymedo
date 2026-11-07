package com.danil.intro;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class IntroScreen extends Screen {

    private static final int FRAME_COUNT = 2231;
    private static final long FRAME_TIME_MS = 33L;
    private static final int FRAME_WIDTH = 1920;
    private static final int FRAME_HEIGHT = 1080;

    private long startedAt;
    private ResourceLocation loadedFrame;

    public IntroScreen() {
        super(Component.empty());
    }

    @Override
    protected void init() {
        startedAt = System.currentTimeMillis();
        IntroAnimMod.playSound();
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        long elapsed = System.currentTimeMillis() - startedAt;
        int frame = (int) (elapsed / FRAME_TIME_MS) + 1;

        // Если заставка закончилась — переходим в меню
        if (frame > FRAME_COUNT) {
            releaseCurrentTexture();
            Minecraft.getInstance().setScreen(new net.minecraft.client.gui.screens.TitleScreen());
            return;
        }

        // Убрали .png в конце!
        ResourceLocation texture = new ResourceLocation(
                IntroAnimMod.MODID,
                "textures/intro/frame_" + String.format("%04d", frame)
        );

        TextureManager textureManager = Minecraft.getInstance().getTextureManager();
        
        // Загружаем только текущий кадр
        if (!texture.equals(loadedFrame)) {
            textureManager.getTexture(texture);
            
            // Освобождаем память от предыдущего кадра
            if (loadedFrame != null) {
                textureManager.release(loadedFrame);
            }
            loadedFrame = texture;
        }

        graphics.fill(0, 0, this.width, this.height, 0xFF000000);

        // Сохраняем пропорции 16:9
        int drawW = this.width;
        int drawH = drawW * FRAME_HEIGHT / FRAME_WIDTH;

        if (drawH > this.height) {
            drawH = this.height;
            drawW = drawH * FRAME_WIDTH / FRAME_HEIGHT;
        }

        int x = (this.width - drawW) / 2;
        int y = (this.height - drawH) / 2;

        // Отрисовка
        graphics.blit(
                texture,
                x, y,
                drawW, drawH,
                0, 0,
                FRAME_WIDTH, FRAME_HEIGHT,
                FRAME_WIDTH, FRAME_HEIGHT
        );
    }

    // ВОТ ЭТОТ МЕТОД МЫ СЛУЧАЙНО УДАЛИЛИ, ВОЗВРАЩАЕМ ЕГО:
    private void releaseCurrentTexture() {
        if (loadedFrame != null) {
            Minecraft.getInstance().getTextureManager().release(loadedFrame);
            loadedFrame = null;
        }
    }

    @Override
    public void removed() {
        releaseCurrentTexture();
        super.removed();
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
}
