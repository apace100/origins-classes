package io.github.apace100.originsclasses.mixin;

import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net/minecraft/screen/BrewingStandScreenHandler$PotionSlot")
public class BrewingStandPotionSlotMixin {

    @Inject(method = { "matches", "method_7631" }, at = @At("HEAD"), cancellable = true)
    private static void preventBrewingExtendedPotions(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if(stack.getOrCreateNbt().getBoolean("IsExtendedByCleric")) {
            cir.setReturnValue(false);
        }
    }
}
