package fuzs.reachbehind.common.config;

import fuzs.reachbehind.common.ReachBehind;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Optional;

public final class SharedState implements GlobalSharedConfig {
    public static final StreamCodec<ByteBuf, GlobalSharedConfig> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.BOOL,
            GlobalSharedConfig::requiresEmptyHand,
            SharedState::new);

    private final Optional<Boolean> passClicksThrough;
    private final boolean requiresEmptyHand;

    public SharedState(boolean requiresEmptyHand) {
        this(Optional.empty(), requiresEmptyHand);
    }

    public SharedState(Optional<Boolean> passClicksThrough, boolean requiresEmptyHand) {
        this.passClicksThrough = passClicksThrough;
        this.requiresEmptyHand = requiresEmptyHand;
    }

    @Override
    public boolean passClicksThrough() {
        return this.passClicksThrough.orElseGet(() -> ReachBehind.CONFIG.get(CommonConfig.class).passClicksThrough());
    }

    @Override
    public boolean requiresEmptyHand() {
        return this.requiresEmptyHand;
    }

    @Override
    public GlobalSharedConfig setPassClicksThrough(boolean passClicksThrough) {
        return new SharedState(Optional.of(passClicksThrough), this.requiresEmptyHand);
    }
}
