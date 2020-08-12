package io.github.apace100.originsclasses.mixin;

import io.github.apace100.originsclasses.OriginsClasses;
import net.minecraft.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

    @ModifyVariable(method = "generateEnchantments", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/item/Item;getEnchantability()I"), ordinal = 0)
    private static int modifyEnchantabilityForClerics(int original) {
        if(original > 0 && OriginsClasses.isClericEnchanting) {
            OriginsClasses.isClericEnchanting = false;
            return original + 10;
        }
        return original;
    }
}
