package net.merchantpug.fhe.mixin;

import net.merchantpug.fhe.FiveHourEnergy;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEffectInstance.class)
public class Mixin_MobEffectInstance {
    @Shadow @Final private Holder<MobEffect> effect;

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/effect/MobEffectInstance;tickDownDuration()I"), cancellable = true)
    private void bovinesandbuttercups$lockDuration(LivingEntity living, Runnable runnable, CallbackInfoReturnable<Boolean> cir) {
        if (effect.is(FiveHourEnergy.FIVE_HOUR_ENERGY_EFFECT) || effect.is(FiveHourEnergy.FIVE_HOUR_SLEEP_EFFECT))
            cir.setReturnValue(true);
    }
}
