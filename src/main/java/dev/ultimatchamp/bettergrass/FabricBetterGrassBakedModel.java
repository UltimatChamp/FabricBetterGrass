package dev.ultimatchamp.bettergrass;

import me.pepperbell.continuity.client.util.SpriteCalculator;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;

import java.util.function.Supplier;

import dev.ultimatchamp.bettergrass.FabricBetterGrass.FabricBetterGrassConfig;

import static dev.ultimatchamp.bettergrass.FabricBetterGrass.FabricBetterGrassConfig.betterGrassMode;
import static dev.ultimatchamp.bettergrass.FabricBetterGrass.FabricBetterGrassConfig.tiles;

public class FabricBetterGrassBakedModel extends ForwardingBakedModel {

    public FabricBetterGrassBakedModel(BakedModel baseModel) {
        this.wrapped = baseModel;
    }

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
        context.pushTransform(quad -> {

            if (betterGrassMode.equals(FabricBetterGrassConfig.BetterGrassMode.OFF)) {
                return true;
            } else if (betterGrassMode.equals(FabricBetterGrassConfig.BetterGrassMode.FAST)) {
                if (quad.nominalFace().getAxis() != Direction.Axis.Y) {
                    if (belowTileBlock(blockView, pos)) {
                        spriteBake(quad, blockView.getBlockState(pos.up()), randomSupplier);
                        return true;
                    }

                    if (diagonallySame(blockView, state, pos, quad.nominalFace())) {
                        spriteBake(quad, blockView.getBlockState(pos), randomSupplier);
                        return true;
                    }
                }
            } else if (betterGrassMode.equals(FabricBetterGrassConfig.BetterGrassMode.FANCY)) {
                if (quad.nominalFace() != Direction.UP && quad.nominalFace() != Direction.DOWN) {

                    Direction face = quad.nominalFace();
                    if (diagonallySame(blockView, state, pos, face)) {
                        if (belowTileBlock(blockView, pos)) {
                            if (belowTileBlock(blockView, pos.offset(face).down())) {
                                if (blockView.getBlockState(pos.up()).getBlock().getDefaultState() == blockView.getBlockState(pos.offset(face)).getBlock().getDefaultState()) {
                                    spriteBake(quad, blockView.getBlockState(pos.up()), randomSupplier);
                                    return true;
                                } else {
                                    spriteBake(quad, blockView.getBlockState(pos.offset(face)), randomSupplier);
                                    return true;
                                }
                            } else {
                                return true;
                            }
                        }
                        if (!blockView.getBlockState(pos.offset(face)).isFullCube(blockView, pos.offset(face))) {
                            spriteBake(quad, blockView.getBlockState(pos), randomSupplier);
                            return true;
                        }
                        spriteBake(quad, blockView.getBlockState(pos), randomSupplier);
                        return true;
                    }

                }
            }
            return true;
        });
        super.emitBlockQuads(blockView, state, pos, randomSupplier, context);
        context.popTransform();
    }

        private static boolean canFullyConnect(BlockRenderView world, BlockState self, BlockPos selfPos, Direction direction) {
            return diagonallySame(world, self, selfPos, direction);
        }

    //block above has layers property
    private static boolean belowTileBlock(BlockRenderView world, BlockPos selfPos) {
        var upPos = selfPos.up();
        var up = world.getBlockState(upPos);
        for (String tile : tiles) {
            try {
                Block tileState = Registries.BLOCK.get(new Identifier(tile));
                if (up.getBlock().getDefaultState().equals(tileState.getDefaultState())) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    private static boolean diagonallySame(BlockRenderView world, BlockState self, BlockPos selfPos, Direction face) {
        var belowAdjacent = world.getBlockState(selfPos.offset(face).down());
        //var adjacentState = world.getBlockState(adjacentPos);
        //&& (adjacentState.isAir() || !adjacentState.isSideSolidFullSquare(world, adjacentPos, Direction.DOWN))
        return isSame(self, belowAdjacent);
    }

    private static boolean isSame(BlockState self, BlockState other) {
        return self.getBlock().getDefaultState() == other.getBlock().getDefaultState();
    }

    private static boolean spriteBake(MutableQuadView quad, BlockState state, Supplier<net.minecraft.util.math.random.Random> randomSupplier) {
        var sprite = SpriteCalculator.calculateSprite(state, Direction.UP, randomSupplier);
        if (sprite != null)
            quad.spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV);
        return sprite != null;
    }

}
