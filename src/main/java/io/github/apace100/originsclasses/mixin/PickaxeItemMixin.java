package io.github.apace100.originsclasses.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Set;

@Mixin(PickaxeItem.class)
public abstract class PickaxeItemMixin extends MiningToolItem {

    protected PickaxeItemMixin(float attackDamage, float attackSpeed, ToolMaterial material, Set<Block> effectiveBlocks, Settings settings) {
        super(attackDamage, attackSpeed, material, effectiveBlocks, settings);
    }

    @Redirect(method = "getMiningSpeedMultiplier", at = @At(value = "FIELD", target = "Lnet/minecraft/item/PickaxeItem;miningSpeed:F", opcode = org.objectweb.asm.Opcodes.GETFIELD, ordinal = 0))
    private float applyMiningSpeedMultiplierMultiplier(PickaxeItem item, ItemStack stack, BlockState blockState) {
        if(stack.hasTag() && stack.getTag().contains("MiningSpeedMultiplier")) {
            return miningSpeed * stack.getTag().getFloat("MiningSpeedMultiplier");
        }
        return miningSpeed;
    }
}
