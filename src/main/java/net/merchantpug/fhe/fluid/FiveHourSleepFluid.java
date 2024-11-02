package net.merchantpug.fhe.fluid;

import net.merchantpug.fhe.FiveHourEnergyBlocksItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.fluids.FluidType;

public abstract class FiveHourSleepFluid extends FlowingFluid {

    @Override
    public Fluid getFlowing() {
        return FiveHourEnergyFluids.FLOWING_FIVE_HOUR_SLEEP;
    }

    @Override
    public Fluid getSource() {
        return FiveHourEnergyFluids.FIVE_HOUR_SLEEP;
    }

    @Override
    protected boolean canConvertToSource(Level level) {
        return false;
    }

    @Override
    protected void beforeDestroyingBlock(LevelAccessor level, BlockPos pos, BlockState state) {
        BlockEntity blockentity = state.hasBlockEntity() ? level.getBlockEntity(pos) : null;
        Block.dropResources(state, level, pos, blockentity);
    }

    @Override
    protected int getSlopeFindDistance(LevelReader levelReader) {
        return 4;
    }

    @Override
    protected int getDropOff(LevelReader levelReader) {
        return 1;
    }

    @Override
    public Item getBucket() {
        return FiveHourEnergyBlocksItems.FIVE_HOUR_SLEEP_BUCKET;
    }

    @Override
    protected boolean canBeReplacedWith(FluidState fluidState, BlockGetter blockGetter, BlockPos blockPos, Fluid fluid, Direction direction) {
        return direction == Direction.DOWN && !fluid.getFluidType().isAir();
    }

    @Override
    public int getTickDelay(LevelReader levelReader) {
        return 20;
    }

    @Override
    protected float getExplosionResistance() {
        return 100.0F;
    }

    @Override
    public boolean isSame(Fluid fluid) {
        return fluid == FiveHourEnergyFluids.FIVE_HOUR_SLEEP || fluid == FiveHourEnergyFluids.FLOWING_FIVE_HOUR_SLEEP;
    }

    @Override
    protected BlockState createLegacyBlock(FluidState state) {
        return FiveHourEnergyBlocksItems.FIVE_HOUR_SLEEP_BLOCK.defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state));
    }

    @Override
    public FluidType getFluidType() {
        return FiveHourEnergyFluids.FIVE_HOUR_SLEEP_FLUID_TYPE;
    }

    public static class Source extends FiveHourSleepFluid {
        public Source() {
        }

        public int getAmount(FluidState state) {
            return 8;
        }

        public boolean isSource(FluidState state) {
            return true;
        }
    }

    public static class Flowing extends FiveHourSleepFluid {
        public Flowing() {
        }

        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        public int getAmount(FluidState state) {
            return state.getValue(LEVEL);
        }

        public boolean isSource(FluidState state) {
            return false;
        }
    }
}
