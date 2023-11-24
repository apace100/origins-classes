package io.github.apace100.originsclasses.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.calio.Calio;
import io.github.apace100.origins.component.OriginComponent;
import io.github.apace100.originsclasses.OriginsClasses;
import io.github.apace100.originsclasses.power.ClassPowerTypes;
import io.github.apace100.originsclasses.power.CraftAmountPower;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.*;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RepairItemRecipe;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Optional;

@Mixin(CraftingScreenHandler.class)
public class CraftingScreenHandlerMixin {

    @Unique
    private static Optional<CraftingRecipe> classes$CachedRecipe;

    @Inject(method = "updateResult", at = @At(value = "INVOKE", target = "Ljava/util/Optional;isPresent()Z"), locals = LocalCapture.CAPTURE_FAILHARD)
    private static void cacheRecipe(ScreenHandler handler, World world, PlayerEntity player, RecipeInputInventory craftingInventory, CraftingResultInventory resultInventory, CallbackInfo ci, ServerPlayerEntity serverPlayerEntity, ItemStack itemStack, Optional optional) {
        classes$CachedRecipe = optional;
    }

    @Inject(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/CraftingResultInventory;setStack(ILnet/minecraft/item/ItemStack;)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    private static void modifyCraftingResult(ScreenHandler handler, World world, PlayerEntity player, RecipeInputInventory craftingInventory, CraftingResultInventory resultInventory, CallbackInfo ci, ServerPlayerEntity serverPlayerEntity, ItemStack itemStack) {
        if(itemStack.getItem().isFood() && ClassPowerTypes.BETTER_CRAFTED_FOOD.isActive(player)) {
            FoodComponent food = itemStack.getItem().getFoodComponent();
            int foodBonus = (int)Math.ceil((float)food.getHunger() / 3F);
            if(foodBonus < 1) {
                foodBonus = 1;
            }
            itemStack.getOrCreateNbt().putInt("FoodBonus", foodBonus);
        }
        if(ClassPowerTypes.QUALITY_EQUIPMENT.isActive(player) && isEquipment(itemStack)) {
            boolean recipeContainsEquipment = false;
            for(int i = 0; i < craftingInventory.size() && !recipeContainsEquipment; i++) {
                if(isEquipment(craftingInventory.getStack(i))) {
                    recipeContainsEquipment = true;
                }
            }
            if(classes$CachedRecipe.isPresent() && classes$CachedRecipe.get() instanceof RepairItemRecipe) {
                recipeContainsEquipment = false;
            }
            if(!recipeContainsEquipment) {
                addQualityAttribute(itemStack);
            }
        }
        int baseValue = itemStack.getCount();
        int newValue = (int) PowerHolderComponent.modify(player, CraftAmountPower.class, baseValue, (p -> p.doesApply(world, itemStack)));
        if(newValue != baseValue) {
            itemStack.setCount(newValue < 0 ? 0 : Math.min(newValue, itemStack.getMaxCount()));
        }
    }

    private static void addQualityAttribute(ItemStack stack) {
        Item item = stack.getItem();
        if(item instanceof ArmorItem) {
            EquipmentSlot slot = ((ArmorItem)item).getSlotType();
            stack.addAttributeModifier(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, new EntityAttributeModifier("Blacksmith quality", 0.25D, EntityAttributeModifier.Operation.ADDITION), slot);
            Calio.setEntityAttributesAdditional(stack, true);
        } else if(item instanceof SwordItem || item instanceof RangedWeaponItem) {
            stack.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier("Blacksmith quality", 0.5D, EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.MAINHAND);
            Calio.setEntityAttributesAdditional(stack, true);
        } else if(item instanceof MiningToolItem || item instanceof ShearsItem) {
            stack.getOrCreateNbt().putFloat("MiningSpeedMultiplier", 1.05F);
        } else if(item instanceof ShieldItem) {
            stack.addAttributeModifier(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, new EntityAttributeModifier("Blacksmith quality", 0.1D, EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.OFFHAND);
            Calio.setEntityAttributesAdditional(stack, true);
        }
    }

    private static boolean isEquipment(ItemStack stack) {
        if(stack == null || stack.isEmpty()) {
            return false;
        }
        Item item = stack.getItem();
        if(item instanceof ArmorItem)
            return true;
        if(item instanceof ToolItem)
            return true;
        if(item instanceof RangedWeaponItem)
            return true;
        if(item instanceof ShieldItem)
            return true;
        return false;
    }
}
