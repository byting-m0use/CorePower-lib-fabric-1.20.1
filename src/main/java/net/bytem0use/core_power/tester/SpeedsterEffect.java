package net.bytem0use.core_power.tester;

import net.bytem0use.core_power.api.abilities.base.PowerAPI;
import net.bytem0use.core_power.api.type.PassiveList;
import net.bytem0use.core_power.api.type.PowersTag;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;

public class SpeedsterEffect extends PowerAPI {
    private int amplifier;
    private int duration;

    public SpeedsterEffect(StatusEffectCategory category, int color, PowersTag tag, PassiveList passive) {
        super(category, color, tag, passive);
    }
}
