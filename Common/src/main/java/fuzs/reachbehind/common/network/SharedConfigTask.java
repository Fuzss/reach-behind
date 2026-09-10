package fuzs.reachbehind.common.network;

import fuzs.puzzleslib.common.api.network.v4.NetworkingHelper;
import fuzs.reachbehind.common.ReachBehind;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ConfigurationTask;
import net.minecraft.server.network.ServerConfigurationPacketListenerImpl;

import java.util.function.Consumer;

public record SharedConfigTask(ServerConfigurationPacketListenerImpl listener) implements ConfigurationTask {
    public static final Type TYPE = new Type(ReachBehind.id("shared_config").toString());

    public static void onRegisterConfigurationTasks(MinecraftServer minecraftServer, ServerConfigurationPacketListenerImpl packetListener, Consumer<ConfigurationTask> configurationTaskConsumer) {
        if (NetworkingHelper.hasChannel(packetListener,
                NetworkingHelper.getPayloadType(ClientboundSharedConfigMessage.class))) {
            configurationTaskConsumer.accept(new SharedConfigTask(packetListener));
        }
    }

    @Override
    public void start(Consumer<Packet<?>> connection) {
        connection.accept(ClientboundSharedConfigMessage.of().toPacket());
        NetworkingHelper.finishConfigurationTask(this.listener(), this.type());
    }

    @Override
    public Type type() {
        return TYPE;
    }
}
