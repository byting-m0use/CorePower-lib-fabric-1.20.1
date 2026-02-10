package net.bytem0use.core_power.api.abilities;

import net.bytem0use.core_power.api.abilities.keys.Ability1;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

//idea is to make it so that externally I could assign each keybind an action
public class CoreKeybindsRegister {
    public static final String KEY_CATEGORY_CORE = "key.category.origins.origins_mod";
    public static final String ABILITY_1 = "key.origins.ability_1";
    public static final String ABILITY_2 = "key.origins.ability_2";
    public static final String ABILITY_3 = "key.origins.ability_3";
    public static final String ABILITY_4 = "key.origins.ability_4";
    public static final String ABILITY_5 = "key.origins.ability_5";
    public static final String ABILITY_6 = "key.origins.ability_6";

    public static KeyBinding ability_1;
    public static KeyBinding ability_2;
    public static KeyBinding ability_3;
    public static KeyBinding ability_4;
    public static KeyBinding ability_5;
    public static KeyBinding ability_6;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
        });
    }

    public static void register() {
        ability_1 = KeyBindingHelper.registerKeyBinding(new Ability1(
                ABILITY_1,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                KEY_CATEGORY_CORE
        ));

        ability_2 = KeyBindingHelper.registerKeyBinding(new Ability1(
                ABILITY_2,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_F,
                KEY_CATEGORY_CORE
        ));

        ability_3 = KeyBindingHelper.registerKeyBinding(new Ability1(
                ABILITY_3,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                KEY_CATEGORY_CORE
        ));

        ability_4 = KeyBindingHelper.registerKeyBinding(new Ability1(
                ABILITY_4,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_C,
                KEY_CATEGORY_CORE
        ));

        ability_5 = KeyBindingHelper.registerKeyBinding(new Ability1(
                ABILITY_5,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_X,
                KEY_CATEGORY_CORE
        ));

        ability_6 = KeyBindingHelper.registerKeyBinding(new Ability1(
                ABILITY_6,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                KEY_CATEGORY_CORE
        ));
        registerKeyInputs();
    }

}
