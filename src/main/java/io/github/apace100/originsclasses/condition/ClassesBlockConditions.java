package io.github.apace100.originsclasses.condition;

import io.github.apace100.origins.Origins;
import io.github.apace100.origins.power.factory.condition.ConditionFactory;
import io.github.apace100.origins.registry.ModRegistries;
import io.github.apace100.origins.util.SerializableData;
import io.github.apace100.origins.util.SerializableDataType;
import io.github.apace100.originsclasses.OriginsClasses;
import io.github.apace100.originsclasses.data.ClassesDataTypes;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

import java.util.List;

public class ClassesBlockConditions {

    @SuppressWarnings("unchecked")
    public static void register() {
        register(new ConditionFactory<>(new Identifier(OriginsClasses.MODID, "material"), new SerializableData()
            .add("material", ClassesDataTypes.MATERIAL),
            (data, block) -> block.getBlockState().getMaterial() == data.get("material")));
    }

    private static void register(ConditionFactory<CachedBlockPosition> conditionFactory) {
        Registry.register(ModRegistries.BLOCK_CONDITION, conditionFactory.getSerializerId(), conditionFactory);
    }
}
