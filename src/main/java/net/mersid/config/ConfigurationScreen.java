package net.mersid.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.mersid.ToggleSneak;
import net.mersid.callbacks.OnTickCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;

/**
 * An instance of the Configuration screen. Each opening will cause a memory leak, so be careful!
 */
public class ConfigurationScreen {

    private ConfigBuilder configBuilder;

    // Config cannot open on same frame as chat menu, so do it next tick (or frame, but we don't need to be fast).
    private static boolean openConfigNextTick = false;

    private ConfigurationScreen()
    {
        // Set up builders, title, and category.
        configBuilder = ConfigBuilder.create();
        ConfigEntryBuilder configEntryBuilder = ConfigEntryBuilder.create();
        ConfigCategory category = configBuilder.getOrCreateCategory("Toggle Sneak");
        configBuilder.setTitle("Configuration");

        category.addEntry(configEntryBuilder.startIntSlider(I18n.translate("Fly boost factor"), 0, 0, 10).build());
        OnTickCallback.EVENT.register(this::onTick);
    }


    public static void open()
    {
        openConfigNextTick = true;
    }

    private void onTick()
    {
        if (openConfigNextTick)
        {
            MinecraftClient.getInstance().openScreen(new ConfigurationScreen().configBuilder.build());
            openConfigNextTick = false;
        }
    }
}

