package fuzs.reachbehind.neoforge;

import fuzs.puzzleslib.common.api.core.v1.ModConstructor;
import fuzs.puzzleslib.common.api.core.v1.ModLoaderEnvironment;
import fuzs.puzzleslib.neoforge.api.data.v3.core.DataProviderBuilder;
import fuzs.reachbehind.common.ReachBehind;
import fuzs.reachbehind.common.data.tags.ModBlockTagsProvider;
import fuzs.reachbehind.common.data.tags.ModEntityTypeTagsProvider;
import net.neoforged.fml.common.Mod;

@Mod(ReachBehind.MOD_ID)
public class ReachBehindNeoForge {

    public ReachBehindNeoForge() {
        // This is for testing the client-only functionality in a development environment.
        if (!ModLoaderEnvironment.INSTANCE.isDevelopmentEnvironment(ReachBehind.MOD_ID)
                || ModLoaderEnvironment.INSTANCE.isClient()) {
            ModConstructor.construct(ReachBehind.MOD_ID, ReachBehind::new);
        }

        DataProviderBuilder.of(ReachBehind.MOD_ID)
                .addProvider(ModBlockTagsProvider::new, ModEntityTypeTagsProvider::new);
    }
}
