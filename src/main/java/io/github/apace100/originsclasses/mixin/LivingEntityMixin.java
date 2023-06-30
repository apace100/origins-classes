package io.github.apace100.originsclasses.mixin;

import io.github.apace100.originsclasses.power.ClassPowerTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Random;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }
    
    @Inject(method = "dropLoot", at = @At(value = "INVOKE", target = "Lnet/minecraft/loot/LootTable;generateLoot(Lnet/minecraft/loot/context/LootContextParameterSet;JLjava/util/function/Consumer;)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void dropAdditionalRancherLoot(DamageSource damageSource, boolean causedByPlayer, CallbackInfo ci, Identifier identifier, LootTable lootTable, LootContextParameterSet.Builder builder, LootContextParameterSet lootContextParameterSet) {
        if(causedByPlayer && (Object)this instanceof AnimalEntity && ClassPowerTypes.MORE_ANIMAL_LOOT.isActive(damageSource.getAttacker())) {
            if(new Random().nextInt(10) < 3) {
                lootTable.generateLoot(builder.build(LootContextTypes.ENTITY), ((LivingEntity)(Object)this)::dropStack);
            }
        }
    }
    
    @Inject(method = "addStatusEffect*", at = @At("RETURN"))
    private void addStatusEffect(StatusEffectInstance effect, CallbackInfoReturnable<Boolean> ci) {
        if (ci.getReturnValue() && !effect.isAmbient()) {
            if(ClassPowerTypes.TAMED_POTION_DIFFUSAL.isActive(this)) {
                getWorld().getEntitiesByClass(TameableEntity.class, getBoundingBox().stretch(8F, 2F, 8F).stretch(-8f, -2F, -8F), e -> e.getOwner() == (Object) this).forEach(e -> e.addStatusEffect(effect));
            }
        }
    }
}
