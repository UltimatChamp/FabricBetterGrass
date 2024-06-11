package dev.ultimatchamp.bettergrass.mixin;

import dev.ultimatchamp.bettergrass.ForgeBetterGrassBakedModel;
import dev.ultimatchamp.bettergrass.config.ForgeBetterGrassConfig;
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
public class MixinSodiumGameOptionsPages {
    @Inject(method = "quality", at = @At(value = "INVOKE", target = "Lme/jellysquid/mods/sodium/client/gui/options/OptionGroup;createBuilder()Lme/jellysquid/mods/sodium/client/gui/options/OptionGroup$Builder;", ordinal = 1, shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILSOFT, remap = false)
    private static void betterGrass(CallbackInfoReturnable<OptionPage> cir, List<OptionGroup> groups) {
        groups.add(OptionGroup.createBuilder()
                .add(OptionImpl.createBuilder(ForgeBetterGrassBakedModel.BetterGrassMode.class, SodiumOptionsStorage.INSTANCE)
                    .setName(Text.translatable("yacl3.config.bettergrass:bettergrass_config.betterGrassMode"))
                    .setTooltip(Text.translatable("yacl3.config.bettergrass:bettergrass_config.betterGrassMode.desc"))
                    .setControl((opt) -> new CyclingControl<>(opt, ForgeBetterGrassBakedModel.BetterGrassMode.class, new Text[]{
                        Text.translatable("options.off"),
                        Text.translatable("options.graphics.fast"),
                        Text.translatable("options.graphics.fancy")
                    }))
                    .setBinding((options, value) -> ForgeBetterGrassConfig.instance().betterGrassMode = value,
                        (options) -> ForgeBetterGrassConfig.instance().betterGrassMode)
                    .setImpact(OptionImpact.VARIES)
                    .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                    .build()
                ).add(OptionImpl.createBuilder(boolean.class, SodiumOptionsStorage.INSTANCE)
                                .setName(Text.translatable("yacl3.config.bettergrass:bettergrass_config.dirtPaths"))
                                .setTooltip(Text.translatable("yacl3.config.bettergrass:bettergrass_config.dirtPaths.desc"))
                                .setControl(TickBoxControl::new)
                                .setBinding((options, value) -> ForgeBetterGrassConfig.instance().dirtPaths = value,
                                        (options) -> ForgeBetterGrassConfig.instance().dirtPaths)
                                .setImpact(OptionImpact.LOW)
                                .setFlags(OptionFlag.REQUIRES_ASSET_RELOAD)
                                .build()
                ).add(OptionImpl.createBuilder(boolean.class, SodiumOptionsStorage.INSTANCE)
                        .setName(Text.translatable("yacl3.config.bettergrass:bettergrass_config.farmLands"))
                        .setTooltip(Text.translatable("yacl3.config.bettergrass:bettergrass_config.farmLands.desc"))
                        .setControl(TickBoxControl::new)
                        .setBinding((options, value) -> ForgeBetterGrassConfig.instance().farmLands = value,
                                (options) -> ForgeBetterGrassConfig.instance().farmLands)
                        .setImpact(OptionImpact.LOW)
                        .setFlags(OptionFlag.REQUIRES_ASSET_RELOAD)
                        .build()
                ).add(OptionImpl.createBuilder(boolean.class, SodiumOptionsStorage.INSTANCE)
                        .setName(Text.translatable("yacl3.config.bettergrass:bettergrass_config.snowy"))
                        .setTooltip(Text.translatable("yacl3.config.bettergrass:bettergrass_config.snowy.desc"))
                        .setControl(TickBoxControl::new)
                        .setBinding((options, value) -> ForgeBetterGrassConfig.instance().snowy = value,
                                (options) -> ForgeBetterGrassConfig.instance().snowy)
                        .setImpact(OptionImpact.LOW)
                        .setFlags(OptionFlag.REQUIRES_ASSET_RELOAD)
                        .build()
                )
                .build()
        );
    }
}
