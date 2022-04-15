package io.github.apace100.originsclasses.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.block.BlockState;
import net.minecraft.data.client.BlockStateVariantMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.function.Predicate;

public class MultiMinePower extends Power {

    private final BlockStateVariantMap.TriFunction<LivingEntity, BlockState, BlockPos, List<BlockPos>> affectedBlocksFunction;
    private final Predicate<BlockState> isBlockStateAffected;

    public MultiMinePower(PowerType<?> type, LivingEntity entity, BlockStateVariantMap.TriFunction<LivingEntity, BlockState, BlockPos, List<BlockPos>> affectedBlocksFuntion, Predicate<BlockState> isBlockStateAffected) {
        super(type, entity);
        this.affectedBlocksFunction = affectedBlocksFuntion;
        this.isBlockStateAffected = isBlockStateAffected;
    }

    public boolean isBlockStateAffected(BlockState state) {
        return isBlockStateAffected.test(state);
    }

    public List<BlockPos> getAffectedBlocks(BlockState state, BlockPos position) {
        return affectedBlocksFunction.apply(entity, state, position);
    }
}
