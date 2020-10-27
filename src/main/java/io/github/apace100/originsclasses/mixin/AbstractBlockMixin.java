package io.github.apace100.originsclasses.mixin;

import io.github.apace100.origins.registry.ModComponents;
import io.github.apace100.originsclasses.ducks.SneakingStateSavingManager;
import io.github.apace100.originsclasses.networking.ModPacketsS2C;
import io.github.apace100.originsclasses.power.ClassPowerTypes;
import io.github.apace100.originsclasses.power.MultiMinePower;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.data.client.model.BlockStateVariantMap;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

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
            ModComponents.ORIGIN.get(player).getPowers(MultiMinePower.class).forEach(mmp -> {
                int affectBlockCount = mmp.getAffectedBlocks(state, pos).size();
                if(mmp.isBlockStateAffected(state) && affectBlockCount > 0) {
                    int multiplier = Math.min(affectBlockCount, finalToolDurability - 1);
                    multiplier = (int)Math.ceil((float)multiplier * 0.75F);
                    cir.setReturnValue(cir.getReturnValueF() / multiplier);
                }
            });
        }
        /*
        if(ClassPowerTypes.MORE_STONE_BREAK_SPEED.isActive(player)) {
            ItemStack tool = player.getEquippedStack(EquipmentSlot.MAINHAND);
            if(tool.getItem() instanceof PickaxeItem) {
                if(state.getMaterial() == Material.STONE) {
                    cir.setReturnValue(cir.getReturnValueF() * 1.5F);
                }
            }
        }*/
    }
}
