package net.bytem0use.core_power.client;

import net.bytem0use.core_power.api.PowerAPI;
import net.bytem0use.core_power.tester.OriginsKeybindings;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.entity.effect.StatusEffectInstance;

public class CorePowerClient implements ClientModInitializer {
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
