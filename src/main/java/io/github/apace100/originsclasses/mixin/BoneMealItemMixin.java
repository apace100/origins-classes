package io.github.apace100.originsclasses.mixin;

import io.github.apace100.originsclasses.power.ClassPowerTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BoneMealItem.class)
public class BoneMealItemMixin {

    private static boolean isFarmer = false;

    @Inject(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/BlockPos;offset(Lnet/minecraft/util/math/Direction;)Lnet/minecraft/util/math/BlockPos;", shift = At.Shift.AFTER))
    private void saveFarmerForLater(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        if(context.getPlayer() != null && ClassPowerTypes.BETTER_BONE_MEAL.isActive(context.getPlayer())) {
            isFarmer = true;
        }
    }

    @Inject(method = "useOnBlock", at = @At("RETURN"))
    private void removeSavedFarmer(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        isFarmer = false;
    }

    @Inject(method = "useOnFertilizable", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;decrement(I)V"))
    private static void applyAdditionalFarmerBoneMeal(ItemStack stack, World world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if(isFarmer) {
            BlockState blockState = world.getBlockState(pos);
            Fertilizable fertilizable = (Fertilizable)blockState.getBlock();
            if (fertilizable.canGrow(world, world.random, pos, blockState)) {
                fertilizable.grow((ServerWorld)world, world.random, pos, blockState);
            }
        }
    }
}
