package io.github.apace100.originsclasses.mixin;

import io.github.apace100.originsclasses.power.ClassPowerTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(AbstractFurnaceBlockEntity.class)
public abstract class AbstractFurnaceBlockEntityMixin extends LockableContainerBlockEntity {

    private static PlayerEntity playerTakingStacks;

    protected AbstractFurnaceBlockEntityMixin(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }


    @Inject(method = "dropExperienceForRecipesUsed", at = @At("HEAD"))
    private void savePlayerForLater(ServerPlayerEntity player, CallbackInfo ci) {
        if(getType() == BlockEntityType.SMOKER) {
            playerTakingStacks = player;
        }
    }

    @Redirect(method = "method_17761", at = @At(value = "INVOKE", target = "Lnet/minecraft/recipe/AbstractCookingRecipe;getExperience()F"))
    private static float modifyExperienceGain(AbstractCookingRecipe abstractCookingRecipe) {
        float regularXp = abstractCookingRecipe.getExperience();
        if(playerTakingStacks != null) {
            if(ClassPowerTypes.MORE_SMOKER_XP.isActive(playerTakingStacks)) {
                return regularXp * 2F;
            }
        }
        return regularXp;
    }
}
