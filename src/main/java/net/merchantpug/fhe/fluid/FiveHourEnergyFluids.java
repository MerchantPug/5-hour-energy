package net.merchantpug.fhe.fluid;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.FlowingFluid;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;

public class FiveHourEnergyFluids {
    public static final FlowingFluid FLOWING_FIVE_HOUR_ENERGY = new FiveHourEnergyFluid.Flowing();
    public static final FlowingFluid FIVE_HOUR_ENERGY = new FiveHourEnergyFluid.Source();
    public static final FluidType FIVE_HOUR_ENERGY_FLUID_TYPE = new FluidType(FluidType.Properties.create()
            .descriptionId("block.fivehourenergy.five_hour_energy")
            .fallDistanceModifier(0.0F)
            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
            .sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH)
            .canExtinguish(true).supportsBoating(true));

    public static final FlowingFluid FLOWING_FIVE_HOUR_SLEEP = new FiveHourSleepFluid.Flowing();
    public static final FlowingFluid FIVE_HOUR_SLEEP = new FiveHourSleepFluid.Source();
    public static final FluidType FIVE_HOUR_SLEEP_FLUID_TYPE = new FluidType(FluidType.Properties.create()
            .descriptionId("block.fivehourenergy.five_hour_sleep")
            .fallDistanceModifier(0.0F)
            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
            .sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH)
            .canExtinguish(true).supportsBoating(true));
}
