package fuzs.reachbehind.fabric.client;

import fuzs.reachbehind.ReachBehind;
import fuzs.reachbehind.client.ReachBehindClient;
import fuzs.puzzleslib.common.api.client.core.v1.ClientModConstructor;
import net.fabricmc.api.ClientModInitializer;

public class ReachBehindFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientModConstructor.construct(ReachBehind.MOD_ID, ReachBehindClient::new);
    }
}
