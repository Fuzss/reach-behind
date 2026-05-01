package fuzs.reachbehind.data.tags;

import fuzs.puzzleslib.common.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.common.api.data.v2.tags.AbstractTagAppender;
import fuzs.puzzleslib.common.api.data.v2.tags.AbstractTagProvider;
import fuzs.reachbehind.init.ModRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;

public class ModEntityTypeTagProvider extends AbstractTagProvider<EntityType<?>> {

    public ModEntityTypeTagProvider(DataProviderContext context) {
        super(Registries.ENTITY_TYPE, context);
    }

    @Override
    public void addTags(HolderLookup.Provider registries) {
        addCommonTagEntries(this.tag(ModRegistry.PASSES_CLICKS_THROUGH_ENTITY_TYPE_TAG));
    }

    public static AbstractTagAppender<EntityType<?>> addCommonTagEntries(AbstractTagAppender<EntityType<?>> tagAppender) {
        return tagAppender.add(EntityType.ITEM_FRAME, EntityType.GLOW_ITEM_FRAME)
                .addOptionalTag("fastitemframes:item_frames");
    }
}
