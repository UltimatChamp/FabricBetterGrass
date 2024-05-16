package dev.ultimatchamp.bettergrass;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;

import java.util.List;

public class FabricBetterGrass implements ClientModInitializer {

    public static class FabricBetterGrassConfig extends MidnightConfig {
        @Entry(category = "config") public static BetterGrassMode betterGrassMode = BetterGrassMode.FANCY;
        public enum BetterGrassMode {
            FANCY, FAST, OFF
        }

        @Entry(category = "advanced") public static List<String> blockstates =
                List.of("minecraft:grass_block",
                        "minecraft:podzol",
                        "minecraft:mycelium",
                        "minecraft:crimson_nylium",
                        "minecraft:warped_nylium",
                        "conquest:clover_covered_grass",
                        "conquest:taiga_grass");

        @Entry(category = "advanced") public static List<String> tiles =
                List.of("minecraft:grass_block",
                        "minecraft:podzol",
                        "minecraft:mycelium",
                        "minecraft:crimson_nylium",
                        "minecraft:warped_nylium",
                        "conquest:clover_covered_grass",
                        "conquest:taiga_grass");
    }

    @Override
    public void onInitializeClient() {
        Log.info(LogCategory.LOG, "Gamers can finally touch grass!?");
        MidnightConfig.init("bettergrass", FabricBetterGrassConfig.class);
    }
}
