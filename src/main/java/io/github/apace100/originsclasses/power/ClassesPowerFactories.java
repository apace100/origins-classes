package io.github.apace100.originsclasses.power;

import io.github.apace100.origins.Origins;
import io.github.apace100.origins.power.DamageOverTimePower;
import io.github.apace100.origins.power.PowerType;
import io.github.apace100.origins.power.VariableIntPower;
import io.github.apace100.origins.power.factory.PowerFactory;
import io.github.apace100.origins.power.factory.condition.ConditionFactory;
import io.github.apace100.origins.registry.ModDamageSources;
import io.github.apace100.origins.registry.ModRegistries;
import io.github.apace100.origins.util.SerializableData;
import io.github.apace100.origins.util.SerializableDataType;
import io.github.apace100.originsclasses.OriginsClasses;
import io.github.apace100.originsclasses.data.ClassesDataTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

import java.util.*;

public class ClassesPowerFactories {

    @SuppressWarnings("unchecked")
    public static void register() {
        register(new PowerFactory<>(new Identifier(OriginsClasses.MODID, "craft_amount"),
            new SerializableData()
                .add("item_condition", SerializableDataType.ITEM_CONDITION, null)
                .add("modifier", SerializableDataType.ATTRIBUTE_MODIFIER, null)
                .add("modifiers", SerializableDataType.ATTRIBUTE_MODIFIERS, null),
            data ->
                (type, player) -> {
                    CraftAmountPower power = new CraftAmountPower(type, player, data.isPresent("item_condition") ?
                        (ConditionFactory<ItemStack>.Instance)data.get("item_condition") : (stack -> true));
                    if(data.isPresent("modifier")) {
                        power.addModifier(data.getModifier("modifier"));
                    }
                    if(data.isPresent("modifiers")) {
                        ((List<EntityAttributeModifier>)data.get("modifiers"))
                            .forEach(power::addModifier);
                    }
                    return power;
                }));
        register(new PowerFactory<>(new Identifier(OriginsClasses.MODID, "lumberjack"),
            new SerializableData(),
            data ->
                (type, player) -> new MultiMinePower(type, player, (pl, bs, bp) -> {
                        Set<BlockPos> affected = new HashSet<>();
                        Queue<BlockPos> queue = new LinkedList<>();
                        queue.add(bp);
                        boolean foundOneWithLeaves = false;
                        BlockPos.Mutable pos = bp.mutableCopy();
                        BlockPos.Mutable newPos = bp.mutableCopy();
                        while(!queue.isEmpty()) {
                            pos.set(queue.remove());
                            for(int dx = -1; dx <= 1; dx++) {
                                for(int dy = 0; dy <= 1; dy++) {
                                    for(int dz = -1; dz <= 1; dz++) {
                                        if(dx == 0 & dy == 0 && dz == 0) {
                                            continue;
                                        }
                                        newPos.set(pos.getX() + dx, pos.getY() + dy, pos.getZ() + dz);
                                        BlockState state = pl.world.getBlockState(newPos);
                                        if(state.isOf(bs.getBlock()) && !affected.contains(newPos)) {
                                            BlockPos savedNewPos = newPos.toImmutable();
                                            affected.add(savedNewPos);
                                            queue.add(savedNewPos);
                                            if(affected.size() > 127) {
                                                if(!foundOneWithLeaves) {
                                                    return new ArrayList<>();
                                                }
                                                return new ArrayList<>(affected);
                                            }
                                        } else
                                        if(state.getBlock() instanceof LeavesBlock && !state.get(LeavesBlock.PERSISTENT)) {
                                            foundOneWithLeaves = true;
                                        }
                                    }
                                }
                            }
                        }
                        if(!foundOneWithLeaves) {
                            affected.clear();
                        }
                        return new ArrayList<>(affected);
                    }, state -> state.getBlock().isIn(BlockTags.LOGS)).addCondition(p -> p.getMainHandStack().getItem() instanceof AxeItem)
                ));
        register(new PowerFactory<>(new Identifier(OriginsClasses.MODID, "variable_int"),
            new SerializableData()
                .add("start_value", SerializableDataType.INT, null)
                .add("min", SerializableDataType.INT, Integer.MIN_VALUE)
                .add("max", SerializableDataType.INT, Integer.MAX_VALUE),
            data ->
                (type, player) -> new VariableIntPower(type, player, data.getInt("start_value"), data.getInt("min"), data.getInt("max"))));
    }

    private static void register(PowerFactory<?> factory) {
        Registry.register(ModRegistries.POWER_FACTORY, factory.getSerializerId(), factory);
    }
}
