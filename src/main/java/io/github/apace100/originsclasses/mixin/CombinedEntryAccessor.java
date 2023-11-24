package io.github.apace100.originsclasses.mixin;

import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.CombinedEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(CombinedEntry.class)
public interface CombinedEntryAccessor {

    @Accessor
    List<LootPoolEntry> getChildren();
}
