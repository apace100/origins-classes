package io.github.apace100.originsclasses.mixin;

import io.github.apace100.originsclasses.power.ClassPowerTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.List;

@Mixin(PotionItem.class)
public class PotionItemMixin {

    @Inject(method = "finishUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;)Z"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void diffusePotionToTamedsDuration(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir, PlayerEntity playerEntity, List<StatusEffectInstance> list, Iterator var6, StatusEffectInstance statusEffectInstance) {
        if(ClassPowerTypes.TAMED_POTION_DIFFUSAL.isActive(playerEntity)) {
            world.getEntitiesByClass(TameableEntity.class, playerEntity.getBoundingBox().stretch(8F, 2F, 8F).stretch(-8f, -2F, -8F), e -> e.getOwner() == playerEntity).forEach(e -> e.addStatusEffect(new StatusEffectInstance(statusEffectInstance)));
        }
    }

    @Inject(method = "finishUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/effect/StatusEffect;applyInstantEffect(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/LivingEntity;ID)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void diffusePotionToTamedsInstant(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir, PlayerEntity playerEntity, List<StatusEffectInstance> list, Iterator var6, StatusEffectInstance statusEffectInstance) {
        if(ClassPowerTypes.TAMED_POTION_DIFFUSAL.isActive(playerEntity)) {
            world.getEntitiesByClass(TameableEntity.class, playerEntity.getBoundingBox().stretch(8F, 2F, 8F).stretch(-8f, -2F, -8F), e -> e.getOwner() == playerEntity).forEach(e -> {
                statusEffectInstance.getEffectType().applyInstantEffect(playerEntity, playerEntity, e, statusEffectInstance.getAmplifier(), 1.0D);
            });
        }
    }
}
