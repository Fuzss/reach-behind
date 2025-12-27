package fuzs.reachbehind.init;

import fuzs.puzzleslib.api.init.v3.tags.TagFactory;
import fuzs.reachbehind.ReachBehind;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;

public class ModRegistry {
    static final TagFactory TAGS = TagFactory.make(ReachBehind.MOD_ID);
    /**
     * The hanging blocks that are permitted to pass clicks to the block they attach to.
     */
    public static final TagKey<Block> PASSES_CLICKS_THROUGH_BLOCK_TAG = TAGS.registerBlockTag("passes_clicks_through");
    /**
     * Blocks that do not accept passed-through clicks from hanging blocks or entities.
     */
    public static final TagKey<Block> REQUIRES_DIRECT_CLICKS_BLOCK_TAG = TAGS.registerBlockTag("requires_direct_clicks");
    /**
     * The hanging entities that are permitted to pass clicks to the block they attach to.
     */
    public static final TagKey<EntityType<?>> PASSES_CLICKS_THROUGH_ENTITY_TYPE_TAG = TAGS.registerEntityTypeTag(
            "passes_clicks_through");

    public static void bootstrap() {
        // NO-OP
    }
}
