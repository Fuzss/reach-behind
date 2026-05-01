package fuzs.reachbehind.neoforge;

import fuzs.puzzleslib.common.api.core.v1.ModConstructor;
import fuzs.puzzleslib.common.api.core.v1.ModLoaderEnvironment;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import fuzs.reachbehind.ReachBehind;
import fuzs.reachbehind.data.tags.ModBlockTagProvider;
import fuzs.reachbehind.data.tags.ModEntityTypeTagProvider;
import net.neoforged.fml.common.Mod;

@Mod(ReachBehind.MOD_ID)
public class ReachBehindNeoForge {

    public ReachBehindNeoForge() {
        // This is for testing the client-only functionality in a development environment.
        if (!ModLoaderEnvironment.INSTANCE.isDevelopmentEnvironment(ReachBehind.MOD_ID)
                || ModLoaderEnvironment.INSTANCE.isClient()) {
            ModConstructor.construct(ReachBehind.MOD_ID, ReachBehind::new);
        }

        DataProviderHelper.registerDataProviders(ReachBehind.MOD_ID,
                ModBlockTagProvider::new,
                ModEntityTypeTagProvider::new);
    }
}
