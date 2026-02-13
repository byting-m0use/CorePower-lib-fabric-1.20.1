package net.bytem0use.core_power.core;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

public class CorePowerDamageSources extends DamageSource {

    public static final RegistryKey<DamageType> LASER_DAMAGE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE,
            new Identifier("core_power", "laser"));

    public static final RegistryKey<DamageType> HEAT_VISION_DAMAGE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE,
            new Identifier("core_power", "heat_vision"));

    public static final RegistryKey<DamageType> BLEEDING_DAMAGE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE,
            new Identifier("core_power", "bleeding"));


    public CorePowerDamageSources(RegistryEntry<DamageType> type, @Nullable Entity source, @Nullable Entity attacker) {
        super(type, source, attacker);
    }

    public CorePowerDamageSources(RegistryEntry<DamageType> type, Vec3d position) {
        super(type, position);
    }

    public CorePowerDamageSources(RegistryEntry<DamageType> type, @Nullable Entity attacker) {
        super(type, attacker);
    }

    public CorePowerDamageSources(RegistryEntry<DamageType> type) {
        super(type);
    }
}
