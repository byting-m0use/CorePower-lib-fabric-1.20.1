package net.bytem0use.core_power.tester;

import net.bytem0use.core_power.api.abilities.base.CorePassive;
import net.bytem0use.core_power.api.abilities.base.PowerAPI;
import net.bytem0use.core_power.api.type.PassiveList;
import net.bytem0use.core_power.api.type.PassiveTags;
import net.bytem0use.core_power.api.type.PowersTag;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.math.random.Random;

public class ImpactDamagePassive extends CorePassive {

    public ImpactDamagePassive(StatusEffectCategory category, int color, PassiveTags tagP, PowersTag tag) {
        super(category, color, tagP, tag);
    }

    public static boolean shouldDamageCollidedEntity(int level, Random random) {
        return level > 0 && random.nextFloat() < 0.15F * level;
    }

    public static int getDamageAmount(int level, Random random) {
        return level > 10 ? level - 10 : 1 + random.nextInt(4);
    }

    public static void sprintingCollisionWithSpeedster(LivingEntity entity, ClientPlayerEntity client) {
        if(client.hasStatusEffect(CorePowerModEffects.SPEEDSTER) && client.isSprinting() && client.collidesWith(entity)) {

        }
    }

}
