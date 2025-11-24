package net.bytem0use.origins.tester;

import net.bytem0use.origins.api.OriginsAbility;
import net.bytem0use.origins.api.type.AbilityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;

public class ClimbingAbility extends OriginsAbility{
    public ClimbingAbility(String abilityID, AbilityType abilityType) {
        super(abilityID, abilityType);
    }

    public static boolean isKeyToggled() {
        if(OriginsKeybindings.climb.isPressed()) {
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean isAbilityOn(LivingEntity entity, int amplifier, SpiderEffect effect) {
        if (isKeyToggled() && entity.hasStatusEffect(effect.getEffectType())) {
            if(entity.horizontalCollision) {
                Vec3d intialVec = entity.getVelocity();
                Vec3d climbVec = new Vec3d(intialVec.x, 0.2D, intialVec.z);
                entity.setVelocity(climbVec.x * 0.92D, climbVec.y * 0.98D, climbVec.z * 0.92D);
            }
            else {
                return false;
            }
        }
        return false;
    }
}
