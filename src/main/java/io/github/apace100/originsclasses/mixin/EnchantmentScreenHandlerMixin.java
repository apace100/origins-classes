package io.github.apace100.originsclasses.mixin;

import io.github.apace100.originsclasses.OriginsClasses;
import io.github.apace100.originsclasses.power.ClassPowerTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentScreenHandler.class)
public class EnchantmentScreenHandlerMixin {

    private PlayerEntity enchanter;

    @Inject(method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/screen/ScreenHandlerContext;)V", at = @At("TAIL"))
    private void saveEnchanterInHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context, CallbackInfo ci) {
        this.enchanter = playerInventory.player;
    }

    @Inject(method = "method_17411", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/EnchantmentScreenHandler;generateEnchantments(Lnet/minecraft/item/ItemStack;II)Ljava/util/List;"))
    private void saveEnchanterForPreview(ItemStack stack, World world, BlockPos pos, CallbackInfo ci) {
        OriginsClasses.isClericEnchanting = ClassPowerTypes.BETTER_ENCHANTING.isActive(enchanter);
    }

    @Inject(method = "method_17410", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/EnchantmentScreenHandler;generateEnchantments(Lnet/minecraft/item/ItemStack;II)Ljava/util/List;"))
    private void saveEnchanter(ItemStack itemStack, int id, PlayerEntity playerEntity, int level, ItemStack stack2, World world, BlockPos pos, CallbackInfo ci) {
        OriginsClasses.isClericEnchanting = ClassPowerTypes.BETTER_ENCHANTING.isActive(playerEntity);
    }
}
