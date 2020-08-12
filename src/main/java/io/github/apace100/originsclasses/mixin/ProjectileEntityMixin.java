package io.github.apace100.originsclasses.mixin;

import io.github.apace100.originsclasses.power.ClassPowerTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ProjectileEntity.class)
public class ProjectileEntityMixin {

    @ModifyVariable(method = "setProperties", at = @At("HEAD"), ordinal = 4, argsOnly = true)
    private float modifyDivergence(float oldDivergence, Entity user) {
        if(ClassPowerTypes.NO_PROJECTILE_DIVERGENCE.isActive(user)) {
            return 0F;
        }
        return oldDivergence;
    }
}
