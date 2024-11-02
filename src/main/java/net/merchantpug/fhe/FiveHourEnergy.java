package net.merchantpug.fhe;

import net.merchantpug.fhe.effect.FiveHourEnergyEffect;
import net.merchantpug.fhe.fluid.FiveHourEnergyFluids;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.EffectCure;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.level.SleepFinishedTimeEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.apache.logging.log4j.LogManager;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.apache.logging.log4j.Logger;

@Mod(FiveHourEnergy.MODID)
public class FiveHourEnergy
{
    public static final String MODID = "fivehourenergy";
    private static final Logger LOGGER = LogManager.getLogger("Five Hour Energy");

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, MODID);

    public static final EffectCure FIVE_HOUR_ENERGY = EffectCure.get("fivehourenergy:five_hour_energy");
    public static final EffectCure FIVE_HOUR_SLEEP = EffectCure.get("fivehourenergy:five_hour_sleep");

    public static final Holder<MobEffect> FIVE_HOUR_ENERGY_EFFECT = MOB_EFFECTS.register("five_hour_energy", () -> new FiveHourEnergyEffect(MobEffectCategory.BENEFICIAL, 0xff3c19, FIVE_HOUR_ENERGY, FIVE_HOUR_SLEEP).addAttributeModifier(Attributes.MOVEMENT_SPEED, FiveHourEnergy.asResource("mob_effect.five_hour_energy.movement_speed"), 1.0, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL).addAttributeModifier(Attributes.MINING_EFFICIENCY, FiveHourEnergy.asResource("mob_effect.five_hour_energy.mining_efficiency"), 0.4, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    public static final Holder<MobEffect> FIVE_HOUR_SLEEP_EFFECT = MOB_EFFECTS.register("five_hour_sleep", () -> new FiveHourEnergyEffect(MobEffectCategory.HARMFUL, 0x954bd6, FIVE_HOUR_SLEEP, FIVE_HOUR_ENERGY).addAttributeModifier(Attributes.MOVEMENT_SPEED, FiveHourEnergy.asResource("mob_effect.five_hour_energy.movement_speed"), -0.4, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL).addAttributeModifier(Attributes.MINING_EFFICIENCY, FiveHourEnergy.asResource("mob_effect.five_hour_sleep.mining_efficiency"), -0.6, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    public FiveHourEnergy(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.GAME)
    public static class GameEvents {
        @SubscribeEvent
        public static void onSleepFinished(SleepFinishedTimeEvent event) {
            LevelAccessor level = event.getLevel();
            level.players().forEach(player -> {
                if (player.isSleepingLongEnough() && event.getNewTime() - level.getTimeOfDay(1.0F) > 5000) {
                    player.removeEffect(FIVE_HOUR_ENERGY_EFFECT);
                    player.displayClientMessage(Component.translatable("sleep.fivehourenergy.cancelled_out_energy"), true);
                }
            });
        }

        @SubscribeEvent
        public static void onEntityTick(EntityTickEvent.Post event) {
            if (event.getEntity() instanceof LivingEntity living) {
                if (living.getEyeInFluidType() == FiveHourEnergyFluids.FIVE_HOUR_ENERGY_FLUID_TYPE) {
                    living.removeEffectsCuredBy(FIVE_HOUR_ENERGY);
                    living.addEffect(new MobEffectInstance(FiveHourEnergy.FIVE_HOUR_ENERGY_EFFECT, 360000));
                } else if (living.getEyeInFluidType() == FiveHourEnergyFluids.FIVE_HOUR_SLEEP_FLUID_TYPE) {
                    living.removeEffectsCuredBy(FIVE_HOUR_SLEEP);
                    living.addEffect(new MobEffectInstance(FiveHourEnergy.FIVE_HOUR_SLEEP_EFFECT, 360000));
                }
            }
        }

        @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
        public static class ModEvents {
            @SubscribeEvent
            public static void register(RegisterEvent event) {
                event.register(Registries.FLUID, asResource("flowing_five_hour_energy"), () -> FiveHourEnergyFluids.FLOWING_FIVE_HOUR_ENERGY);
                event.register(Registries.FLUID, asResource("five_hour_energy"), () -> FiveHourEnergyFluids.FIVE_HOUR_ENERGY);
                event.register(NeoForgeRegistries.Keys.FLUID_TYPES, asResource("five_hour_energy"), () -> FiveHourEnergyFluids.FIVE_HOUR_ENERGY_FLUID_TYPE);

                event.register(Registries.FLUID, asResource("flowing_five_hour_sleep"), () -> FiveHourEnergyFluids.FLOWING_FIVE_HOUR_SLEEP);
                event.register(Registries.FLUID, asResource("five_hour_sleep"), () -> FiveHourEnergyFluids.FIVE_HOUR_SLEEP);
                event.register(NeoForgeRegistries.Keys.FLUID_TYPES, asResource("five_hour_sleep"), () -> FiveHourEnergyFluids.FIVE_HOUR_SLEEP_FLUID_TYPE);

                event.register(Registries.ITEM, asResource("five_hour_energy_bucket"), () -> FiveHourEnergyBlocksItems.FIVE_HOUR_ENERGY_BUCKET);
                event.register(Registries.ITEM, asResource("five_hour_sleep_bucket"), () -> FiveHourEnergyBlocksItems.FIVE_HOUR_SLEEP_BUCKET);

                event.register(Registries.BLOCK, asResource("five_hour_energy"), () -> FiveHourEnergyBlocksItems.FIVE_HOUR_ENERGY_BLOCK);
                event.register(Registries.BLOCK, asResource("five_hour_sleep"), () -> FiveHourEnergyBlocksItems.FIVE_HOUR_SLEEP_BLOCK);
            }

            @SubscribeEvent
            public static void fillCreativeModeTabs(BuildCreativeModeTabContentsEvent event) {
                if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
                    event.insertAfter(new ItemStack(Items.MILK_BUCKET), new ItemStack(FiveHourEnergyBlocksItems.FIVE_HOUR_ENERGY_BUCKET), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                    event.insertAfter(new ItemStack(FiveHourEnergyBlocksItems.FIVE_HOUR_ENERGY_BUCKET), new ItemStack(FiveHourEnergyBlocksItems.FIVE_HOUR_SLEEP_BUCKET), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                }
            }
        }
    }

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
