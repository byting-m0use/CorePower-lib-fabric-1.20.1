package net.bytem0use.origins.api;

import net.bytem0use.origins.api.type.AbilityType;

public class OriginsAbility {
    private static String keybind;
    public String abilityID;

    public OriginsAbility(String abilityID, AbilityType pType, int duration, int cooldown) {
        this.getAbilityID();
        this.getAbilityCategory();
        this.getAbilityType(pType);
        this.getKeybind();
        this.setKeybind(keybind);
    }

    public OriginsAbility(String abilityID, AbilityType abilityType) {
        this.getAbilityID();
        this.getAbilityType(AbilityType.TOGGLE);
    }

    public Object getAbilityID() {
        return null;
    }

    public void getAbilityType(AbilityType type) {
    }

    public void getAbilityCategory() {
    }

    public void getKeybind() {
    }

    public void setKeybind(String keybind) {
        OriginsAbility.keybind = keybind;
    }
}
