package io.github.apace100.originsclasses.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Item.class)
public abstract class ItemMixin {

    @Shadow public abstract boolean isFood();

    @Inject(method = "appendTooltip", at = @At("HEAD"))
    @Environment(EnvType.CLIENT)
    private void appendFoodBonusInfo(ItemStack stack, World world, List<Text> tooltip, TooltipContext context, CallbackInfo ci) {
        if(stack != null) {
            NbtCompound tag = stack.hasNbt() ? stack.getNbt() : null;
            if(tag != null) {
                if(tag.contains("FoodBonus")) {
                    int bonus = tag.getInt("FoodBonus");
                    tooltip.add(Text.translatable("origins-classes.food_bonus", bonus).formatted(Formatting.GRAY));
                }
                if(tag.contains("MiningSpeedMultiplier")) {
                    int bonusInt = Math.round((tag.getFloat("MiningSpeedMultiplier") - 1F) * 100);
                    String bonus = bonusInt > 0 ? ("+" + bonusInt + "%") : (bonusInt + "%");
                    tooltip.add(Text.translatable("origins-classes.mining_speed_bonus", bonus).formatted(Formatting.BLUE));
                }
            }
        }
    }
}
