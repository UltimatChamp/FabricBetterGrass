package dev.ultimatchamp.bettergrass.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.*;
import dev.isxander.yacl3.gui.controllers.cycling.EnumController;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class FabricBetterGrassGui {
    public static Screen createConfigScreen(Screen parent) {
        FabricBetterGrassConfig.load();
        return YetAnotherConfigLib.create(FabricBetterGrassConfig.handler(), (defaults, config, builder) -> builder
                        .title(Text.translatable("bettergrass.title"))
                        .category(ConfigCategory.createBuilder()
                                .name(Text.translatable("bettergrass.category.general"))
                                .option(Option.<FabricBetterGrassConfig.BetterGrassMode>createBuilder()
                                        .name(Text.translatable("bettergrass.betterGrassMode"))
                                        .description(OptionDescription.createBuilder()
                                                .text(Text.translatable("bettergrass.betterGrassMode.desc"))
                                                .webpImage(Identifier.of("bettergrass", "textures/images/bettergrassmode.webp"))
                                                .build())
                                        .binding(
                                                defaults.betterGrassMode,
                                                () -> config.betterGrassMode,
                                                (value) -> config.betterGrassMode = value
                                        )
                                        .customController(opt -> new EnumController<>(opt, FabricBetterGrassConfig.BetterGrassMode.class))
                                        .build())
                                .group(OptionGroup.createBuilder()
                                        .name(Text.translatable("bettergrass.category.general.group.connectedblocks"))
                                        .option(Option.<Boolean>createBuilder()
                                                .name(Text.translatable("block.minecraft.dirt_path"))
                                                .description(OptionDescription.createBuilder()
                                                        .text(Text.translatable("bettergrass.dirtPaths.desc"))
                                                        .build())
                                                .binding(
                                                        defaults.dirtPaths,
                                                        () -> config.dirtPaths,
                                                        (value) -> config.dirtPaths = value
                                                )
                                                .controller(TickBoxControllerBuilder::create)
                                                .build())
                                        .option(Option.<Boolean>createBuilder()
                                                .name(Text.translatable("block.minecraft.farmland"))
                                                .description(OptionDescription.createBuilder()
                                                        .text(Text.translatable("bettergrass.farmLands.desc"))
                                                        .build())
                                                .binding(
                                                        defaults.farmLands,
                                                        () -> config.farmLands,
                                                        (value) -> config.farmLands = value
                                                )
                                                .controller(TickBoxControllerBuilder::create)
                                                .build())
                                        .option(Option.<Boolean>createBuilder()
                                                .name(Text.translatable("block.minecraft.snow"))
                                                .description(OptionDescription.createBuilder()
                                                        .text(Text.translatable("bettergrass.snowy.desc"))
                                                        .build())
                                                .binding(
                                                        defaults.snowy,
                                                        () -> config.snowy,
                                                        (value) -> config.snowy = value
                                                )
                                                .controller(TickBoxControllerBuilder::create)
                                                .build())
                                        .build())
                                .build())
                        .category(ConfigCategory.createBuilder()
                                .name(Text.translatable("bettergrass.category.advanced"))
                                .group(ListOption.<String>createBuilder()
                                        .name(Text.translatable("soundCategory.block"))
                                        .description(OptionDescription.createBuilder()
                                                .text(Text.translatable("bettergrass.grassBlocks.desc"))
                                                .build())
                                        .binding(
                                                defaults.grassBlocks,
                                                () -> config.grassBlocks,
                                                val -> config.grassBlocks = val
                                        )
                                        .controller(StringControllerBuilder::create)
                                        .initial("")
                                        .insertEntriesAtEnd(true)
                                        .build())
                                .build())
                        .save(FabricBetterGrassConfig.handler()::save)
                )
                .generateScreen(parent);
    }
}
