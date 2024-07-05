package dev.ultimatchamp.bettergrass;

import dev.ultimatchamp.bettergrass.config.FabricBetterGrassConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;

public class FabricBetterGrass implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FabricBetterGrassConfig.load();
        if (FabricBetterGrassConfig.instance().betterGrassMode == FabricBetterGrassConfig.BetterGrassMode.OFF)
            Log.info(LogCategory.LOG, "[FabricBetterGrass] Better Grass is disabled.");
        else
            Log.info(LogCategory.LOG, "[FabricBetterGrass] [" + FabricBetterGrassConfig.instance().betterGrassMode.toString() + "] Gamers can finally touch grass!?");
    }
}
