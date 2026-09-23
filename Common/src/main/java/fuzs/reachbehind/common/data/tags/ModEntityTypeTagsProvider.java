package fuzs.reachbehind.common.data.tags;

import fuzs.puzzleslib.common.api.data.v3.core.DataProviderContext;
import fuzs.puzzleslib.common.api.data.v3.tags.AbstractTagAppender;
import fuzs.puzzleslib.common.api.data.v3.tags.AbstractTagsProvider;
import fuzs.reachbehind.common.init.ModRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypeIds;

public class ModEntityTypeTagsProvider extends AbstractTagsProvider<EntityType<?>> {

    public ModEntityTypeTagsProvider(DataProviderContext context) {
        super(Registries.ENTITY_TYPE, context);
    }

    @Override
    public void addTags(HolderLookup.Provider registries) {
        addCommonEntities(this.tag(ModRegistry.PASSES_CLICKS_THROUGH_ENTITY_TYPE_TAG));
    }

    public static AbstractTagAppender<EntityType<?>> addCommonEntities(AbstractTagAppender<EntityType<?>> tagAppender) {
        return tagAppender.add(EntityTypeIds.ITEM_FRAME, EntityTypeIds.GLOW_ITEM_FRAME)
                .addOptionalTag("fastitemframes:item_frames");
    }
}
