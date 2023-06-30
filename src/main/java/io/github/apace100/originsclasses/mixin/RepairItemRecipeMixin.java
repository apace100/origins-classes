package io.github.apace100.originsclasses.mixin;

import io.github.apace100.originsclasses.power.ClassPowerTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.recipe.RepairItemRecipe;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(RepairItemRecipe.class)
public class RepairItemRecipeMixin {

    @ModifyConstant(method = "craft(Lnet/minecraft/inventory/RecipeInputInventory;Lnet/minecraft/registry/DynamicRegistryManager;)Lnet/minecraft/item/ItemStack;", constant = @Constant(intValue = 5, ordinal = 0))
    private int doubleRepairDurabilityBonus(int original, RecipeInputInventory inventory, DynamicRegistryManager dynamicRegistryManager) {
        ScreenHandler handler = ((CraftingInventoryAccessor)inventory).getHandler();
        PlayerEntity player = null;
        if(handler instanceof CraftingScreenHandler) {
            player = ((CraftingScreenHandlerAccessor)handler).getPlayer();
        } else if(handler instanceof PlayerScreenHandler) {
            player = ((PlayerScreenHandlerAccessor)handler).getOwner();
        }
        if(player != null && ClassPowerTypes.EFFICIENT_REPAIRS.isActive(player)) {
            return original * 3;
        }
        return original;
    }
}
