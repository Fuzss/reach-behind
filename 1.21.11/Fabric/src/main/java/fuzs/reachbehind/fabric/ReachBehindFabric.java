package fuzs.reachbehind.fabric;

import fuzs.reachbehind.ReachBehind;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import net.fabricmc.api.ModInitializer;

public class ReachBehindFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ModConstructor.construct(ReachBehind.MOD_ID, ReachBehind::new);
    }
}
