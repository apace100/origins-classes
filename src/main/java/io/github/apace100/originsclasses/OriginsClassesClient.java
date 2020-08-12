package io.github.apace100.originsclasses;

import io.github.apace100.originsclasses.networking.ModPacketsS2C;
import net.fabricmc.api.ClientModInitializer;

public class OriginsClassesClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModPacketsS2C.register();
    }
}
