package dev.ultimatchamp.bettergrass;

import dev.ultimatchamp.bettergrass.config.BetterGrassifyConfig;
//? if fabric {
import net.fabricmc.api.ClientModInitializer;
//?} elif neo {
/*import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
*///?} elif forge {
/*import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
*///?}
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//? if fabric {
public class BetterGrassify implements ClientModInitializer {
//?} else {
/*@Mod("bettergrass")
public final class BetterGrassify {
*///?}
    public static final Logger LOGGER = LoggerFactory.getLogger("bettergrass");

    //? if fabric {
    @Override
    public void onInitializeClient() {
        BetterGrassifyConfig.load();
    //?} elif neo {
    /*public BetterGrassify(ModContainer modContainer, IEventBus modBus) {
        modBus.addListener(this::onClientSetup);
        BetterGrassifyConfig.load();

        modContainer.registerExtensionPoint(IConfigScreenFactory.class, (client, parent) -> BetterGrassifyConfig.createConfigScreen(parent));
    *///?} elif forge {
    /*public BetterGrassify() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
        BetterGrassifyConfig.load();

        ModLoadingContext.get().registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () ->  new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> BetterGrassifyConfig.createConfigScreen(parent))
        );
    *///?}
        if (BetterGrassifyConfig.instance().betterGrassMode == BetterGrassifyConfig.BetterGrassMode.OFF)
            LOGGER.info("[BetterGrassify] Better Grass is disabled.");
        else
            LOGGER.info("[BetterGrassify] [" + BetterGrassifyConfig.instance().betterGrassMode.toString() + "] Gamers can finally touch grass!?");
    }

    //? if !fabric {
    /*private void onClientSetup(FMLClientSetupEvent event) {}
    *///?}
}
