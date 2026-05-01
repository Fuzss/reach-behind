package fuzs.reachbehind;

import fuzs.puzzleslib.common.api.config.v3.ConfigHolder;
import fuzs.puzzleslib.common.api.core.v1.ModConstructor;
import fuzs.puzzleslib.common.api.event.v1.core.EventPhase;
import fuzs.puzzleslib.common.api.event.v1.entity.player.PlayerInteractEvents;
import fuzs.reachbehind.config.ClientConfig;
import fuzs.reachbehind.config.ServerConfig;
import fuzs.reachbehind.handler.CommonMenuProviderInteraction;
import fuzs.reachbehind.init.ModRegistry;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReachBehind implements ModConstructor {
    public static final String MOD_ID = "reachbehind";
    public static final String MOD_NAME = "Reach Behind";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static final ConfigHolder CONFIG = ConfigHolder.builder(MOD_ID)
            .client(ClientConfig.class)
            .server(ServerConfig.class);

    @Override
    public void onConstructMod() {
        ModRegistry.bootstrap();
        registerEventHandlers();
    }

    private static void registerEventHandlers() {
        PlayerInteractEvents.USE_BLOCK.register(EventPhase.BEFORE, CommonMenuProviderInteraction.INSTANCE::onUseBlock);
        PlayerInteractEvents.USE_ENTITY.register(EventPhase.BEFORE,
                CommonMenuProviderInteraction.INSTANCE::onUseEntity);
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
