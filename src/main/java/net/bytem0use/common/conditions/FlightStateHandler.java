package net.bytem0use.common.conditions;

import net.bytem0use.common.utils.FlyingState;
import net.bytem0use.common.utils.PlayerFlightInterface;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;

@Environment(EnvType.CLIENT)
public abstract class FlightStateHandler implements PlayerFlightInterface{

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register((ClientTickEvents.EndTick)(client) -> {
            if(client.player != null) {
                PlayerFlightInterface flyingPlayer = (PlayerFlightInterface)client.player;

                PlayerEntity player = client.player;
                PlayerAbilities abilities = client.player.getAbilities();

                FlyingState state = client.player.getAbilities().allowFlying == abilities.flying ? FlyingState.FLYING : FlyingState.GROUND;
            }

        });


    }
}
