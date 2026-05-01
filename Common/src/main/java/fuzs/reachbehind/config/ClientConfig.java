package fuzs.reachbehind.config;

import fuzs.puzzleslib.common.api.config.v3.Config;
import fuzs.puzzleslib.common.api.config.v3.serialization.ConfigDataSet;
import fuzs.puzzleslib.common.api.config.v3.serialization.KeyedValueProvider;
import fuzs.puzzleslib.common.api.network.v4.NetworkingHelper;
import fuzs.reachbehind.ReachBehind;
import fuzs.reachbehind.data.tags.ModBlockTagProvider;
import fuzs.reachbehind.data.tags.ModEntityTypeTagProvider;
import fuzs.reachbehind.init.ModRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class ClientConfig extends ServerConfig {
    @Config(name = "passes_block_clicks_through",
            description = "The hanging blocks that are permitted to pass clicks to the block they attach to.")
    List<String> passesBlockClicksThroughRaw = ModBlockTagProvider.addCommonTagEntries(KeyedValueProvider.tagAppender(
            Registries.BLOCK).addTag(ModRegistry.PASSES_CLICKS_THROUGH_BLOCK_TAG)).asStringList();
    @Config(name = "passes_entity_clicks_through",
            description = "The hanging entities that are permitted to pass clicks to the block they attach to.")
    List<String> passesEntityClicksThroughRaw = ModEntityTypeTagProvider.addCommonTagEntries(KeyedValueProvider.tagAppender(
            Registries.ENTITY_TYPE).addTag(ModRegistry.PASSES_CLICKS_THROUGH_ENTITY_TYPE_TAG)).asStringList();

    public ConfigDataSet<Block> passesBlockClicksThrough = ConfigDataSet.from(Registries.BLOCK);
    public ConfigDataSet<EntityType<?>> passesEntityClicksThrough = ConfigDataSet.from(Registries.ENTITY_TYPE);

    @Override
    public void afterConfigReload() {
        this.passesBlockClicksThrough = ConfigDataSet.from(Registries.BLOCK, this.passesBlockClicksThroughRaw);
        this.passesEntityClicksThrough = ConfigDataSet.from(Registries.ENTITY_TYPE, this.passesEntityClicksThroughRaw);
    }

    @Override
    public boolean supportsCurrentEnvironment(boolean isClientSide) {
        if (!this.passClicksToAttachedBlockValue.get()) {
            return false;
        } else if (NetworkingHelper.isModPresentServerside(ReachBehind.MOD_ID)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    String getEffectiveEnvironmentLine() {
        return "This option only takes effect in singleplayer and for players on a multiplayer server which does not have this mod installed.";
    }
}
