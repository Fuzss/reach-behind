package fuzs.reachbehind.common.handler;

import fuzs.puzzleslib.common.api.event.v1.core.EventResultHolder;
import fuzs.reachbehind.common.ReachBehind;
import fuzs.reachbehind.common.config.ServerConfig;
import fuzs.reachbehind.common.init.ModRegistry;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public final class CommonMenuProviderInteraction extends AbstractMenuProviderInteraction {
    public static final CommonMenuProviderInteraction INSTANCE = new CommonMenuProviderInteraction();

    private CommonMenuProviderInteraction() {
        // NO-OP
    }

    public EventResultHolder<InteractionResult> onUseEntity(Player player, Level level, InteractionHand interactionHand, Entity entity, Vec3 hitVector) {
        if (!ReachBehind.CONFIG.getHolder(ServerConfig.class).isAvailable()
                || !ReachBehind.CONFIG.get(ServerConfig.class).supportsCurrentEnvironment(level.isClientSide())) {
            return EventResultHolder.pass();
        }

        InteractionResult interactionResult = this.interactWithEntity(player, level, interactionHand, entity);
        if (interactionResult != null) {
            return EventResultHolder.interrupt(interactionResult);
        } else {
            return EventResultHolder.pass();
        }
    }

    public EventResultHolder<InteractionResult> onUseBlock(Player player, Level level, InteractionHand interactionHand, BlockHitResult hitResult) {
        if (!ReachBehind.CONFIG.getHolder(ServerConfig.class).isAvailable()
                || !ReachBehind.CONFIG.get(ServerConfig.class).supportsCurrentEnvironment(level.isClientSide())) {
            return EventResultHolder.pass();
        }

        InteractionResult interactionResult = this.interactWithBlock(player, level, interactionHand, hitResult);
        if (interactionResult != null) {
            return EventResultHolder.interrupt(interactionResult);
        } else {
            return EventResultHolder.pass();
        }
    }

    @Override
    protected boolean isEntityPassingThroughClicks(Entity entity) {
        return entity.is(ModRegistry.PASSES_CLICKS_THROUGH_ENTITY_TYPE_TAG);
    }

    @Override
    protected boolean isBlockPassingThroughClicks(BlockState blockState) {
        return blockState.is(ModRegistry.PASSES_CLICKS_THROUGH_BLOCK_TAG);
    }

    @Override
    protected boolean requiresEmptyHand() {
        return ReachBehind.CONFIG.get(ServerConfig.class).requiresEmptyHand;
    }

    /**
     * @see BlockState#useItemOn(ItemStack, Level, Player, InteractionHand, BlockHitResult)
     * @see BlockState#useWithoutItem(Level, Player, BlockHitResult)
     */
    @Override
    protected @Nullable InteractionResult useBlock(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult hitResult) {
        ItemStack itemStack = player.getItemInHand(interactionHand).copy();
        InteractionResult useItemOnResult;
        if (!this.requiresEmptyHand()) {
            useItemOnResult = blockState.useItemOn(player.getItemInHand(interactionHand),
                    level,
                    player,
                    interactionHand,
                    hitResult);
            if (useItemOnResult.consumesAction()) {
                if (player instanceof ServerPlayer serverPlayer) {
                    CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, blockPos, itemStack);
                }

                return useItemOnResult;
            }
        } else {
            useItemOnResult = InteractionResult.TRY_WITH_EMPTY_HAND;
        }

        if (useItemOnResult instanceof InteractionResult.TryEmptyHandInteraction
                && interactionHand == InteractionHand.MAIN_HAND) {
            InteractionResult useWithoutItemResult = blockState.useWithoutItem(level, player, hitResult);
            if (useWithoutItemResult.consumesAction()) {
                if (player instanceof ServerPlayer serverPlayer) {
                    CriteriaTriggers.DEFAULT_BLOCK_USE.trigger(serverPlayer, blockPos);
                }

                return useWithoutItemResult;
            }
        }

        return null;
    }
}
