package fuzs.reachbehind.common.network;

import fuzs.puzzleslib.common.api.network.v4.message.MessageListener;
import fuzs.puzzleslib.common.api.network.v4.message.configuration.ClientboundConfigurationMessage;
import fuzs.reachbehind.common.ReachBehind;
import fuzs.reachbehind.common.config.CommonConfig;
import fuzs.reachbehind.common.config.GlobalSharedConfig;
import fuzs.reachbehind.common.config.SharedConfig;
import fuzs.reachbehind.common.config.SharedState;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record ClientboundSharedConfigMessage(boolean passClicksToAttachedBlock,
                                             GlobalSharedConfig sharedConfig) implements ClientboundConfigurationMessage {
    public static final StreamCodec<ByteBuf, ClientboundSharedConfigMessage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            ClientboundSharedConfigMessage::passClicksToAttachedBlock,
            SharedState.STREAM_CODEC,
            ClientboundSharedConfigMessage::sharedConfig,
            ClientboundSharedConfigMessage::new);

    public static ClientboundSharedConfigMessage of() {
        return of(ReachBehind.CONFIG.get(CommonConfig.class));
    }

    public static ClientboundSharedConfigMessage of(GlobalSharedConfig sharedConfig) {
        return new ClientboundSharedConfigMessage(sharedConfig.passClicksThrough(), sharedConfig.plainCopy());
    }

    @Override
    public MessageListener<Context> getListener() {
        return new MessageListener<Context>() {
            @Override
            public void accept(Context context) {
                SharedConfig sharedConfig = this.fetchSharedConfig(context.client().isLocalServer());
                ReachBehind.CONFIG.get(CommonConfig.class).setSharedConfig(sharedConfig);
            }

            private SharedConfig fetchSharedConfig(boolean isLocalServer) {
                return isLocalServer ? ClientboundSharedConfigMessage.this.sharedConfig() :
                        ClientboundSharedConfigMessage.this.sharedConfig()
                                .setPassClicksThrough(ClientboundSharedConfigMessage.this.passClicksToAttachedBlock());
            }
        };
    }
}
