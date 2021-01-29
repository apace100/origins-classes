package io.github.apace100.originsclasses.mixin;

import io.github.apace100.originsclasses.networking.ModPacketsS2C;
import io.github.apace100.originsclasses.power.ClassPowerTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.village.SimpleMerchant;
import net.minecraft.village.TradeOffer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SimpleMerchant.class)
public class SimpleMerchantMixin {

    @Shadow @Final private PlayerEntity player;

    @Redirect(method = "trade", at = @At(value = "INVOKE", target = "Lnet/minecraft/village/TradeOffer;use()V"))
    private void preventUseClientSide(TradeOffer tradeOffer) {
        if(ModPacketsS2C.isWanderingTrader || !ClassPowerTypes.TRADE_AVAILABILITY.isActive(player)) {
            tradeOffer.use();
        }
    }
}
