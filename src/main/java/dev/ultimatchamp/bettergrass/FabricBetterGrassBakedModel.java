package dev.ultimatchamp.bettergrass;

import dev.ultimatchamp.bettergrass.config.FabricBetterGrassConfig;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;

import java.util.function.Supplier;

public class FabricBetterGrassBakedModel extends ForwardingBakedModel {

    public FabricBetterGrassBakedModel(BakedModel baseModel) {
        this.wrapped = baseModel;
    }

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

    public static boolean isSnowy;
    public static BlockState upBlock;

    public enum BetterGrassMode {
        OFF, FAST, FANCY
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
        context.pushTransform(quad -> {

            if (FabricBetterGrassConfig.instance().betterGrassMode.equals(BetterGrassMode.OFF)) {
                return true;
            } else if (FabricBetterGrassConfig.instance().betterGrassMode.equals(BetterGrassMode.FAST)) {
                if (quad.nominalFace().getAxis() != Direction.Axis.Y) {
                    fastSnowy(blockView, pos);
                    spriteBake(quad, blockView.getBlockState(pos), randomSupplier);
                    return true;
                }
            } else if (FabricBetterGrassConfig.instance().betterGrassMode.equals(BetterGrassMode.FANCY)) {
                if (quad.nominalFace().getAxis() != Direction.Axis.Y) {
                    Direction face = quad.nominalFace();

                    if (canFullyConnect(blockView, state, pos, face)) {
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
        upBlock = up;
        isSnowy = String.valueOf(up).contains("minecraft:snow") && up.isSideSolidFullSquare(world, upPos, Direction.DOWN);

        return canConnect(self, adjacent) && (up.isAir() || !up.isSideSolidFullSquare(world, upPos, Direction.DOWN) || isSnowy);
    }

    private static boolean fastSnowy(BlockRenderView world, BlockPos adjacentPos) {
        var upPos = adjacentPos.up();
        var up = world.getBlockState(upPos);
        upBlock = up;
        isSnowy = String.valueOf(up).contains("minecraft:snow") && up.isSideSolidFullSquare(world, upPos, Direction.DOWN);

        return true;
    }

    private static boolean canConnect(BlockState self, BlockState adjacent) {
        return self == adjacent;
    }

    @SuppressWarnings("deprecation")
    private static boolean spriteBake(MutableQuadView quad, BlockState state, Supplier<Random> randomSupplier) {
        var sprite = SpriteCalculator.calculateSprite(state, Direction.UP, randomSupplier);

        if (isSnowy) {
            sprite = SpriteCalculator.calculateSprite(upBlock, Direction.UP, randomSupplier);
        }

        if (sprite != null && (String.valueOf(sprite).contains("_top") || String.valueOf(sprite).contains("snow") || String.valueOf(sprite).contains("farmland") || String.valueOf(sprite).contains("path") || String.valueOf(sprite).contains("lium"))) {
            if (!(isSnowy && !String.valueOf(upBlock).contains("minecraft:snow")) && !(isSnowy && String.valueOf(upBlock).contains("_top"))) {
                quad.spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
