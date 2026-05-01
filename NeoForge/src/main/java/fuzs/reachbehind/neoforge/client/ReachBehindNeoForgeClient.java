package fuzs.reachbehind.neoforge.client;

import fuzs.puzzleslib.common.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import fuzs.reachbehind.common.ReachBehind;
import fuzs.reachbehind.common.client.ReachBehindClient;
import fuzs.reachbehind.common.data.client.ModLanguageProvider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = ReachBehind.MOD_ID, dist = Dist.CLIENT)
public class ReachBehindNeoForgeClient {

    public ReachBehindNeoForgeClient() {
        ClientModConstructor.construct(ReachBehind.MOD_ID, ReachBehindClient::new);
        DataProviderHelper.registerDataProviders(ReachBehind.MOD_ID, ModLanguageProvider::new);
    }
}
