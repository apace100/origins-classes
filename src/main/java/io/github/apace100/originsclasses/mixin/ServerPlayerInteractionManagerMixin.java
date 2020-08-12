package io.github.apace100.originsclasses.mixin;

import io.github.apace100.origins.registry.ModComponents;
import io.github.apace100.originsclasses.ducks.SneakingStateSavingManager;
import io.github.apace100.originsclasses.networking.ModPackets;
import io.github.apace100.originsclasses.power.MultiMinePower;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
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
    @Shadow public abstract void finishMining(BlockPos pos, PlayerActionC2SPacket.Action action, String reason);

    @Shadow private int blockBreakingProgress;
    @Shadow private int startMiningTime;
    private BlockState justMinedBlockState;
    private boolean performingMultiMine = false;
    private boolean wasSneakingWhenStarted = false;

    @Inject(method = "processBlockBreakingAction", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;onBlockBreakStart(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/player/PlayerEntity;)V", ordinal = 0))
    private void saveSneakingState(BlockPos pos, PlayerActionC2SPacket.Action action, Direction direction, int worldHeight, CallbackInfo ci) {
        wasSneakingWhenStarted = player.isSneaking();
        PacketByteBuf data = new PacketByteBuf(Unpooled.buffer());
        data.writeBoolean(!wasSneakingWhenStarted);
        ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, ModPackets.MULTI_MINING, data);
    }

    @Inject(method = "finishMining", at = @At("HEAD"))
    private void saveBlockStateForMultiMine(BlockPos pos, PlayerActionC2SPacket.Action action, String reason, CallbackInfo ci) {
        justMinedBlockState = world.getBlockState(pos);
    }

    @Inject(method = "finishMining", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;sendPacket(Lnet/minecraft/network/Packet;)V", ordinal = 0))
    private void multiMinePower(BlockPos pos, PlayerActionC2SPacket.Action action, String reason, CallbackInfo ci) {
        if(!wasSneakingWhenStarted && !performingMultiMine) {
            performingMultiMine = true;
            ModComponents.ORIGIN.get(player).getPowers(MultiMinePower.class).forEach(mmp -> {
                if(mmp.isBlockStateAffected(justMinedBlockState)) {
                    ItemStack tool = player.getMainHandStack().copy();
                    for(BlockPos bp : mmp.getAffectedBlocks(justMinedBlockState, pos)) {
                        finishMining(bp, action, reason);
                        if(!player.getMainHandStack().isItemEqualIgnoreDamage(tool)) {
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
