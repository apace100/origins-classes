package io.github.apace100.originsclasses.mixin;

import io.github.apace100.originsclasses.power.ClassPowerTypes;
import net.minecraft.entity.ai.brain.task.BreedTask;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Random;

@Mixin(BreedTask.class)
public class BreedTaskMixin {

    @Inject(method = "keepRunning", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/AnimalEntity;breed(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/passive/AnimalEntity;)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void produceAdditionalBaby(ServerWorld serverWorld, AnimalEntity animalEntity, long l, CallbackInfo ci, AnimalEntity animalEntity2) {
        if(ClassPowerTypes.TWIN_BREEDING.isActive(animalEntity.getLovingPlayer())) {
            if(new Random().nextInt(5) == 0) {
                animalEntity.breed(serverWorld, animalEntity2);
            }
        }
    }
}
