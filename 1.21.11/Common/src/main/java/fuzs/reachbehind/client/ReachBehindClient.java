package fuzs.reachbehind.client;

import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.client.core.v1.context.KeyMappingsContext;
import fuzs.puzzleslib.api.client.event.v1.entity.player.InteractionInputEvents;
import fuzs.puzzleslib.api.client.key.v1.KeyActivationHandler;
import fuzs.puzzleslib.api.client.key.v1.KeyMappingHelper;
import fuzs.puzzleslib.api.event.v1.core.EventPhase;
import fuzs.puzzleslib.api.network.v4.NetworkingHelper;
import fuzs.reachbehind.ReachBehind;
import fuzs.reachbehind.client.handler.ClientMenuProviderInteraction;
import fuzs.reachbehind.config.ClientConfig;
import fuzs.reachbehind.config.ServerConfig;
import fuzs.reachbehind.config.SharedConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.Nullable;

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
                    SharedConfig serverConfig = chooseSharedConfig(minecraft);
                    Component component = chooseFeedbackComponent(serverConfig);
                    minecraft.gui.setOverlayMessage(component, false);
                }));
    }

    /**
     * @see fuzs.reachbehind.handler.AbstractMenuProviderInteraction
     */
    private static @Nullable SharedConfig chooseSharedConfig(Minecraft minecraft) {
        if (!NetworkingHelper.isModPresentServerside(ReachBehind.MOD_ID)) {
            return ReachBehind.CONFIG.get(ClientConfig.class);
        } else if (minecraft.isLocalServer()) {
            return ReachBehind.CONFIG.get(ServerConfig.class);
        } else {
            return null;
        }
    }

    private static Component chooseFeedbackComponent(SharedConfig sharedConfig) {
        if (sharedConfig != null) {
            return Component.translatable(TOGGLE_REACHING_BEHIND_BLOCKS_STATUS_TRANSLATION_KEY,
                    sharedConfig.flipPassClicksToAttachedBlock() ? ON_COMPONENT : OFF_COMPONENT);
        } else {
            return Component.translatable(TOGGLE_REACHING_BEHIND_BLOCKS_UNAVAILABLE_TRANSLATION_KEY);
        }
    }
}
