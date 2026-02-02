package net.bytem0use.core_power.tester;

import net.bytem0use.core_power.api.PowerAPI;
import net.bytem0use.core_power.api.type.PowersTag;
import net.minecraft.entity.effect.StatusEffectCategory;

public class ArachnidEffect extends PowerAPI {
    public ArachnidEffect(StatusEffectCategory category, int color, PowersTag pTag) {
        super(category, color, pTag);
    }
}
