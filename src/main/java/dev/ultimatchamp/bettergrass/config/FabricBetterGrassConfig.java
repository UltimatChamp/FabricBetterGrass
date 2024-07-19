package dev.ultimatchamp.bettergrass.config;

import com.google.common.collect.Lists;
import dev.isxander.yacl3.api.NameableEnum;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.List;

public class FabricBetterGrassConfig {
    private static final ConfigClassHandler<FabricBetterGrassConfig> GSON = ConfigClassHandler.createBuilder(FabricBetterGrassConfig.class)
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("bettergrass.json5"))
                    .setJson5(true)
                    .build())
            .build();

    @SerialEntry(comment = "General\nOFF/FAST/FANCY (default: FANCY)")
    public BetterGrassMode betterGrassMode = BetterGrassMode.FANCY;

    public static enum BetterGrassMode implements NameableEnum {
        OFF("options.off"),
        FAST("options.graphics.fast"),
        FANCY("options.graphics.fancy");

        private final String displayName;

        BetterGrassMode(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public Text getDisplayName() {
            return Text.translatable(displayName);
        }
    }

    @SerialEntry(comment = "\nAdditional\n(default: false)")
    public boolean dirtPaths = false;

    @SerialEntry(comment = "(default: false)")
    public boolean farmLands = false;

    @SerialEntry(comment = "(default: true)")
    public boolean snowy = true;

    @SerialEntry(comment = "\nAdvanced")
    public List<String> grassBlocks =
            Lists.newArrayList("minecraft:grass_block",
                    "minecraft:podzol",
                    "minecraft:mycelium",
                    "minecraft:crimson_nylium",
                    "minecraft:warped_nylium");

    public static ConfigClassHandler<FabricBetterGrassConfig> handler() {
        return GSON;
    }

    public static void load() {
        GSON.load();
    }

    public static void save() {
        GSON.save();
    }

    public static FabricBetterGrassConfig instance() {
        return GSON.instance();
    }

    public static Screen createConfigScreen(Screen parent) {
        return FabricBetterGrassGui.createConfigScreen(parent);
    }
}
