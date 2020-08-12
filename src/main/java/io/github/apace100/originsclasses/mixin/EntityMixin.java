package io.github.apace100.originsclasses.mixin;

import io.github.apace100.originsclasses.power.ClassPowerTypes;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {

    @Inject(method = "isSneaky", at = @At("HEAD"), cancellable = true)
    private void modifySneakyState(CallbackInfoReturnable<Boolean> cir) {
        if(ClassPowerTypes.SNEAKY.isActive((Entity)(Object)this)) {
            cir.setReturnValue(true);
        }
    }
}
