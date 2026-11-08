package com.danil.intro;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class IntroScreen extends Screen {
    private long startedAt;
    private static final long INTRO_DURATION_MS = 74000; 
    private static final long SEGMENT_DURATION_MS = 10000; 

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

        if (elapsed > INTRO_DURATION_MS) {
            Minecraft.getInstance().setScreen(new net.minecraft.client.gui.screens.TitleScreen());
            return;
        }

        graphics.fill(0, 0, this.width, this.height, 0xFF000000);

        int currentPart = (int) (elapsed / SEGMENT_DURATION_MS) + 1;
        
        if (currentPart > 8) {
            currentPart = 8;
        }

        ResourceLocation gifTexture = new ResourceLocation(
                IntroAnimMod.MODID, 
                "textures/intro/intro" + currentPart
        );
        
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
