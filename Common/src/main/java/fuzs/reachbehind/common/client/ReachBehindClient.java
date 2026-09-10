package fuzs.reachbehind.common.client;

import fuzs.puzzleslib.common.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.common.api.client.core.v1.context.KeyMappingsContext;
import fuzs.puzzleslib.common.api.client.event.v1.entity.player.InteractionInputEvents;
import fuzs.puzzleslib.common.api.client.key.v1.KeyActivationHandler;
import fuzs.puzzleslib.common.api.client.key.v1.KeyMappingHelper;
import fuzs.puzzleslib.common.api.event.v1.core.EventPhase;
import fuzs.puzzleslib.common.api.network.v4.NetworkingHelper;
import fuzs.reachbehind.common.ReachBehind;
import fuzs.reachbehind.common.client.handler.ClientMenuProviderInteraction;
import fuzs.reachbehind.common.config.CommonConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class ReachBehindClient implements ClientModConstructor {
    public static final KeyMapping TOGGLE_REACHING_BEHIND_BLOCKS_KEY_MAPPING = KeyMappingHelper.registerUnboundKeyMapping(
            ReachBehind.id("toggle_reaching_behind_blocks"));
    private static final Component ON_COMPONENT = CommonComponents.OPTION_ON.copy().withStyle(ChatFormatting.GREEN);
    private static final Component OFF_COMPONENT = CommonComponents.OPTION_OFF.copy().withStyle(ChatFormatting.RED);
    public static final String TOGGLE_REACHING_BEHIND_BLOCKS_STATUS_TRANSLATION_KEY =
            TOGGLE_REACHING_BEHIND_BLOCKS_KEY_MAPPING.getName() + ".message";
    public static final String TOGGLE_REACHING_BEHIND_BLOCKS_UNAVAILABLE_TRANSLATION_KEY =
            TOGGLE_REACHING_BEHIND_BLOCKS_KEY_MAPPING.getName() + ".unavailable";

    @Override
    public void onConstructMod() {
        registerEventHandlers();
    }

    private static void registerEventHandlers() {
        InteractionInputEvents.USE.register(EventPhase.BEFORE,
                ClientMenuProviderInteraction.INSTANCE::onUseInteraction);
    }

    @Override
    public void onRegisterKeyMappings(KeyMappingsContext context) {
        context.registerKeyMapping(TOGGLE_REACHING_BEHIND_BLOCKS_KEY_MAPPING,
                KeyActivationHandler.forGame((Minecraft minecraft) -> {
                    boolean mayUseToggleKeybind = mayUseToggleKeybind(minecraft);
                    Component component = pickFeedbackComponent(mayUseToggleKeybind);
                    minecraft.gui.setOverlayMessage(component, false);
                }));
    }

    private static boolean mayUseToggleKeybind(Minecraft minecraft) {
        if (!NetworkingHelper.isModPresentServerside(ReachBehind.MOD_ID)) {
            // The mod is only installed client side, we are using client-only mode which mimics player interactions.
            return true;
        } else if (minecraft.isLocalServer()) {
            // The mod is running in singleplayer, we have full control over both the client & server.
            return true;
        } else {
            // The mod is installed on the multiplayer server, control is out of our hands.
            return false;
        }
    }

    private static Component pickFeedbackComponent(boolean mayUseToggleKeybind) {
        if (mayUseToggleKeybind) {
            return Component.translatable(TOGGLE_REACHING_BEHIND_BLOCKS_STATUS_TRANSLATION_KEY,
                    ReachBehind.CONFIG.get(CommonConfig.class).togglePassClicksBehind() ? ON_COMPONENT : OFF_COMPONENT);
        } else {
            return Component.translatable(TOGGLE_REACHING_BEHIND_BLOCKS_UNAVAILABLE_TRANSLATION_KEY);
        }
    }
}
