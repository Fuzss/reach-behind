package fuzs.reachbehind.common;

import fuzs.puzzleslib.common.api.config.v3.ConfigHolder;
import fuzs.puzzleslib.common.api.core.v1.ModConstructor;
import fuzs.puzzleslib.common.api.core.v1.context.PayloadTypesContext;
import fuzs.puzzleslib.common.api.event.v1.core.EventPhase;
import fuzs.puzzleslib.common.api.event.v1.entity.player.PlayerInteractEvents;
import fuzs.puzzleslib.common.api.event.v1.server.RegisterConfigurationTasksCallback;
import fuzs.reachbehind.common.config.ClientConfig;
import fuzs.reachbehind.common.config.CommonConfig;
import fuzs.reachbehind.common.handler.CommonMenuProviderInteraction;
import fuzs.reachbehind.common.init.ModRegistry;
import fuzs.reachbehind.common.network.ClientboundSharedConfigMessage;
import fuzs.reachbehind.common.network.SharedConfigTask;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReachBehind implements ModConstructor {
    public static final String MOD_ID = "reachbehind";
    public static final String MOD_NAME = "Reach Behind";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static final ConfigHolder CONFIG = ConfigHolder.builder(MOD_ID)
            .client(ClientConfig.class)
            .common(CommonConfig.class);

    @Override
    public void onConstructMod() {
        ModRegistry.bootstrap();
        registerEventHandlers();
    }

    private static void registerEventHandlers() {
        RegisterConfigurationTasksCallback.EVENT.register(SharedConfigTask::onRegisterConfigurationTasks);
        PlayerInteractEvents.USE_BLOCK.register(EventPhase.BEFORE, CommonMenuProviderInteraction.INSTANCE::onUseBlock);
        PlayerInteractEvents.USE_ENTITY.register(EventPhase.BEFORE,
                CommonMenuProviderInteraction.INSTANCE::onUseEntity);
    }

    @Override
    public void onRegisterPayloadTypes(PayloadTypesContext context) {
        context.optional();
        context.configurationToClient(ClientboundSharedConfigMessage.class,
                ClientboundSharedConfigMessage.STREAM_CODEC);
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
