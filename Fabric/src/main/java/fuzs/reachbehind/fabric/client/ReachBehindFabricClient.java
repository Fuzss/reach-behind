package fuzs.reachbehind.fabric.client;

import fuzs.reachbehind.common.ReachBehind;
import fuzs.reachbehind.common.client.ReachBehindClient;
import fuzs.puzzleslib.common.api.client.core.v1.ClientModConstructor;
import net.fabricmc.api.ClientModInitializer;

public class ReachBehindFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientModConstructor.construct(ReachBehind.MOD_ID, ReachBehindClient::new);
    }
}
