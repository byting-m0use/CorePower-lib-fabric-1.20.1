package net.bytem0use.origins.tester;

import net.bytem0use.origins.Origins;
import net.bytem0use.origins.api.Power;
import net.bytem0use.origins.registration.ORegistries;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEffects {

    public static final Power SPIDER_EFFECT = registerPower("spider", new SpiderEffect(
            StatusEffectCategory.BENEFICIAL, Power.INFINITE, false, false, true));

    private static Power registerPower(String name, Power power) {
        return Registry.register(ORegistries.POWER, new Identifier(Origins.MOD_ID, name), power);
    }

    public static void registerEffects() {
        Origins.LOGGER.info("Registering Mod Effects for " + Origins.MOD_ID);
    }

}
