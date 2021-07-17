package io.github.apace100.originsclasses.mixin;

import net.minecraft.entity.player.HungerManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HungerManager.class)
public abstract class HungerManagerMixin {

    @Shadow public abstract void add(int food, float f);

    @Inject(method = "eat", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/HungerManager;add(IF)V", shift = At.Shift.AFTER))
    private void addFoodBonus(Item item, ItemStack stack, CallbackInfo ci) {
        if(stack.hasNbt()) {
            NbtCompound tag = stack.getNbt();
            if(tag.contains("FoodBonus")) {
                int foodBonus = tag.getInt("FoodBonus");
                float saturationBonus = (float)foodBonus * 0.2F;
                this.add(foodBonus, saturationBonus);
            }
        }
    }
}
