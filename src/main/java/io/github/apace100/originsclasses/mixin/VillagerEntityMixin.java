package io.github.apace100.originsclasses.mixin;

import io.github.apace100.originsclasses.networking.ModPackets;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VillagerEntity.class)
public class VillagerEntityMixin {
    @Inject(method = "beginTradeWith", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/VillagerEntity;sendOffers(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/text/Text;I)V", shift = At.Shift.AFTER))
    private void sendTraderType(PlayerEntity customer, CallbackInfo ci) {
        PacketByteBuf data = new PacketByteBuf(Unpooled.buffer());
        data.writeBoolean(false);
        ServerSidePacketRegistry.INSTANCE.sendToPlayer(customer, ModPackets.TRADER_TYPE, data);
    }
}
