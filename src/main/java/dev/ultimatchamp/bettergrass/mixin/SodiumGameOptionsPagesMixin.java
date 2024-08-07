package dev.ultimatchamp.bettergrass.mixin;

import dev.ultimatchamp.bettergrass.config.FabricBetterGrassConfig;
import dev.ultimatchamp.bettergrass.config.SodiumOptionsStorage;
import me.jellysquid.mods.sodium.client.gui.SodiumGameOptionPages;
import me.jellysquid.mods.sodium.client.gui.options.*;
import me.jellysquid.mods.sodium.client.gui.options.control.CyclingControl;
import me.jellysquid.mods.sodium.client.gui.options.control.TickBoxControl;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(value = SodiumGameOptionPages.class, remap = false)
public class SodiumGameOptionsPagesMixin {
    @Inject(method = "quality", at = @At(value = "INVOKE", target = "Lme/jellysquid/mods/sodium/client/gui/options/OptionGroup;createBuilder()Lme/jellysquid/mods/sodium/client/gui/options/OptionGroup$Builder;", ordinal = 1, shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILSOFT, remap = false)
    private static void betterGrass(CallbackInfoReturnable<OptionPage> cir, List<OptionGroup> groups) {
        groups.add(OptionGroup.createBuilder()
                .add(OptionImpl.createBuilder(FabricBetterGrassConfig.BetterGrassMode.class, SodiumOptionsStorage.INSTANCE)
                    .setName(Text.translatable("bettergrass.betterGrassMode"))
                    .setTooltip(Text.translatable("bettergrass.betterGrassMode.desc"))
                    .setControl((opt) -> new CyclingControl<>(opt, FabricBetterGrassConfig.BetterGrassMode.class, new Text[]{
                        Text.translatable("options.off"),
                        Text.translatable("options.graphics.fast"),
                        Text.translatable("options.graphics.fancy")
                    }))
                    .setBinding((options, value) -> FabricBetterGrassConfig.instance().betterGrassMode = value,
                        (options) -> FabricBetterGrassConfig.instance().betterGrassMode)
                    .setImpact(OptionImpact.VARIES)
                    .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                    .build()
                ).add(OptionImpl.createBuilder(boolean.class, SodiumOptionsStorage.INSTANCE)
                        .setName(Text.translatable("block.minecraft.snow"))
                        .setTooltip(Text.translatable("bettergrass.snowy.desc"))
                        .setControl(TickBoxControl::new)
                        .setBinding((options, value) -> FabricBetterGrassConfig.instance().snowy = value,
                                (options) -> FabricBetterGrassConfig.instance().snowy)
                        .setImpact(OptionImpact.LOW)
                        .setFlags(OptionFlag.REQUIRES_ASSET_RELOAD)
                        .build()
                ).add(OptionImpl.createBuilder(boolean.class, SodiumOptionsStorage.INSTANCE)
                        .setName(Text.translatable("block.minecraft.grass_block"))
                        .setTooltip(Text.translatable("bettergrass.grassBlocks.desc"))
                        .setControl(TickBoxControl::new)
                        .setBinding((options, value) -> FabricBetterGrassConfig.instance().grassBlocks = value,
                                (options) -> FabricBetterGrassConfig.instance().grassBlocks)
                        .setImpact(OptionImpact.LOW)
                        .setFlags(OptionFlag.REQUIRES_ASSET_RELOAD)
                        .build()
                ).add(OptionImpl.createBuilder(boolean.class, SodiumOptionsStorage.INSTANCE)
                                .setName(Text.translatable("block.minecraft.dirt_path"))
                                .setTooltip(Text.translatable("bettergrass.dirtPaths.desc"))
                                .setControl(TickBoxControl::new)
                                .setBinding((options, value) -> FabricBetterGrassConfig.instance().dirtPaths = value,
                                        (options) -> FabricBetterGrassConfig.instance().dirtPaths)
                                .setImpact(OptionImpact.LOW)
                                .setFlags(OptionFlag.REQUIRES_ASSET_RELOAD)
                                .build()
                ).add(OptionImpl.createBuilder(boolean.class, SodiumOptionsStorage.INSTANCE)
                        .setName(Text.translatable("block.minecraft.farmland"))
                        .setTooltip(Text.translatable("bettergrass.farmLands.desc"))
                        .setControl(TickBoxControl::new)
                        .setBinding((options, value) -> FabricBetterGrassConfig.instance().farmLands = value,
                                (options) -> FabricBetterGrassConfig.instance().farmLands)
                        .setImpact(OptionImpact.LOW)
                        .setFlags(OptionFlag.REQUIRES_ASSET_RELOAD)
                        .build()
                ).add(OptionImpl.createBuilder(boolean.class, SodiumOptionsStorage.INSTANCE)
                        .setName(Text.translatable("block.minecraft.podzol"))
                        .setTooltip(Text.translatable("bettergrass.podzol.desc"))
                        .setControl(TickBoxControl::new)
                        .setBinding((options, value) -> FabricBetterGrassConfig.instance().podzol = value,
                                (options) -> FabricBetterGrassConfig.instance().podzol)
                        .setImpact(OptionImpact.LOW)
                        .setFlags(OptionFlag.REQUIRES_ASSET_RELOAD)
                        .build()
                ).add(OptionImpl.createBuilder(boolean.class, SodiumOptionsStorage.INSTANCE)
                        .setName(Text.translatable("block.minecraft.mycelium"))
                        .setTooltip(Text.translatable("bettergrass.mycelium.desc"))
                        .setControl(TickBoxControl::new)
                        .setBinding((options, value) -> FabricBetterGrassConfig.instance().mycelium = value,
                                (options) -> FabricBetterGrassConfig.instance().mycelium)
                        .setImpact(OptionImpact.LOW)
                        .setFlags(OptionFlag.REQUIRES_ASSET_RELOAD)
                        .build()
                ).add(OptionImpl.createBuilder(boolean.class, SodiumOptionsStorage.INSTANCE)
                        .setName(Text.translatable("block.minecraft.crimson_nylium"))
                        .setTooltip(Text.translatable("bettergrass.crimsonNylium.desc"))
                        .setControl(TickBoxControl::new)
                        .setBinding((options, value) -> FabricBetterGrassConfig.instance().crimsonNylium = value,
                                (options) -> FabricBetterGrassConfig.instance().crimsonNylium)
                        .setImpact(OptionImpact.LOW)
                        .setFlags(OptionFlag.REQUIRES_ASSET_RELOAD)
                        .build()
                ).add(OptionImpl.createBuilder(boolean.class, SodiumOptionsStorage.INSTANCE)
                        .setName(Text.translatable("block.minecraft.warped_nylium"))
                        .setTooltip(Text.translatable("bettergrass.warpedNylium.desc"))
                        .setControl(TickBoxControl::new)
                        .setBinding((options, value) -> FabricBetterGrassConfig.instance().warpedNylium = value,
                                (options) -> FabricBetterGrassConfig.instance().warpedNylium)
                        .setImpact(OptionImpact.LOW)
                        .setFlags(OptionFlag.REQUIRES_ASSET_RELOAD)
                        .build()
                )
                .build()
        );
    }
}
