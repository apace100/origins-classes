package io.github.apace100.originsclasses.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
	
	@Shadow public abstract boolean hasTag();
	
	@Shadow public abstract CompoundTag getTag();
	
	@Inject(method = "getName", at = @At(value = "INVOKE", shift = At.Shift.BEFORE, target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"), cancellable = true)
	private void getExtendedName(CallbackInfoReturnable<Text> cir) {
		if (hasTag() && getTag().contains("OriginalName")) {
			cir.setReturnValue(Text.Serializer.fromJson(getTag().getString("OriginalName")));
		}
	}
}
