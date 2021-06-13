package io.github.apace100.originsclasses.power;

import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.ValueModifyingPower;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

import java.util.function.Predicate;

public class CraftAmountPower extends ValueModifyingPower {

    private final Predicate<ItemStack> outputPredicate;

    public CraftAmountPower(PowerType<?> type, LivingEntity entity, Predicate<ItemStack> outputPredicate) {
        super(type, entity);
        this.outputPredicate = outputPredicate;
    }

    public boolean doesApply(ItemStack outputStack) {
        return outputPredicate.test(outputStack);
    }
}
