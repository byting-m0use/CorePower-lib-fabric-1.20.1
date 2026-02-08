package net.bytem0use.core_power.api.abilities;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.option.KeyBinding;

public class CoreKeybindsRegister {

    public static KeyBinding climb;
    public static KeyBinding web_attach;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
        });
    }

    public static void register() {

        registerKeyInputs();
    }

}
