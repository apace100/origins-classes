package io.github.apace100.originsclasses.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.PlayerScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PlayerScreenHandler.class)
public interface PlayerScreenHandlerAccessor {

    @Accessor
    PlayerEntity getOwner();
}
