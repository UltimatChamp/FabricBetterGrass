package dev.ultimatchamp.bettergrass.mixin;

import dev.ultimatchamp.bettergrass.FabricBetterGrassUnbakedModel;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

import dev.ultimatchamp.bettergrass.FabricBetterGrass.FabricBetterGrassConfig;

@Mixin(ModelLoader.class)
public class FabricBetterGrassModelLoaderMixin {
    @Shadow
    @Final
    private Map<ModelIdentifier, UnbakedModel> modelsToBake;

    @Inject(method = "addModelToBake", at = @At("HEAD"), cancellable = true)
    private void onAddModelToBake(ModelIdentifier id, UnbakedModel unbakedModel, CallbackInfo ci) {
        if (id instanceof ModelIdentifier modelId) {
            if (!modelId.getVariant().equals("inventory")) {
                FabricBetterGrassConfig.blockstates.forEach(s -> {
                    if (modelId.toString().startsWith(s.split("\\[")[0])) {
                        var newModel = new FabricBetterGrassUnbakedModel(unbakedModel);
                        this.modelsToBake.put(id, newModel);
                        ci.cancel();
                    }
                });
            }
        }
    }
}
