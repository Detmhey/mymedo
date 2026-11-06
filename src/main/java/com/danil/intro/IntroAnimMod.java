package com.danil.intro;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod(IntroAnimMod.MODID)
@Mod.EventBusSubscriber(modid = IntroAnimMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class IntroAnimMod {
    public static final String MODID = "introanim";
    private static boolean played = false;

    public static final DeferredRegister<SoundEvent> SOUNDS =
            DeferredRegister.create(Registries.SOUND_EVENT, MODID);

    public static final RegistryObject<SoundEvent> INTRO_SOUND = SOUNDS.register(
            "intro",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "intro"))
    );

    public IntroAnimMod() {
        SOUNDS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

@SubscribeEvent
public static void onScreenOpening(ScreenEvent.Opening event) {
    if (!played && event.getNewScreen() instanceof TitleScreen) {
        played = true;
        event.setNewScreen(new IntroScreen());
    }
}

public static void playSound() {
    Minecraft.getInstance().getSoundManager().play(
        SimpleSoundInstance.forUI(
            INTRO_SOUND.get(),
            1.0F
        )
    );
}
}
