package io.github.apace100.originsclasses.mixin;

import io.github.apace100.originsclasses.power.ClassPowerTypes;
import io.github.apace100.originsclasses.util.EntityUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class ChildCreationMixin {

    @Mixin(WolfEntity.class)
    public static abstract class WolfKids extends TameableEntity {

        protected WolfKids(EntityType<? extends TameableEntity> entityType, World world) {
            super(entityType, world);
        }

        @Inject(method = "createChild", at = @At("RETURN"))
        private void applyBeastmasterBoost(ServerWorld serverWorld, PassiveEntity passiveEntity, CallbackInfoReturnable<WolfEntity> cir) {
            if(ClassPowerTypes.TAMED_ANIMAL_BOOST.isActive(this.getOwner())) {
                EntityUtil.addBeastmasterAttributes(cir.getReturnValue());
            }
        }
    }

    @Mixin(CatEntity.class)
    public static abstract class CatKids extends TameableEntity {

        protected CatKids(EntityType<? extends TameableEntity> entityType, World world) {
            super(entityType, world);
        }

        @Inject(method = "createChild", at = @At("RETURN"))
        private void applyBeastmasterBoost(ServerWorld serverWorld, PassiveEntity passiveEntity, CallbackInfoReturnable<WolfEntity> cir) {
            if(ClassPowerTypes.TAMED_ANIMAL_BOOST.isActive(this.getOwner())) {
                EntityUtil.addBeastmasterAttributes(cir.getReturnValue());
            }
        }
    }

    @Mixin(HorseEntity.class)
    public static abstract class HorseKids extends AbstractHorseEntity {

        protected HorseKids(EntityType<? extends AbstractHorseEntity> entityType, World world) {
            super(entityType, world);
        }

        @Inject(method = "createChild", at = @At("RETURN"))
        private void applyBeastmasterBoost(ServerWorld serverWorld, PassiveEntity passiveEntity, CallbackInfoReturnable<WolfEntity> cir) {
            if(ClassPowerTypes.TAMED_ANIMAL_BOOST.isActive(serverWorld.getPlayerByUuid(this.getOwnerUuid()))) {
                EntityUtil.addBeastmasterAttributes(cir.getReturnValue());
            }
        }
    }

}
