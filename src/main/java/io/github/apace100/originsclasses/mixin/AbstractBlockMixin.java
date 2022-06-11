package io.github.apace100.originsclasses.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.originsclasses.ducks.SneakingStateSavingManager;
import io.github.apace100.originsclasses.networking.ModPacketsS2C;
import io.github.apace100.originsclasses.power.MultiMinePower;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.class)
public class AbstractBlockMixin {

    @Inject(method = "calcBlockBreakingDelta", at = @At("RETURN"), cancellable = true)
    private void modifyMultiMinedBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos, CallbackInfoReturnable<Float> cir) {
        boolean processMultimine = false;
        if(player instanceof ServerPlayerEntity) {
            SneakingStateSavingManager sneakingState = (SneakingStateSavingManager)(Object)((ServerPlayerEntity)player).interactionManager;
            processMultimine = !sneakingState.wasSneakingWhenBlockBreakingStarted();
        } else {
            processMultimine = ModPacketsS2C.isMultiMining;
        }
        if(processMultimine) {
            ItemStack tool = player.getEquippedStack(EquipmentSlot.MAINHAND);
            int toolDurability = 128;
            if(!tool.isEmpty()) {
                toolDurability = tool.getMaxDamage() - tool.getDamage();
            }
            int finalToolDurability = toolDurability;
            PowerHolderComponent.KEY.get(player).getPowers(MultiMinePower.class).forEach(mmp -> {
                if(mmp.isBlockStateAffected(state)) {
                    int affectBlockCount = mmp.getAffectedBlocks(state, pos).size();
                    if(affectBlockCount > 0) {
                        int multiplier = Math.min(affectBlockCount, finalToolDurability - 1);
                        multiplier = (int)Math.ceil((float)multiplier * 0.75F);
                        cir.setReturnValue(cir.getReturnValueF() / multiplier);
                    }
                }
            });
        }
    }
}
