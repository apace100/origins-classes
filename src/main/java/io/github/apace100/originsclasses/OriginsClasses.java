package io.github.apace100.originsclasses;

import io.github.apace100.apoli.util.IdentifierAlias;
import io.github.apace100.originsclasses.effect.StealthEffect;
import io.github.apace100.originsclasses.power.ClassesPowerFactories;
import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class OriginsClasses implements ModInitializer {

	public static final String MODID = "origins-classes";

	// Mixin Save States :) Very useful, not hacky :)
	public static boolean isClericEnchanting;

	@Override
	public void onInitialize() {
		IdentifierAlias.addNamespaceAlias(MODID, "apoli");
		ClassesPowerFactories.register();
		Registry.register(Registries.STATUS_EFFECT, new Identifier(MODID, "stealth"), StealthEffect.INSTANCE);
	}
}
