package io.github.apace100.originsclasses.networking;

import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;

public class ModPacketsS2C {

    public static boolean isWanderingTrader;
    public static boolean isMultiMining;

    public static void register() {
        ClientSidePacketRegistry.INSTANCE.register(ModPackets.TRADER_TYPE, ((packetContext, packetByteBuf) -> {
            boolean isWandering = packetByteBuf.readBoolean();
            packetContext.getTaskQueue().execute(() -> {
                isWanderingTrader = isWandering;
            });
        }));
        ClientSidePacketRegistry.INSTANCE.register(ModPackets.MULTI_MINING, ((packetContext, packetByteBuf) -> {
            boolean isMulti = packetByteBuf.readBoolean();
            packetContext.getTaskQueue().execute(() -> {
                isMultiMining = isMulti;
            });
        }));
    }
}
