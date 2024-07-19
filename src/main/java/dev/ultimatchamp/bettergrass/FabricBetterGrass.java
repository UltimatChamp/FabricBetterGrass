package dev.ultimatchamp.bettergrass;

import dev.ultimatchamp.bettergrass.config.FabricBetterGrassConfig;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FabricBetterGrass implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("YetAnotherConfigLib");

    @Override
    public void onInitializeClient() {
        FabricBetterGrassConfig.load();
        if (FabricBetterGrassConfig.instance().betterGrassMode == FabricBetterGrassConfig.BetterGrassMode.OFF)
            LOGGER.info("[FabricBetterGrass] Better Grass is disabled.");
        else
            LOGGER.info("[FabricBetterGrass] [" + FabricBetterGrassConfig.instance().betterGrassMode.toString() + "] Gamers can finally touch grass!?");
    }
}
