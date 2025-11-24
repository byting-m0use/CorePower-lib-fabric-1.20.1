package net.bytem0use.origins.registration;

import net.bytem0use.origins.api.Power;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class ORegistryKeys {

    public static final RegistryKey<Registry<Power>> POWER = of("super_power");

    private static <T> RegistryKey<Registry<T>> of(String id) {
        return RegistryKey.ofRegistry(new Identifier(id));
    }
}
