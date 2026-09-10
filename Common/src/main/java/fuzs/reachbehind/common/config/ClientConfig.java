package fuzs.reachbehind.common.config;

import fuzs.puzzleslib.common.api.config.v3.Config;
import fuzs.puzzleslib.common.api.config.v3.ConfigCore;
import fuzs.puzzleslib.common.api.config.v3.serialization.ConfigDataSet;
import fuzs.puzzleslib.common.api.config.v3.serialization.KeyedValueProvider;
import fuzs.reachbehind.common.data.tags.ModBlockTagProvider;
import fuzs.reachbehind.common.data.tags.ModEntityTypeTagProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class ClientConfig implements ConfigCore {
    private static final String CLIENT_ONLY_DISCLAIMER = "This option only takes effect when playing on a multiplayer server which does not have this mod installed.";

    @Config(name = "passes_block_clicks_through",
            description = {
                    "The hanging blocks that are permitted to pass clicks to the block they attach to.",
                    CLIENT_ONLY_DISCLAIMER
            })
    List<String> passesBlockClicksThroughRaw = ModBlockTagProvider.addCommonTagEntries(KeyedValueProvider.<Block>tags())
            .asStringList();
    @Config(name = "passes_entity_clicks_through",
            description = {
                    "The hanging entities that are permitted to pass clicks to the block they attach to.",
                    CLIENT_ONLY_DISCLAIMER
            })
    List<String> passesEntityClicksThroughRaw = ModEntityTypeTagProvider.addCommonTagEntries(KeyedValueProvider.<EntityType<?>>tags())
            .asStringList();

    public ConfigDataSet<Block> passesBlockClicksThrough = ConfigDataSet.from(Registries.BLOCK);
    public ConfigDataSet<EntityType<?>> passesEntityClicksThrough = ConfigDataSet.from(Registries.ENTITY_TYPE);

    @Override
    public void afterConfigReload() {
        this.passesBlockClicksThrough = ConfigDataSet.from(Registries.BLOCK, this.passesBlockClicksThroughRaw);
        this.passesEntityClicksThrough = ConfigDataSet.from(Registries.ENTITY_TYPE, this.passesEntityClicksThroughRaw);
    }
}
