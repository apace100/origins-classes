package io.github.apace100.originsclasses.power;

import io.github.apace100.origins.power.Power;
import io.github.apace100.origins.power.PowerType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class StartingEquipmentPower extends Power {

    private final List<ItemStack> itemStacks = new LinkedList<>();
    private final HashMap<Integer, ItemStack> slottedStacks = new HashMap<>();

    public StartingEquipmentPower(PowerType<?> type, PlayerEntity player) {
        super(type, player);
    }

    public void addStack(ItemStack stack) {
        this.itemStacks.add(stack);
    }

    public void addStack(int slot, ItemStack stack) {
        slottedStacks.put(slot, stack);
    }

    @Override
    public void onChosen(boolean isOrbOfOrigin) {
        if(!isOrbOfOrigin) {
            slottedStacks.forEach((slot, stack) -> {
                if(player.inventory.getStack(slot).isEmpty()) {
                    player.inventory.setStack(slot, stack);
                } else {
                    player.giveItemStack(stack);
                }
            });
            itemStacks.forEach(is -> {
                ItemStack copy = is.copy();
                player.giveItemStack(copy);
            });
        }
    }
}
