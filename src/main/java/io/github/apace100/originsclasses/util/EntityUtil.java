package io.github.apace100.originsclasses.util;

import io.github.apace100.originsclasses.OriginsClasses;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;

public final class EntityUtil {

    public static void addBeastmasterAttributes(LivingEntity entity) {
        if(entity.getAttributes().hasAttribute(EntityAttributes.GENERIC_MAX_HEALTH)) {
            entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).addPersistentModifier(new EntityAttributeModifier("Beastmaster boost", 0.3, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
        }
        if(entity.getAttributes().hasAttribute(EntityAttributes.GENERIC_ATTACK_DAMAGE)) {
            entity.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).addPersistentModifier(new EntityAttributeModifier("Beastmaster boost", 1.5, EntityAttributeModifier.Operation.ADDITION));
        }
        System.out.println("Added BEASTMASTER attributes to: " + entity.getEntityName());
    }
}
