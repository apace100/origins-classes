package io.github.apace100.originsclasses.util;

import io.github.apace100.calio.util.IdentifiedTag;
import io.github.apace100.originsclasses.OriginsClasses;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ClassesTags {

    public static final Tag<Item> MERCHANT_BLACKLIST = new IdentifiedTag<>(Registry.ITEM_KEY, new Identifier(OriginsClasses.MODID, "merchant_blacklist"));
}
