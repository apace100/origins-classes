package io.github.apace100.originsclasses.util;

import io.github.apace100.originsclasses.OriginsClasses;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ClassesTags {

    public static final TagKey<Item> MERCHANT_BLACKLIST = TagKey.of(Registry.ITEM_KEY, new Identifier(OriginsClasses.MODID, "merchant_blacklist"));
}
