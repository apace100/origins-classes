package io.github.apace100.originsclasses.networking;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class ModPacketsS2C {

    public static boolean isWanderingTrader;
    public static boolean isMultiMining;

    public static void register() {
        ClientPlayConnectionEvents.INIT.register(((clientPlayNetworkHandler, minecraftClient) -> {
            ClientPlayNetworking.registerReceiver(ModPackets.TRADER_TYPE, ModPacketsS2C::receiveTraderType);
            ClientPlayNetworking.registerReceiver(ModPackets.MULTI_MINING, ModPacketsS2C::receiveMultiMine);
        }));
    }

    @Environment(EnvType.CLIENT)
    private static void receiveTraderType(MinecraftClient minecraftClient, ClientPlayNetworkHandler clientPlayNetworkHandler, PacketByteBuf packetByteBuf, PacketSender packetSender) {
        boolean isWandering = packetByteBuf.readBoolean();
        minecraftClient.execute(() -> isWanderingTrader = isWandering);
    }

    @Environment(EnvType.CLIENT)
    private static void receiveMultiMine(MinecraftClient minecraftClient, ClientPlayNetworkHandler clientPlayNetworkHandler, PacketByteBuf packetByteBuf, PacketSender packetSender) {
        boolean isMulti = packetByteBuf.readBoolean();
        minecraftClient.execute(() -> isMultiMining = isMulti);
    }
}
