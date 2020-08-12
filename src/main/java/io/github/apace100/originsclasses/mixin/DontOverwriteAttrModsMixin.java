package io.github.apace100.originsclasses.mixin;

import com.google.common.collect.Multimap;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ItemStack.class)
public abstract class DontOverwriteAttrModsMixin {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/CompoundTag;getList(Ljava/lang/String;I)Lnet/minecraft/nbt/ListTag;", ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD, method = "getAttributeModifiers")
    private void addAttributeModifiersFromItem(EquipmentSlot slot, CallbackInfoReturnable info, Multimap multimap) {
        multimap.putAll(((ItemStack)(Object)this).getItem().getAttributeModifiers(slot));
    }
}
