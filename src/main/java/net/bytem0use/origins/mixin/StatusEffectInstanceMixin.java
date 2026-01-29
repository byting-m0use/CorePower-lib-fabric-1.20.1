package net.bytem0use.origins.mixin;

import net.bytem0use.origins.api.PowerAPI;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({StatusEffectInstance.class})
public abstract class StatusEffectInstanceMixin {
    @Shadow
    public abstract StatusEffect getEffectType();
    @Shadow private boolean showParticles;

    /**
     * @author YourName
     * @reason Force no particles for effects extending YourCertainClass
     */
    @Overwrite
    public boolean shouldShowParticles() {
        StatusEffectInstance self = (StatusEffectInstance) (Object) this;
        if (PowerAPI.class.isAssignableFrom(self.getEffectType().getClass())) {
            return false;  // Invisible: no particles spawn
        }
        return this.showParticles;
    }
}
