package dev.ultimatchamp.bettergrass;

import dev.ultimatchamp.bettergrass.config.FabricBetterGrassConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;

public class FabricBetterGrass implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FabricBetterGrassConfig.load();
        Log.info(LogCategory.LOG, "Gamers can finally touch grass!?");
    }
}
