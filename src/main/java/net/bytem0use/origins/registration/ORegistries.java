package net.bytem0use.origins.registration;

import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Lifecycle;
import net.bytem0use.origins.api.Power;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;

import java.util.Map;
import java.util.function.Supplier;

public class ORegistries {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Map<Identifier, Supplier<?>> DEFAULT_ENTRIES = Maps.<Identifier, Supplier<?>>newLinkedHashMap();
    public static final Identifier ROOT_KEY = new Identifier("root");
    private static final MutableRegistry<MutableRegistry<?>> ROOT = new SimpleRegistry<>(RegistryKey.ofRegistry(ROOT_KEY), Lifecycle.stable());
    public static final Registry<? extends Registry<?>> REGISTRIES = ROOT;

    public static final Registry<Power> POWER = create(ORegistryKeys.POWER, registry -> Powers.SUPER_STRENGTH);

    private static <T> Registry<T> create(RegistryKey<? extends Registry<T>> key, ORegistries.Initializer<T> initializer) {
        return create(key, Lifecycle.stable(), initializer);
    }

    private static <T> DefaultedRegistry<T> create(RegistryKey<? extends Registry<T>> key, String defaultId, ORegistries.Initializer<T> initializer) {
        return create(key, defaultId, Lifecycle.stable(), initializer);
    }

    private static <T> DefaultedRegistry<T> createIntrusive(RegistryKey<? extends Registry<T>> key, String defaultId, ORegistries.Initializer<T> initializer) {
        return createIntrusive(key, defaultId, Lifecycle.stable(), initializer);
    }

    private static <T> Registry<T> create(RegistryKey<? extends Registry<T>> key, Lifecycle lifecycle, ORegistries.Initializer<T> initializer) {
        return create(key, new SimpleRegistry<>(key, lifecycle, false), initializer, lifecycle);
    }

    private static <T> DefaultedRegistry<T> create(
            RegistryKey<? extends Registry<T>> key, String defaultId, Lifecycle lifecycle, ORegistries.Initializer<T> initializer
    ) {
        return create(key, new SimpleDefaultedRegistry<>(defaultId, key, lifecycle, false), initializer, lifecycle);
    }

    private static <T> DefaultedRegistry<T> createIntrusive(
            RegistryKey<? extends Registry<T>> key, String defaultId, Lifecycle lifecycle, ORegistries.Initializer<T> initializer
    ) {
        return create(key, new SimpleDefaultedRegistry<>(defaultId, key, lifecycle, true), initializer, lifecycle);
    }

    private static <T, R extends MutableRegistry<T>> R create(
            RegistryKey<? extends Registry<T>> key, R registry, ORegistries.Initializer<T> initializer, Lifecycle lifecycle
    ) {
        Identifier identifier = key.getValue();
        DEFAULT_ENTRIES.put(identifier, (Supplier)() -> initializer.run(registry));
        return registry;
    }

    public static void bootstrap() {
        init();
        freezeRegistries();
        validate(REGISTRIES);
    }

    private static void init() {
        DEFAULT_ENTRIES.forEach((id, initializer) -> {
            if (initializer.get() == null) {
                LOGGER.error("Unable to bootstrap registry '{}'", id);
            }
        });
    }

    private static void freezeRegistries() {
        REGISTRIES.freeze();

        for (Registry<?> registry : REGISTRIES) {
            registry.freeze();
        }
    }

    private static <T extends Registry<?>> void validate(Registry<T> registries) {
        registries.forEach(registry -> {
            if (registry.getIds().isEmpty()) {
                Util.error("Registry '" + registries.getId((T)registry) + "' was empty after loading");
            }

            if (registry instanceof DefaultedRegistry) {
                Identifier identifier = ((DefaultedRegistry)registry).getDefaultId();
                Validate.notNull(registry.get(identifier), "Missing default of DefaultedMappedRegistry: " + identifier);
            }
        });
    }


    @FunctionalInterface
    interface Initializer<T> {
        T run(Registry<T> registry);
    }
}
