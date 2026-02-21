package net.bytem0use.core_power.tester;

import com.google.common.collect.Maps;
import net.bytem0use.core_power.CorePower;
import net.bytem0use.core_power.api.type.PassiveList;
import net.bytem0use.core_power.api.type.PowersTag;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Map;

public class CorePowerModEffects {
    private final Map<StatusEffect, StatusEffectInstance> activePowers = Maps.<StatusEffect, StatusEffectInstance>newHashMap();

    private static StatusEffect registerPower(String name, StatusEffect power) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(CorePower.MOD_ID, name), power);
    }

    public static final StatusEffect SPEEDSTER = registerPower("speedster",
            new SpeedsterEffect(StatusEffectCategory.BENEFICIAL, 55550, PowersTag.ENHANCER, PassiveList.IMPACT_DAMAGE));

    public static void registerEffects() {
        CorePower.LOGGER.info("Registering Mod Effects for " + CorePower.MOD_ID);
    }

}
