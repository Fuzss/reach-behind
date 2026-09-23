package fuzs.reachbehind.common.data.client;

import fuzs.puzzleslib.common.api.client.data.v3.language.AbstractLanguageProvider;
import fuzs.puzzleslib.common.api.data.v3.core.DataProviderContext;
import fuzs.reachbehind.common.ReachBehind;
import fuzs.reachbehind.common.client.ReachBehindClient;

public class ModLanguageProvider extends AbstractLanguageProvider {

    public ModLanguageProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addTranslations() {
        this.addKeyCategory(ReachBehind.MOD_ID, ReachBehind.MOD_NAME);
        this.add(ReachBehindClient.TOGGLE_REACHING_BEHIND_BLOCKS_KEY_MAPPING, "Toggle Reaching Behind Blocks");
        this.add(ReachBehindClient.TOGGLE_REACHING_BEHIND_BLOCKS_STATUS_TRANSLATION_KEY, "Reach Behind Blocks: %s");
        this.add(ReachBehindClient.TOGGLE_REACHING_BEHIND_BLOCKS_UNAVAILABLE_TRANSLATION_KEY,
                "Reaching behind blocks is controlled by the server.");
    }
}
