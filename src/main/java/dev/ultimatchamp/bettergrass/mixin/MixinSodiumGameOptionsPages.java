package dev.ultimatchamp.bettergrass.mixin;

import dev.ultimatchamp.bettergrass.FabricBetterGrassBakedModel;
import dev.ultimatchamp.bettergrass.config.FabricBetterGrassConfig;
import dev.ultimatchamp.bettergrass.config.SodiumOptionsStorage;
import me.jellysquid.mods.sodium.client.gui.SodiumGameOptionPages;
import me.jellysquid.mods.sodium.client.gui.options.*;
import me.jellysquid.mods.sodium.client.gui.options.control.CyclingControl;
import me.jellysquid.mods.sodium.client.gui.options.control.TickBoxControl;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = SodiumGameOptionPages.class, remap = false)
public class MixinSodiumGameOptionsPages {
    @Redirect(method = "quality", at = @At(value = "INVOKE", target = "Lme/jellysquid/mods/sodium/client/gui/options/OptionGroup$Builder;add(Lme/jellysquid/mods/sodium/client/gui/options/Option;)Lme/jellysquid/mods/sodium/client/gui/options/OptionGroup$Builder;", ordinal = 1), remap = false)
    private static OptionGroup.Builder grassMode(OptionGroup.Builder instance, Option<?> option) {
        return instance.add(OptionImpl.createBuilder(FabricBetterGrassBakedModel.BetterGrassMode.class, SodiumOptionsStorage.INSTANCE)
                .setName(Text.translatable("yacl3.config.bettergrass:bettergrass_config.betterGrassMode"))
                .setTooltip(Text.translatable("yacl3.config.bettergrass:bettergrass_config.betterGrassMode.desc"))
                .setControl((opt) -> new CyclingControl<>(opt, FabricBetterGrassBakedModel.BetterGrassMode.class, new Text[]{
                        Text.translatable("yacl3.config.enum.BetterGrassMode.off"),
                        Text.translatable("yacl3.config.enum.BetterGrassMode.fast"),
                        Text.translatable("yacl3.config.enum.BetterGrassMode.fancy")
                }))
                .setBinding((options, value) -> FabricBetterGrassConfig.instance().betterGrassMode = value,
                    (options) -> FabricBetterGrassConfig.instance().betterGrassMode)
                .setImpact(OptionImpact.VARIES)
                .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                .build());
    }

    @Redirect(method = "quality", at = @At(value = "INVOKE", target = "Lme/jellysquid/mods/sodium/client/gui/options/OptionGroup$Builder;add(Lme/jellysquid/mods/sodium/client/gui/options/Option;)Lme/jellysquid/mods/sodium/client/gui/options/OptionGroup$Builder;", ordinal = 2), remap = false)
    private static OptionGroup.Builder dirtPaths(OptionGroup.Builder instance, Option<?> option) {
        return instance.add(OptionImpl.createBuilder(boolean.class, SodiumOptionsStorage.INSTANCE)
                .setName(Text.translatable("yacl3.config.bettergrass:bettergrass_config.dirtPaths"))
                .setTooltip(Text.translatable("yacl3.config.bettergrass:bettergrass_config.dirtPaths.desc"))
                .setControl(TickBoxControl::new)
                .setBinding((options, value) -> FabricBetterGrassConfig.instance().dirtPaths = value,
                        (options) -> FabricBetterGrassConfig.instance().dirtPaths)
                .setImpact(OptionImpact.LOW)
                .setFlags(OptionFlag.REQUIRES_ASSET_RELOAD)
                .build());
    }

    @Redirect(method = "quality", at = @At(value = "INVOKE", target = "Lme/jellysquid/mods/sodium/client/gui/options/OptionGroup$Builder;add(Lme/jellysquid/mods/sodium/client/gui/options/Option;)Lme/jellysquid/mods/sodium/client/gui/options/OptionGroup$Builder;", ordinal = 3), remap = false)
    private static OptionGroup.Builder farmLands(OptionGroup.Builder instance, Option<?> option) {
        return instance.add(OptionImpl.createBuilder(boolean.class, SodiumOptionsStorage.INSTANCE)
                .setName(Text.translatable("yacl3.config.bettergrass:bettergrass_config.farmLands"))
                .setTooltip(Text.translatable("yacl3.config.bettergrass:bettergrass_config.farmLands.desc"))
                .setControl(TickBoxControl::new)
                .setBinding((options, value) -> FabricBetterGrassConfig.instance().farmLands = value,
                        (options) -> FabricBetterGrassConfig.instance().farmLands)
                .setImpact(OptionImpact.LOW)
                .setFlags(OptionFlag.REQUIRES_ASSET_RELOAD)
                .build());
    }

    @Redirect(method = "quality", at = @At(value = "INVOKE", target = "Lme/jellysquid/mods/sodium/client/gui/options/OptionGroup$Builder;add(Lme/jellysquid/mods/sodium/client/gui/options/Option;)Lme/jellysquid/mods/sodium/client/gui/options/OptionGroup$Builder;", ordinal = 4), remap = false)
    private static OptionGroup.Builder snowy(OptionGroup.Builder instance, Option<?> option) {
        return instance.add(OptionImpl.createBuilder(boolean.class, SodiumOptionsStorage.INSTANCE)
                .setName(Text.translatable("yacl3.config.bettergrass:bettergrass_config.snowy"))
                .setTooltip(Text.translatable("yacl3.config.bettergrass:bettergrass_config.snowy.desc"))
                .setControl(TickBoxControl::new)
                .setBinding((options, value) -> FabricBetterGrassConfig.instance().snowy = value,
                        (options) -> FabricBetterGrassConfig.instance().snowy)
                .setImpact(OptionImpact.LOW)
                .setFlags(OptionFlag.REQUIRES_ASSET_RELOAD)
                .build());
    }
}
