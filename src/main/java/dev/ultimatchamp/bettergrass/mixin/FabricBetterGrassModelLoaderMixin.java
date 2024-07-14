package dev.ultimatchamp.bettergrass.mixin;

import dev.ultimatchamp.bettergrass.FabricBetterGrassUnbakedModel;
import dev.ultimatchamp.bettergrass.config.FabricBetterGrassConfig;
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
public class FabricBetterGrassModelLoaderMixin {
    @Shadow
    @Final
    //? if =1.21 {
    private Map<ModelIdentifier, UnbakedModel> modelsToBake;
    //?} elif =1.20.1 {
    /*private Map<Identifier, UnbakedModel> unbakedModels;

    @Shadow
    @Final
    private Set<Identifier> modelsToLoad;
    *///?}

    //? if =1.21 {
    @Inject(method = "addModelToBake", at = @At("HEAD"), cancellable = true)
    private void onAddModelToBake(ModelIdentifier id, UnbakedModel unbakedModel, CallbackInfo ci) {
    //?} elif =1.20.1 {
    /*@Inject(method = "putModel", at = @At("HEAD"), cancellable = true)
    private void onPutModel(Identifier id, UnbakedModel unbakedModel, CallbackInfo ci) {
    *///?}
        if (id instanceof ModelIdentifier modelId) {
            if (!modelId.getVariant().equals("inventory")) {
                FabricBetterGrassConfig.instance().grassBlocks.forEach(s -> {
                    if (modelId.toString().startsWith(s.split("\\[")[0]) && !modelId.toString().contains("snowy=true")) {
                        var newModel = new FabricBetterGrassUnbakedModel(unbakedModel);
                        //? if =1.21 {
                        this.modelsToBake.put(id, newModel);
                        //?} elif =1.20.1 {
                        /*this.unbakedModels.put(id, newModel);
                        this.modelsToLoad.addAll(newModel.getModelDependencies());
                        *///?}
                        ci.cancel();
                    }
                    if (FabricBetterGrassConfig.instance().snowy) {
                        if (modelId.toString().startsWith(s.split("\\[")[0]) && modelId.toString().contains("snowy=true")) {
                            var newModel = new FabricBetterGrassUnbakedModel(unbakedModel);
                            //? if =1.21 {
                            this.modelsToBake.put(id, newModel);
                            //?} elif =1.20.1 {
                            /*this.unbakedModels.put(id, newModel);
                            this.modelsToLoad.addAll(newModel.getModelDependencies());
                            *///?}
                            ci.cancel();
                        }
                    }
                });

                if (FabricBetterGrassConfig.instance().dirtPaths) {
                    if (modelId.toString().startsWith("minecraft:dirt_path".split("\\[")[0])) {
                        var newModel = new FabricBetterGrassUnbakedModel(unbakedModel);
                        //? if =1.21 {
                        this.modelsToBake.put(id, newModel);
                        //?} elif =1.20.1 {
                        /*this.unbakedModels.put(id, newModel);
                        this.modelsToLoad.addAll(newModel.getModelDependencies());
                        *///?}
                        ci.cancel();
                    }
                }

                if (FabricBetterGrassConfig.instance().farmLands) {
                    if (modelId.toString().startsWith("minecraft:farmland".split("\\[")[0])) {
                        var newModel = new FabricBetterGrassUnbakedModel(unbakedModel);
                        //? if =1.21 {
                        this.modelsToBake.put(id, newModel);
                        //?} elif =1.20.1 {
                        /*this.unbakedModels.put(id, newModel);
                        this.modelsToLoad.addAll(newModel.getModelDependencies());
                        *///?}
                        ci.cancel();
                    }
                }
            }
        }
    }
}
