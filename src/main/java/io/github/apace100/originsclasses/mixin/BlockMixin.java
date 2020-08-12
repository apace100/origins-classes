package io.github.apace100.originsclasses.mixin;

import io.github.apace100.originsclasses.power.ClassPowerTypes;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

import static net.minecraft.block.Block.dropStacks;

@Mixin(Block.class)
public class BlockMixin {

    @Inject(method = "afterBreak", at = @At("TAIL"))
    private void dropAdditionalCrops(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack stack, CallbackInfo ci) {
        if(state.getBlock() instanceof CropBlock || state.getBlock() instanceof MelonBlock) {
            if(player != null && ClassPowerTypes.MORE_CROP_DROPS.isActive(player) && new Random().nextInt(10) < 3) {
                dropStacks(state, world, pos, blockEntity, player, stack);
            }
        }
    }

    @Redirect(method = "afterBreak", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;addExhaustion(F)V"))
    private void preventBlockMiningExhaustion(PlayerEntity playerEntity, float exhaustion) {
        if(!ClassPowerTypes.NO_MINING_EXHAUSTION.isActive(playerEntity)) {
            playerEntity.addExhaustion(exhaustion);
        }
    }
}
