package dev.ultimatchamp.bettergrass;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;

import java.util.List;

public class FabricBetterGrass implements ClientModInitializer {

    public static class FabricBetterGrassConfig extends MidnightConfig {
        @Entry
        public static BetterGrassMode betterGrassMode = BetterGrassMode.FANCY;

        public enum BetterGrassMode {
            OFF, FAST, FANCY
        }

        @Entry
        public static List<String> blockstates =
                List.of("minecraft:grass_block",
                        "minecraft:podzol",
                        "minecraft:mycelium",
                        "minecraft:crimson_nylium",
                        "minecraft:warped_nylium");
    }

    @Override
    public void onInitializeClient() {
        Log.info(LogCategory.LOG, "Gamers can finally touch grass!?");
        MidnightConfig.init("bettergrass", FabricBetterGrassConfig.class);
    }
}
