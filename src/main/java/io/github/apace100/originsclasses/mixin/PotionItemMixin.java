package io.github.apace100.originsclasses.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.LingeringPotionItem;
import net.minecraft.item.PotionItem;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin({PotionItem.class, LingeringPotionItem.class})
public class PotionItemMixin {
    
    @Inject(method = "appendTooltip", at = @At("HEAD"))
    @Environment(EnvType.CLIENT)
    private void appendExtendedTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context, CallbackInfo ci) {
        if (stack.hasNbt() && stack.getNbt().contains("IsExtendedByCleric")) {
            tooltip.add(Text.translatable("origins-classes.longer_potions").formatted(Formatting.GOLD));
        }
    }
}
