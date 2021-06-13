package io.github.apace100.originsclasses.util;

import com.google.common.collect.Sets;
import io.github.apace100.originsclasses.mixin.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.CombinedEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.entry.TagEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

import java.util.*;

public class ItemUtil {

    private static final Set<Item> OBTAINABLE = new HashSet<>();
    private static Item[] OBTAINABLE_ARRAY;
    private static boolean isObtainableSetBuilt = false;

    public static ItemStack createMerchantItemStack(Item item, Random random) {
        ItemStack stack = new ItemStack(item);
        if(item.isEnchantable(stack) && random.nextFloat() < 0.5) {
            EnchantmentHelper.enchant(random, stack, 1 + random.nextInt(30), random.nextBoolean());
        }
        return stack;
    }

    public static boolean isObtainable(MinecraftServer server, Item item) {
        buildObtainableSet(server);
        return OBTAINABLE.contains(item);
    }

    public static Item getRandomObtainableItem(MinecraftServer server, Random random, Set<Item> exclude) {
        buildObtainableSet(server);
        if(exclude == null || exclude.isEmpty()) {
            return OBTAINABLE_ARRAY[random.nextInt(OBTAINABLE_ARRAY.length)];
        } else {
            Set<Item> possibles = Sets.difference(OBTAINABLE, exclude);
            Item[] items = new Item[possibles.size()];
            items = possibles.toArray(items);
            return items[random.nextInt(items.length)];
        }
    }

    public static void buildObtainableSet(MinecraftServer server) {
        if(isObtainableSetBuilt) {
            return;
        }

        LootManager manager = server.getLootManager();
        Set<Identifier> lootTables = manager.getTableIds();
        LootTableAccessor table;
        for(Identifier id : lootTables) {
            table = (LootTableAccessor)manager.getTable(id);
            LootPool[] pools = table.getPools();
            Queue<LootPoolEntry> entryQueue = new LinkedList<>();
            for (LootPool pool : pools) {
                LootPoolEntry[] entries = ((LootPoolAccessor) pool).getEntries();
                entryQueue.addAll(Arrays.asList(entries));
            }
            while(!entryQueue.isEmpty()) {
                LootPoolEntry entry = entryQueue.remove();
                if(entry instanceof ItemEntry) {
                    OBTAINABLE.add(((ItemEntryAccessor)entry).getItem());
                } else if(entry instanceof TagEntry) {
                    OBTAINABLE.addAll(((TagEntryAccessor)entry).getName().values());
                } else if(entry instanceof CombinedEntry) {
                    entryQueue.addAll(Arrays.asList(((CombinedEntryAccessor)entry).getChildren()));
                }
            }
        }

        OBTAINABLE_ARRAY = new Item[OBTAINABLE.size()];
        OBTAINABLE_ARRAY = OBTAINABLE.toArray(OBTAINABLE_ARRAY);
        isObtainableSetBuilt = true;
    }
}
