package dev.ultimatchamp.bettergrass.mixin;

import dev.ultimatchamp.bettergrass.config.BetterGrassifyConfig;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.OptionListWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.gui.screen.option.VideoOptionsScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if <1.21 {
/*import org.spongepowered.asm.mixin.Shadow;
*///?}

@Mixin(VideoOptionsScreen.class)
public abstract class VideoOptionsScreenMixin extends GameOptionsScreen {
    //? if <1.21 {
    /*@Shadow
    private OptionListWidget list;
    *///?}

    public VideoOptionsScreenMixin(Screen parent, GameOptions gameOptions, Text title, OptionListWidget list, OptionListWidget list1) {
        super(parent, gameOptions, title);
    }

    @Inject(
            //? if >1.20.6 {
            method = "addOptions",
            //?} else {
            /*method = "init",
            *///?}
            at = @At(
                    value = "INVOKE",
                    //? if >1.20.1 {
                    target = "Lnet/minecraft/client/gui/widget/OptionListWidget;addSingleOptionEntry(Lnet/minecraft/client/option/SimpleOption;)V",
                    //?} else {
                    /*target = "Lnet/minecraft/client/gui/widget/OptionListWidget;addSingleOptionEntry(Lnet/minecraft/client/option/SimpleOption;)I",
                    *///?}
                    ordinal = 0
            )
    )
    @SuppressWarnings("ConstantConditions")
    private void bettergrass$addConfigButton(CallbackInfo ci) {
        //? if >1.20.6 {
        this.body.addSingleOptionEntry(new SimpleOption<>("bettergrass.title", SimpleOption.constantTooltip(Text.empty()), (arg, object) -> Text.empty(), SimpleOption.BOOLEAN, true, (parent) -> this.client.setScreen(BetterGrassifyConfig.createConfigScreen(this))));
        //?} else {
        /*this.list.addSingleOptionEntry(new SimpleOption<>("bettergrass.title", SimpleOption.constantTooltip(Text.empty()), (arg, object) -> Text.empty(), SimpleOption.BOOLEAN, true, (parent) -> this.client.setScreen(BetterGrassifyConfig.createConfigScreen(this))));
        *///?}
    }
}
