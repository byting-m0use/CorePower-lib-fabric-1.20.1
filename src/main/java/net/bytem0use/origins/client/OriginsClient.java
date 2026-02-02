package net.bytem0use.origins.client;

import net.bytem0use.origins.api.PowerAPI;
import net.bytem0use.origins.tester.OriginsKeybindings;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.entity.effect.StatusEffectInstance;

public class OriginsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        OriginsKeybindings.register();

        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
            if (!alive) {
                for (StatusEffectInstance effect : oldPlayer.getStatusEffects()) {
                    if (effect.getEffectType() instanceof PowerAPI) {
                        newPlayer.addStatusEffect(new StatusEffectInstance(effect));
                    }
                }
            }
        });
    }
}
