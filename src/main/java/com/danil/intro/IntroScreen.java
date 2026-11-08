package com.danil.intro;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;

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
        this.startedAt = System.currentTimeMillis();
        Minecraft mc = Minecraft.getInstance();
        
        // Останавливаем музыку меню
        mc.getMusicManager().stopPlaying();
        mc.getSoundManager().stop(null, SoundSource.MUSIC);
        
        // Запускаем нашу музыку
        IntroAnimMod.playSound();
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        long elapsed = System.currentTimeMillis() - this.startedAt;
        int frame = (int)(elapsed / FRAME_TIME_MS) + 1;

        if (frame > FRAME_COUNT) {
            this.releaseCurrentTexture();
            Minecraft.getInstance().setScreen(new TitleScreen());
            return;
        }

        // ВОТ ОН, ПРАВИЛЬНЫЙ ПУТЬ! (Без textures/intro/frame_)
        ResourceLocation texture = new ResourceLocation(IntroAnimMod.MODID, String.format("%04d", frame));
        
        TextureManager textureManager = Minecraft.getInstance().getTextureManager();
        if (!texture.equals(this.loadedFrame)) {
            textureManager.getTexture(texture);
            if (this.loadedFrame != null) {
                textureManager.release(this.loadedFrame);
            }
            this.loadedFrame = texture;
        }

        graphics.fill(0, 0, this.width, this.height, 0xFF000000);

        // Правильные пропорции
        int drawW = this.width;
        int drawH = drawW * FRAME_HEIGHT / FRAME_WIDTH;
        if (drawH > this.height) {
            drawH = this.height;
            drawW = drawH * FRAME_WIDTH / FRAME_HEIGHT;
        }

        int x = (this.width - drawW) / 2;
        int y = (this.height - drawH) / 2;

        // ВОТ ПРАВИЛЬНЫЙ МЕТОД BLIT (с float координатами)
        graphics.blit(texture, x, y, 0.0F, 0.0F, drawW, drawH, FRAME_WIDTH, FRAME_HEIGHT);
    }

    private void releaseCurrentTexture() {
        if (this.loadedFrame != null) {
            Minecraft.getInstance().getTextureManager().release(this.loadedFrame);
            this.loadedFrame = null;
        }
    }

    @Override
    public void removed() {
        this.releaseCurrentTexture();
        super.removed();
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
}
