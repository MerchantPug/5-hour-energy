package net.merchantpug.fhe.client;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import net.merchantpug.fhe.FiveHourEnergy;
import net.merchantpug.fhe.fluid.FiveHourEnergyFluids;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

public class FiveHourEnergyClient {
    @EventBusSubscriber(modid = FiveHourEnergy.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ModEvents {
        @SubscribeEvent
        public static void registerExtensions(RegisterClientExtensionsEvent event) {
            event.registerFluidType(new IClientFluidTypeExtensions() {
                @Override
                public int getTintColor() {
                    return 0xC41133;
                }

                @Override
                public ResourceLocation getFlowingTexture() {
                    return ResourceLocation.withDefaultNamespace("block/water_flow");
                }

                @Override
                public ResourceLocation getStillTexture() {
                    return ResourceLocation.withDefaultNamespace("block/water_still");
                }

                @Override
                public ResourceLocation getOverlayTexture() {
                    return ResourceLocation.withDefaultNamespace("block/water_overlay");
                }

                @Override
                public ResourceLocation getRenderOverlayTexture(Minecraft mc) {
                    return FiveHourEnergy.asResource("textures/misc/five_hour_energy.png");
                }

                @Override
                public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape) {
                    float start = -8.0F;
                    float end = 96.0F;
                    if (camera.getEntity() instanceof LocalPlayer localplayer) {
                        end = end * Math.max(0.25F, localplayer.getWaterVision());
                        Holder<Biome> holder = localplayer.level().getBiome(localplayer.blockPosition());
                        if (holder.is(BiomeTags.HAS_CLOSER_WATER_FOG)) {
                            end *= 0.85F;
                        }
                    }

                    if (end > farDistance) {
                        end = farDistance;
                        shape = FogShape.CYLINDER;
                    }
                    RenderSystem.setShaderFogStart(start);
                    RenderSystem.setShaderFogEnd(end);
                    RenderSystem.setShaderFogShape(shape);
                    RenderSystem.setShaderFogColor(0.76862745098F, 0.06666666666F, 0.2F);
                }
            }, FiveHourEnergyFluids.FIVE_HOUR_ENERGY_FLUID_TYPE);

            event.registerFluidType(new IClientFluidTypeExtensions() {
                @Override
                public int getTintColor() {
                    return 0x8d5fe9;
                }

                @Override
                public ResourceLocation getFlowingTexture() {
                    return ResourceLocation.withDefaultNamespace("block/water_flow");
                }

                @Override
                public ResourceLocation getStillTexture() {
                    return ResourceLocation.withDefaultNamespace("block/water_still");
                }

                @Override
                public ResourceLocation getOverlayTexture() {
                    return ResourceLocation.withDefaultNamespace("block/water_overlay");
                }

                @Override
                public ResourceLocation getRenderOverlayTexture(Minecraft mc) {
                    return FiveHourEnergy.asResource("textures/misc/five_hour_sleep.png");
                }

                @Override
                public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape) {
                    float start = -8.0F;
                    float end = 48.0F;
                    if (camera.getEntity() instanceof LocalPlayer localplayer) {
                        end = end * Math.max(0.25F, localplayer.getWaterVision());
                        Holder<Biome> holder = localplayer.level().getBiome(localplayer.blockPosition());
                        if (holder.is(BiomeTags.HAS_CLOSER_WATER_FOG)) {
                            end *= 0.85F;
                        }
                    }

                    if (end > farDistance) {
                        end = farDistance;
                        shape = FogShape.CYLINDER;
                    }
                    RenderSystem.setShaderFogStart(start);
                    RenderSystem.setShaderFogEnd(end);
                    RenderSystem.setShaderFogShape(shape);
                    RenderSystem.setShaderFogColor(0.55294117647F, 0.3725490196F, 0.91372549019F);
                }
            }, FiveHourEnergyFluids.FIVE_HOUR_SLEEP_FLUID_TYPE);
        }
    }
}
