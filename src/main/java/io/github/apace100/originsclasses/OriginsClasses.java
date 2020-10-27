package io.github.apace100.originsclasses;

import io.github.apace100.origins.registry.ModRegistries;
import io.github.apace100.originsclasses.condition.ClassesBlockConditions;
import io.github.apace100.originsclasses.effect.StealthEffect;
import io.github.apace100.originsclasses.power.ClassPowerTypes;
import io.github.apace100.originsclasses.power.ClassesPowerFactories;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class OriginsClasses implements ModInitializer {

	public static final String MODID = "origins-classes";

	// Mixin Save States :) Very useful, not hacky :)
	public static boolean isClericEnchanting;

	@Override
	public void onInitialize() {
		ClassesPowerFactories.register();
		//ClassPowerTypes.register();
		ClassesBlockConditions.register();
		Registry.register(Registry.STATUS_EFFECT, new Identifier(MODID, "stealth"), StealthEffect.INSTANCE);
	}
}
