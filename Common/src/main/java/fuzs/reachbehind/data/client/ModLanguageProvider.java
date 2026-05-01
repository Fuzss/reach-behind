package fuzs.reachbehind.data.client;

import fuzs.puzzleslib.common.api.client.data.v2.AbstractLanguageProvider;
import fuzs.puzzleslib.common.api.data.v2.core.DataProviderContext;
import fuzs.reachbehind.ReachBehind;
import fuzs.reachbehind.client.ReachBehindClient;

public class ModLanguageProvider extends AbstractLanguageProvider {

    public ModLanguageProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.addKeyCategory(ReachBehind.MOD_ID, ReachBehind.MOD_NAME);
        translationBuilder.add(ReachBehindClient.TOGGLE_REACHING_BEHIND_BLOCKS_KEY_MAPPING, "Toggle Reaching Behind Blocks");
        translationBuilder.add(ReachBehindClient.TOGGLE_REACHING_BEHIND_BLOCKS_STATUS_TRANSLATION_KEY, "Reach Behind Blocks: %s");
        translationBuilder.add(ReachBehindClient.TOGGLE_REACHING_BEHIND_BLOCKS_UNAVAILABLE_TRANSLATION_KEY,
                "Reaching behind blocks is controlled by the server.");
    }
}
