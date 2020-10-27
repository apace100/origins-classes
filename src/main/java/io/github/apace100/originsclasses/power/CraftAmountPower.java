package io.github.apace100.originsclasses.power;

import io.github.apace100.origins.power.PowerType;
import io.github.apace100.origins.power.ValueModifyingPower;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.function.Predicate;

public class CraftAmountPower extends ValueModifyingPower {

    private final Predicate<ItemStack> outputPredicate;

    public CraftAmountPower(PowerType<?> type, PlayerEntity player, Predicate<ItemStack> outputPredicate) {
        super(type, player);
        this.outputPredicate = outputPredicate;
    }

    public boolean doesApply(ItemStack outputStack) {
        return outputPredicate.test(outputStack);
    }
}
