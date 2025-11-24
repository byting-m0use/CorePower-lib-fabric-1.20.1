package net.bytem0use.origins.registration;

import net.bytem0use.origins.api.Power;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class Powers {

    public static final Power SUPER_STRENGTH = register(117, "super_strength",
            new Power(StatusEffectCategory.BENEFICIAL, Power.INFINITE, false, true, false)
                    .addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, "c559233c-8810-4432-b5b3-f64078a5c753", 1.5f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL)
                    .addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED, "d6c0f793-b722-44a1-8726-0f4614ad596f", 1.3f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL)
                    .addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "71d1cac2-62fd-455c-9bfd-5b92d0960c6e", .25f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL)
                    .addAttributeModifier(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, "2b8155bd-c9b2-4064-adf8-26e1552ad495", .60f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));

    private static Power register(int rawId, String id, Power entry) {
        return Registry.register(ORegistries.POWER, rawId, id, entry);
    }
}
