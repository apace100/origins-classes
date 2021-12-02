package io.github.apace100.originsclasses.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class StealthEffect extends StatusEffect {

    public static final StatusEffect INSTANCE = new StealthEffect(StatusEffectCategory.BENEFICIAL, 0x242424);

    protected StealthEffect(StatusEffectCategory type, int color) {
        super(type, color);
    }

}
