package io.github.apace100.originsclasses.mixin;

import io.github.apace100.originsclasses.power.ClassPowerTypes;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(AnimalMateGoal.class)
public class AnimalMateGoalMixin {
    @Shadow @Final protected AnimalEntity animal;

    @Shadow protected AnimalEntity mate;

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/goal/AnimalMateGoal;breed()V"))
    private void produceAdditionalBaby(CallbackInfo ci) {
        if(ClassPowerTypes.TWIN_BREEDING.isActive(this.animal.getLovingPlayer())) {
            if(new Random().nextInt(5) == 0) {
                animal.breed((ServerWorld)animal.getWorld(), this.mate);
            }
        }
    }
}
