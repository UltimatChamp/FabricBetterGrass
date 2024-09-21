package dev.ultimatchamp.bettergrass;

import dev.ultimatchamp.bettergrass.config.BetterGrassifyConfig;
import dev.ultimatchamp.bettergrass.util.SpriteCalculator;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;

import java.util.function.Supplier;

//? if neo {
/*import net.fabricmc.loader.api.FabricLoader;
*///?}

@SuppressWarnings("ConstantConditions")
public class BetterGrassifyBakedModel extends ForwardingBakedModel {
    public BetterGrassifyBakedModel(BakedModel baseModel) {
        this.wrapped = baseModel;
    }

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
        context.pushTransform(quad -> {
            if (BetterGrassifyConfig.instance().betterGrassMode.equals(BetterGrassifyConfig.BetterGrassMode.OFF)) {
                return true;
            } else if (BetterGrassifyConfig.instance().betterGrassMode.equals(BetterGrassifyConfig.BetterGrassMode.FAST)) {
                if (quad.nominalFace().getAxis() != Direction.Axis.Y) {
                    if (isSnowy(blockView, pos, pos.up()))
                        spriteBake(quad, blockView.getBlockState(pos.up()), randomSupplier);
                    else
                        spriteBake(quad, blockView.getBlockState(pos), randomSupplier);
                    return true;
                }
            } else if (BetterGrassifyConfig.instance().betterGrassMode.equals(BetterGrassifyConfig.BetterGrassMode.FANCY)) {
                if (quad.nominalFace().getAxis() != Direction.Axis.Y) {
                    Direction face = quad.nominalFace();

                    if (canFullyConnect(blockView, state, pos, face)) {
                        if (isSnowy(blockView, pos, pos.up()))
                            spriteBake(quad, blockView.getBlockState(pos.offset(face)), randomSupplier);
                        else
                            spriteBake(quad, state, randomSupplier);
                    }
                }
                return true;
            }
            return true;
        });
        super.emitBlockQuads(blockView, state, pos, randomSupplier, context);
        context.popTransform();
    }

    private static boolean canFullyConnect(BlockRenderView world, BlockState self, BlockPos selfPos, Direction direction) {
        return canConnect(world, self, selfPos, selfPos.offset(direction).down());
    }

    private static boolean canConnect(BlockRenderView world, BlockState self, BlockPos selfPos, BlockPos adjacentPos) {
        var adjacent = world.getBlockState(adjacentPos);
        var upPos = adjacentPos.up();
        var up = world.getBlockState(upPos);

        return canConnect(self, adjacent) && (up.isAir() || isSnowy(world, selfPos, selfPos.up()) || !up.isSideSolidFullSquare(world, upPos, Direction.DOWN));
    }

    private static boolean canConnect(BlockState self, BlockState adjacent) {
        return self == adjacent;
    }

    private static boolean isSnowy(BlockRenderView world, BlockPos selfPos, BlockPos upPos) {
        return String.valueOf(world.getBlockState(selfPos)).contains("[snowy=true]") || canHaveSnowLayer(upPos);
    }

    public static boolean canHaveSnowLayer(BlockPos selfPos) {
        //? if neo {
        /*if (!FabricLoader.getInstance().isModLoaded("sodium")) {
            return false;
        }
        *///?}

        //? if >1.20.1 {
        var world = MinecraftClient.getInstance().world;
        var self = world.getBlockState(selfPos);

        var isBottomSnowy = String.valueOf(world.getBlockState(selfPos.down())).contains("[snowy=false]");

        var isNorth = String.valueOf(world.getBlockState(selfPos.north())).contains("snow}[layers=") || String.valueOf(world.getBlockState(selfPos.north())).contains("snow_block");
        var isSouth = String.valueOf(world.getBlockState(selfPos.south())).contains("snow}[layers=") || String.valueOf(world.getBlockState(selfPos.south())).contains("snow_block");
        var isEast = String.valueOf(world.getBlockState(selfPos.east())).contains("snow}[layers=") || String.valueOf(world.getBlockState(selfPos.east())).contains("snow_block");
        var isWest = String.valueOf(world.getBlockState(selfPos.west())).contains("snow}[layers=") || String.valueOf(world.getBlockState(selfPos.west())).contains("snow_block");

        var snowDetectionMode = false;

        if (BetterGrassifyConfig.instance().betterSnowMode == BetterGrassifyConfig.BetterSnowMode.OPTIFINE) {
            snowDetectionMode = isNorth || isSouth || isEast || isWest;
        } else if (BetterGrassifyConfig.instance().betterSnowMode == BetterGrassifyConfig.BetterSnowMode.LAMBDA) {
            snowDetectionMode = ((isNorth || isSouth) && (isEast || isWest)) || (isNorth && isSouth) || (isEast && isWest);
        }

        return snowDetectionMode && !self.isAir() && isBottomSnowy && !self.isSideSolidFullSquare(world, selfPos, Direction.DOWN);
        //?} else {
        /*return false;
        *///?}
    }

    @SuppressWarnings("deprecation")
    private static boolean spriteBake(MutableQuadView quad, BlockState state, Supplier<Random> randomSupplier) {
        var sprite = SpriteCalculator.calculateSprite(state, Direction.UP, randomSupplier);

        if (sprite != null && !String.valueOf(sprite).contains("missingno")) {
            quad.spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV);
            return true;
        } else {
            return false;
        }
    }
}
