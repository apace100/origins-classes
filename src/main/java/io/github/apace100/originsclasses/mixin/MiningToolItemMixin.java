package io.github.apace100.originsclasses.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MiningToolItem.class)
public class MiningToolItemMixin {

    @Shadow @Final protected float miningSpeed;

    @Redirect(method = "getMiningSpeedMultiplier", at = @At(value = "FIELD", target = "Lnet/minecraft/item/MiningToolItem;miningSpeed:F", opcode = org.objectweb.asm.Opcodes.GETFIELD, ordinal = 0))
    private float applyMiningSpeedMultiplierMultiplier(MiningToolItem item, ItemStack stack, BlockState blockState) {
        if(stack != null) {
            if(stack.hasTag() && stack.getTag().contains("MiningSpeedMultiplier")) {
                return miningSpeed * stack.getTag().getFloat("MiningSpeedMultiplier");
            }
        }
        return miningSpeed;
    }
}
