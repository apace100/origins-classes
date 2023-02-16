package io.github.apace100.originsclasses.util;

import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;

import java.util.HashSet;
import java.util.Set;

public final class TagUtil {

    public static <T> Set<T> getAllEntries(Registry<T> registry, TagKey<T> tag) {
        Set<T> entrySet = new HashSet<>();
        for (RegistryEntry<T> entry :
            registry.iterateEntries(tag)) {
            entrySet.add(entry.value());
        }
        return entrySet;
    }
}
