package net.bytem0use.origins.client;

import net.bytem0use.origins.tester.OriginsKeybindings;
import net.fabricmc.api.ClientModInitializer;

public class OriginsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        OriginsKeybindings.register();
    }
}
