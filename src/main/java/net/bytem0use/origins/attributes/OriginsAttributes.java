package net.bytem0use.origins.attributes;

import net.bytem0use.origins.Origins;
import net.bytem0use.origins.api.type.AbilityType;
import net.bytem0use.origins.api.type.PowerCategory;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class OriginsAttributes extends EntityAttribute {

    protected OriginsAttributes(String translationKey, double fallback, PowerCategory pCategory, AbilityType pType) {
        super(translationKey, fallback);
    }

    public static void registerAttribute(String name, OriginsAttributes attribute) {
        Registry.register(Registries.ATTRIBUTE, Identifier.of("origins", name), attribute);
    }

    public static void registerAttributes() {
        Origins.LOGGER.info("Registering Attributes for " + Origins.MOD_ID);
    }
}
