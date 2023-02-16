package io.github.apace100.originsclasses.util;

import io.github.apace100.originsclasses.OriginsClasses;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ClassesTags {

    public static final TagKey<Item> MERCHANT_BLACKLIST = TagKey.of(RegistryKeys.ITEM, new Identifier(OriginsClasses.MODID, "merchant_blacklist"));
}
