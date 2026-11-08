\package com.danil.intro;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class IntroScreen extends Screen {
    private long startedAt;
    // Общая длительность: 7 гифок по 10 сек + последняя 4 сек = 74 секунды (74000 мс)
    private static final long INTRO_DURATION_MS = 74000; 
    // Одна гифка длится 10 секунд
    private static final long SEGMENT_DURATION_MS = 10000; 

    public IntroScreen() {
        super(Component.empty());
    }

    @Override
    protected void init() {
        startedAt = System.currentTimeMillis();
        IntroAnimMod.playSound(); // Твоя музыка
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        long elapsed = System.currentTimeMillis() - startedAt;

        // Если время вышло — перекидываем в меню
        if (elapsed > INTRO_DURATION_MS) {
            Minecraft.getInstance().setScreen(new net.minecraft.client.gui.screens.TitleScreen());
            return;
        }

        graphics.fill(0, 0, this.width, this.height, 0xFF000000);

        // Вычисляем, какую по счету гифку показывать (1, 2, 3... 8)
        int currentPart = (int) (elapsed / SEGMENT_DURATION_MS) + 1;
        
        // Если время перевалило за 70 секунд, показываем последнюю (8-ю) гифку
        if (currentPart > 8) {
            currentPart = 8;
        }

        // Путь к гифке (без .gif в конце!)
        ResourceLocation gifTexture = new ResourceLocation(
                IntroAnimMod.MODID, 
                "textures/intro/intro" + currentPart
        );
        
        // Рисуем на весь экран
        int drawW = this.width;
        int drawH = this.height;
        int x = 0;
        int y = 0;

        graphics.blit(gifTexture, x, y, 0, 0, drawW, drawH, 1920, 1080);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
}
