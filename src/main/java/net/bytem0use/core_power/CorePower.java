package net.bytem0use.core_power;

import net.bytem0use.core_power.attributes.CorePowerAttributes;
import net.bytem0use.core_power.tester.ModEffects;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib.GeckoLib;

public class CorePower implements ModInitializer {
	public static final String MOD_ID = "origins";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        GeckoLib.initialize();

        CorePowerAttributes.registerAttributes();

        ModEffects.registerEffects();

		LOGGER.info("Hello Fabric world!");
	}
}