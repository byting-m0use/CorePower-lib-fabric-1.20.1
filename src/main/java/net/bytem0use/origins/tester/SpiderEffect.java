package net.bytem0use.origins.tester;

import net.bytem0use.origins.api.Power;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;

public class SpiderEffect extends Power {
    public SpiderEffect(StatusEffectCategory category, int duration, boolean ambient, boolean showParticles, boolean showIcon) {
        super(category, duration, ambient, showParticles, showIcon);
    }
}
