package dev.ultimatchamp.bettergrass;

import dev.ultimatchamp.bettergrass.config.ForgeBetterGrassConfig;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.network.NetworkConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(ForgeBetterGrass.MODID)
public class ForgeBetterGrass {
    public static final String MODID = "bettergrass";
    public static final String MODNAME = "ForgeBetterGrass";

    private static final Logger LOGGER = LoggerFactory.getLogger(MODNAME);

    private static String MOD_VERSION;

    public ForgeBetterGrass() {
        MOD_VERSION = ModList.get().getModContainerById(MODID).get().getModInfo().getVersion().toString();
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (a, b) -> true));

        if (FMLLoader.getDist().isClient()) {
            this.onClientSetup();
        }
    }

    public void onClientSetup() {
        ForgeBetterGrassConfig.load();
        LOGGER.info("Gamers can finally touch grass!?");
    }
}
