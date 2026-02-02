package net.bytem0use.core_power.api;

import net.bytem0use.core_power.api.type.PowersTag;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class PowerAPI extends StatusEffect {
    public PowerAPI(StatusEffectCategory category, int color) {
        super(category, color);
    }

    public PowerAPI(StatusEffectCategory category, int color, PowersTag pTag) {
        super(category, color);
    }


}
