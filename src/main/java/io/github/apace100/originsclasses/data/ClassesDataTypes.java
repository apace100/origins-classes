package io.github.apace100.originsclasses.data;

import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.apace100.origins.util.ClassUtil;
import io.github.apace100.origins.util.SerializableData;
import io.github.apace100.origins.util.SerializableDataType;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.util.Pair;

import java.util.HashMap;
import java.util.List;

public class ClassesDataTypes {

    private static final HashMap<String, Material> MATERIAL_MAP;
    static {
        MATERIAL_MAP = new HashMap<>();
        MATERIAL_MAP.put("air", Material.AIR);
        MATERIAL_MAP.put("plant", Material.PLANT);
        MATERIAL_MAP.put("organic_product", Material.ORGANIC_PRODUCT);
        MATERIAL_MAP.put("soil", Material.SOIL);
        MATERIAL_MAP.put("solid_organic", Material.SOLID_ORGANIC);
        MATERIAL_MAP.put("wood", Material.WOOD);
        MATERIAL_MAP.put("wool", Material.WOOL);
        MATERIAL_MAP.put("leaves", Material.LEAVES);
        MATERIAL_MAP.put("glass", Material.GLASS);
        MATERIAL_MAP.put("stone", Material.STONE);
        MATERIAL_MAP.put("metal", Material.METAL);
    }

    public static final SerializableDataType<Material> MATERIAL = SerializableDataType.mapped(Material.class, HashBiMap.create(
        MATERIAL_MAP
    ));
}
