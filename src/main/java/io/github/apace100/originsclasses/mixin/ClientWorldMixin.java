package io.github.apace100.originsclasses.mixin;

import io.github.apace100.originsclasses.effect.StealthEffect;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {

    @Inject(method = "syncWorldEvent", at = @At("HEAD"), cancellable = true)
    private void cancelEffectsDuringStealth(PlayerEntity player, int eventId, BlockPos pos, int data, CallbackInfo ci) {
        if(player != null && player.hasStatusEffect(StealthEffect.INSTANCE)) {
            ci.cancel();
        }
    }
}
