package net.merchantpug.fhe;

import net.merchantpug.fhe.fluid.FiveHourEnergyFluids;
import net.merchantpug.fhe.item.DrinkableBucketItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class FiveHourEnergyBlocksItems {
    public static final Block FIVE_HOUR_ENERGY_BLOCK = new LiquidBlock(FiveHourEnergyFluids.FIVE_HOUR_ENERGY, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_RED).replaceable().noCollission().strength(100.0F).pushReaction(PushReaction.DESTROY).noLootTable().liquid().sound(SoundType.EMPTY));
    public static final BucketItem FIVE_HOUR_ENERGY_BUCKET = new DrinkableBucketItem(FiveHourEnergyFluids.FIVE_HOUR_ENERGY, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).food(new FoodProperties.Builder().nutrition(0).saturationModifier(0.0F).usingConvertsTo(Items.BUCKET).alwaysEdible().effect(() -> new MobEffectInstance(FiveHourEnergy.FIVE_HOUR_ENERGY_EFFECT, 360000), 1.0F).build()));

    public static final Block FIVE_HOUR_SLEEP_BLOCK = new LiquidBlock(FiveHourEnergyFluids.FIVE_HOUR_SLEEP, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).replaceable().noCollission().strength(100.0F).pushReaction(PushReaction.DESTROY).noLootTable().liquid().sound(SoundType.EMPTY));
    public static final BucketItem FIVE_HOUR_SLEEP_BUCKET = new DrinkableBucketItem(FiveHourEnergyFluids.FIVE_HOUR_SLEEP, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).food(new FoodProperties.Builder().nutrition(0).saturationModifier(0.0F).usingConvertsTo(Items.BUCKET).alwaysEdible().effect(() -> new MobEffectInstance(FiveHourEnergy.FIVE_HOUR_SLEEP_EFFECT, 360000), 1.0F).build()));
}
