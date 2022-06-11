package io.github.apace100.originsclasses.mixin;

import io.github.apace100.originsclasses.power.ClassPowerTypes;
import io.github.apace100.originsclasses.util.EntityUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractHorseEntity.class)
public class HorseBaseEntityMixin {

    @Inject(method = "bondWithPlayer", at = @At("TAIL"))
    private void applyBeastmasterAttributes(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        if(ClassPowerTypes.TAMED_ANIMAL_BOOST.isActive(player)) {
            EntityUtil.addBeastmasterAttributes((LivingEntity)(Object)this);
        }
    }
}
