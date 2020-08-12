package io.github.apace100.originsclasses.mixin;

import io.github.apace100.originsclasses.power.ClassPowerTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Random;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "dropLoot", at = @At(value = "INVOKE", target = "Lnet/minecraft/loot/LootTable;generateLoot(Lnet/minecraft/loot/context/LootContext;Ljava/util/function/Consumer;)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void dropAdditionalRancherLoot(DamageSource source, boolean causedByPlayer, CallbackInfo ci, Identifier identifier, LootTable lootTable, LootContext.Builder builder) {
        if(causedByPlayer && (Object)this instanceof AnimalEntity && ClassPowerTypes.MORE_ANIMAL_LOOT.isActive(source.getAttacker())) {
            if(new Random().nextInt(10) < 3) {
                lootTable.generateLoot(builder.build(LootContextTypes.ENTITY), ((LivingEntity)(Object)this)::dropStack);
            }
        }
    }
}
