package io.github.apace100.originsclasses.power;

import io.github.apace100.origins.power.Power;
import io.github.apace100.origins.power.PowerType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.LinkedList;
import java.util.List;

public class StartingEquipmentPower extends Power {

    private final List<ItemStack> itemStacks = new LinkedList<>();

    public StartingEquipmentPower(PowerType<?> type, PlayerEntity player) {
        super(type, player);
    }

    public StartingEquipmentPower addStack(ItemStack stack) {
        this.itemStacks.add(stack);
        return this;
    }

    @Override
    public void onChosen(boolean isOrbOfOrigin) {
        if(!isOrbOfOrigin) {
            itemStacks.forEach(is -> {
                ItemStack copy = is.copy();
                player.giveItemStack(copy);
            });
        }
    }
}
