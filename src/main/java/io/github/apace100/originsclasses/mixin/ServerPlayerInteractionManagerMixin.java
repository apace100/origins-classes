package io.github.apace100.originsclasses.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.origins.Origins;
import io.github.apace100.originsclasses.ducks.SneakingStateSavingManager;
import io.github.apace100.originsclasses.networking.ModPackets;
import io.github.apace100.originsclasses.power.MultiMinePower;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerInteractionManager.class)
public abstract class ServerPlayerInteractionManagerMixin implements SneakingStateSavingManager {

    @Shadow public ServerWorld world;
    @Shadow public ServerPlayerEntity player;

    @Shadow private int blockBreakingProgress;
    @Shadow private int startMiningTime;

    @Shadow public abstract void finishMining(BlockPos pos, int sequence, String reason);

    @Shadow protected abstract void method_41250(BlockPos pos, boolean success, int sequence, String reason);

    @Shadow public abstract boolean tryBreakBlock(BlockPos pos);

    private BlockState justMinedBlockState;
    private boolean performingMultiMine = false;
    private boolean wasSneakingWhenStarted = false;

    @Inject(method = "processBlockBreakingAction", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;onBlockBreakStart(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/player/PlayerEntity;)V", ordinal = 0))
    private void saveSneakingState(BlockPos pos, PlayerActionC2SPacket.Action action, Direction direction, int worldHeight, int sequence, CallbackInfo ci) {
        wasSneakingWhenStarted = player.isSneaking();
        PacketByteBuf data = new PacketByteBuf(Unpooled.buffer());
        data.writeBoolean(!wasSneakingWhenStarted);
        ServerPlayNetworking.send(player, ModPackets.MULTI_MINING, data);
    }

    @Inject(method = "finishMining", at = @At("HEAD"))
    private void saveBlockStateForMultiMine(BlockPos pos, int sequence, String reason, CallbackInfo ci) {
        justMinedBlockState = world.getBlockState(pos);
    }

    @Inject(method = "finishMining", at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/server/network/ServerPlayerInteractionManager;method_41250(Lnet/minecraft/util/math/BlockPos;ZILjava/lang/String;)V"))
    private void multiMinePower(BlockPos pos, int sequence, String reason, CallbackInfo ci) {
        if(!wasSneakingWhenStarted && !performingMultiMine) {
            performingMultiMine = true;
            PowerHolderComponent.KEY.get(player).getPowers(MultiMinePower.class).forEach(mmp -> {
                if(mmp.isBlockStateAffected(justMinedBlockState)) {
                    ItemStack tool = player.getMainHandStack().copy();
                    for(BlockPos bp : mmp.getAffectedBlocks(justMinedBlockState, pos)) {
                        finishMining(bp, sequence, reason);
                        if(!ItemStack.areItemsEqual(player.getMainHandStack(), tool)) {
                            break;
                        }
                    }
                }
            });
            performingMultiMine = false;
        }
    }

    public boolean wasSneakingWhenBlockBreakingStarted() {
        return wasSneakingWhenStarted;
    }
}
