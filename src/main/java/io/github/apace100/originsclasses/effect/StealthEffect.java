package io.github.apace100.originsclasses.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;

public class StealthEffect extends StatusEffect {

    public static final StatusEffect INSTANCE = new StealthEffect(StatusEffectType.BENEFICIAL, 0x242424);

    protected StealthEffect(StatusEffectType type, int color) {
        super(type, color);
    }

}
