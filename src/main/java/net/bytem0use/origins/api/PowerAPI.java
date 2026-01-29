package net.bytem0use.origins.api;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class PowerAPI extends StatusEffect {
    protected PowerAPI(StatusEffectCategory category, int color) {
        super(category, color);
    }
}
