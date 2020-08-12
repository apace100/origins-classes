package io.github.apace100.originsclasses.mixin;

import io.github.apace100.originsclasses.effect.StealthEffect;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Inject(method = "processWorldEvent", at = @At("HEAD"), cancellable = true)
    private void cancelSoundsFromStealth(PlayerEntity source, int eventId, BlockPos pos, int data, CallbackInfo ci) {
        if(source != null && source.hasStatusEffect(StealthEffect.INSTANCE)) {
            ci.cancel();
        }
    }
}
