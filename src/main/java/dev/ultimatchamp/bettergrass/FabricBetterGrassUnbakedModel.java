package dev.ultimatchamp.bettergrass;

import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.Baker;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.function.Function;

public class FabricBetterGrassUnbakedModel implements UnbakedModel {
    private final UnbakedModel baseModel;

    public FabricBetterGrassUnbakedModel(UnbakedModel unbakedModel) {
        this.baseModel = unbakedModel;
    }


    @Override
    public Collection<Identifier> getModelDependencies() {
        return this.baseModel.getModelDependencies();
    }

    @Override
    public void setParents(Function<Identifier, UnbakedModel> modelLoader) {
        this.baseModel.setParents(modelLoader);
    }

    @Nullable
    @Override
    //? if =1.21 {
    public BakedModel bake(Baker loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer) {
        return new FabricBetterGrassBakedModel(this.baseModel.bake(loader, textureGetter, rotationContainer));
    }
    //?} elif =1.20.1 {
    /*public BakedModel bake(Baker loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        return new FabricBetterGrassBakedModel(this.baseModel.bake(loader, textureGetter, rotationContainer, modelId));
    }
    *///?}
}
