package io.github.apace100.originsclasses.power;

import io.github.apace100.origins.power.Power;
import io.github.apace100.origins.power.PowerType;
import net.minecraft.block.BlockState;
import net.minecraft.data.client.model.BlockStateVariantMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class MultiMinePower extends Power {

    private final BlockStateVariantMap.TriFunction<PlayerEntity, BlockState, BlockPos, List<BlockPos>> affectedBlocksFunction;
    private final Predicate<BlockState> isBlockStateAffected;

    public MultiMinePower(PowerType<?> type, PlayerEntity player, BlockStateVariantMap.TriFunction<PlayerEntity, BlockState, BlockPos, List<BlockPos>> affectedBlocksFuntion, Predicate<BlockState> isBlockStateAffected) {
        super(type, player);
        this.affectedBlocksFunction = affectedBlocksFuntion;
        this.isBlockStateAffected = isBlockStateAffected;
    }

    public boolean isBlockStateAffected(BlockState state) {
        return isBlockStateAffected.test(state);
    }

    public List<BlockPos> getAffectedBlocks(BlockState state, BlockPos position) {
        return affectedBlocksFunction.apply(player, state, position);
    }
}
