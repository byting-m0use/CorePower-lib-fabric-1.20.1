package net.bytem0use.origins.tester;

import net.bytem0use.origins.Origins;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEffects {

    private static StatusEffect registerPower(String name, StatusEffect power) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(Origins.MOD_ID, name), power);
    }

    public static void registerEffects() {
        Origins.LOGGER.info("Registering Mod Effects for " + Origins.MOD_ID);
    }

}
