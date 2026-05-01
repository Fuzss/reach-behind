package fuzs.reachbehind.common.data.tags;

import fuzs.puzzleslib.common.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.common.api.data.v2.tags.AbstractTagAppender;
import fuzs.puzzleslib.common.api.data.v2.tags.AbstractTagProvider;
import fuzs.reachbehind.common.init.ModRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;

public class ModBlockTagProvider extends AbstractTagProvider<Block> {

    public ModBlockTagProvider(DataProviderContext context) {
        super(Registries.BLOCK, context);
    }

    @Override
    public void addTags(HolderLookup.Provider registries) {
        addCommonTagEntries(this.tag(ModRegistry.PASSES_CLICKS_THROUGH_BLOCK_TAG));
        this.tag(ModRegistry.REQUIRES_DIRECT_CLICKS_BLOCK_TAG);
    }

    public static AbstractTagAppender<Block> addCommonTagEntries(AbstractTagAppender<Block> tagAppender) {
        return tagAppender.addTag(BlockTags.WALL_SIGNS, BlockTags.BANNERS).addOptionalTag("fastitemframes:item_frames");
    }
}
