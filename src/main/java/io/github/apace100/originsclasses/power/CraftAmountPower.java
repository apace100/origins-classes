package io.github.apace100.originsclasses.power;

import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.ValueModifyingPower;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class CraftAmountPower extends ValueModifyingPower {

    private final Predicate<Pair<World, ItemStack>> outputPredicate;

    public CraftAmountPower(PowerType<?> type, LivingEntity entity, Predicate<Pair<World, ItemStack>> outputPredicate) {
        super(type, entity);
        this.outputPredicate = outputPredicate;
    }

    public boolean doesApply(World world, ItemStack outputStack) {
        return outputPredicate.test(new Pair<>(world, outputStack));
    }
}
