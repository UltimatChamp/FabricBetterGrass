package dev.ultimatchamp.bettergrass.mixin;

import dev.ultimatchamp.bettergrass.config.FabricBetterGrassConfig;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.gui.screen.option.VideoOptionsScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(VideoOptionsScreen.class)
public abstract class VideoOptionsScreenMixin extends GameOptionsScreen {
    public VideoOptionsScreenMixin(Screen parent, GameOptions gameOptions, Text title) {
        super(parent, gameOptions, title);
    }

    @ModifyArg(
            //? if =1.20.1 {
            method = "init",
            //?} else
            //method = "addOptions",
            at = @At(
                    value = "INVOKE",
                    //? if =1.20.1 {
                    target = "Lnet/minecraft/client/gui/widget/OptionListWidget;addSingleOptionEntry(Lnet/minecraft/client/option/SimpleOption;)I"
                    //?} else
                    //target = "Lnet/minecraft/client/gui/widget/OptionListWidget;addSingleOptionEntry(Lnet/minecraft/client/option/SimpleOption;)V"
            )
    )
    private SimpleOption<?> bettergrass$addConfigButton(SimpleOption<?> option) {
        return new SimpleOption<>("yacl3.config.bettergrass:bettergrass_config.title", SimpleOption.constantTooltip(Text.empty()), (arg, object) -> Text.empty(), SimpleOption.BOOLEAN, true, (parent) -> this.client.setScreen(FabricBetterGrassConfig.createConfigScreen(this)));
    }
}
