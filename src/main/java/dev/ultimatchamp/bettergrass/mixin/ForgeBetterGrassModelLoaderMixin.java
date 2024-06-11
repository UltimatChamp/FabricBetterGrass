package dev.ultimatchamp.bettergrass.mixin;

import dev.ultimatchamp.bettergrass.ForgeBetterGrassUnbakedModel;
import dev.ultimatchamp.bettergrass.config.ForgeBetterGrassConfig;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.Set;

@Mixin(ModelLoader.class)
public class ForgeBetterGrassModelLoaderMixin {
    @Shadow
    @Final
    private Map<Identifier, UnbakedModel> unbakedModels;

    @Shadow
    @Final
    private Set<Identifier> modelsToLoad;

    @Inject(method = "putModel", at = @At("HEAD"), cancellable = true)
    private void onPutModel(Identifier id, UnbakedModel unbakedModel, CallbackInfo ci) {
        if (id instanceof ModelIdentifier modelId) {
            if (!modelId.getVariant().equals("inventory")) {
                ForgeBetterGrassConfig.instance().grassBlocks.forEach(s -> {
                    if (modelId.toString().startsWith(s.split("\\[")[0]) && !modelId.toString().contains("snowy=true")) {
                        var newModel = new ForgeBetterGrassUnbakedModel(unbakedModel);
                        this.unbakedModels.put(id, newModel);
                        this.modelsToLoad.addAll(newModel.getModelDependencies());
                        ci.cancel();
                    }
                });

                if (ForgeBetterGrassConfig.instance().snowy) {
                    ForgeBetterGrassConfig.instance().grassBlocks.forEach(s -> {
                        if (modelId.toString().startsWith(s.split("\\[")[0]) && modelId.toString().contains("snowy=true")) {
                            var newModel = new ForgeBetterGrassUnbakedModel(unbakedModel);
                            this.unbakedModels.put(id, newModel);
                            this.modelsToLoad.addAll(newModel.getModelDependencies());
                            ci.cancel();
                        }
                    });
                }

                if (ForgeBetterGrassConfig.instance().dirtPaths) {
                    if (modelId.toString().startsWith("minecraft:dirt_path".split("\\[")[0])) {
                        var newModel = new ForgeBetterGrassUnbakedModel(unbakedModel);
                        this.unbakedModels.put(id, newModel);
                        this.modelsToLoad.addAll(newModel.getModelDependencies());
                        ci.cancel();
                    }
                }

                if (ForgeBetterGrassConfig.instance().farmLands) {
                    if (modelId.toString().startsWith("minecraft:farmland".split("\\[")[0])) {
                        var newModel = new ForgeBetterGrassUnbakedModel(unbakedModel);
                        this.unbakedModels.put(id, newModel);
                        this.modelsToLoad.addAll(newModel.getModelDependencies());
                        ci.cancel();
                    }
                }
            }
        }
    }
}
