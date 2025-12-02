package net.bytem0use.origins.client;

import net.bytem0use.origins.Origins;
import net.bytem0use.origins.api.OriginsAbility;
import net.bytem0use.origins.api.type.AbilityType;
import net.minecraft.util.Identifier;

import java.util.*;

public class AbilityRegistry {
    public static final Map<String, OriginsAbility> ABILITY_MAP = new HashMap<>();
    public static final ArrayList<OriginsAbility> abilities = new ArrayList<>();
    public static final HashSet<Identifier> identifiers = new HashSet<>();
    public static final ArrayList<OriginsAbility> baseAbilities = new ArrayList<>();

    public static OriginsAbility getAbility(String identifier) {
        OriginsAbility tAbility = ABILITY_MAP.get(identifier);
        if (tAbility == null) {
            for(OriginsAbility ability : baseAbilities) {
                if (ability.getAbilityID().equals(identifier)) {
                    tAbility = ability;
                    break;
                }
            }
        }

        if (tAbility == null) {
            Origins.LOGGER.error("Ability with identifier '{}' not found!", identifier);
        }

        return tAbility;
    }

    public static void registerAbility(OriginsAbility ability) {
        if (ABILITY_MAP.containsKey(ability.getAbilityID())) {
            throw new IllegalArgumentException("Duplicate ability id tried to register: '" + ability.getAbilityID() + "'");
        } else {
            identifiers.add(Identifier.of("origins", ability.abilityID));
            Origins.LOGGER.debug("(SERVER): {}", ability.getAbilityID());
            ABILITY_MAP.put(ability.abilityID, ability);
        }
    }
}
