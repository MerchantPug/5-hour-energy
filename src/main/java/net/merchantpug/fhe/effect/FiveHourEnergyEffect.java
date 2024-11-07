package net.merchantpug.fhe.effect;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.EffectCure;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FiveHourEnergyEffect extends MobEffect {
    private final EffectCure cure;
    private final EffectCure curedBy;

    public FiveHourEnergyEffect(MobEffectCategory category, int color, EffectCure cure, EffectCure curedBy) {
        super(category, color);
        this.cure = cure;
        this.curedBy = curedBy;
    }

    @Override
    public void onEffectStarted(LivingEntity living, int amplifier) {
        List<MobEffectInstance> instances = new ArrayList<>(living.getActiveEffects());
        if (instances.stream().anyMatch(mobEffectInstance -> mobEffectInstance.getCures().contains(cure))) {
            for (Holder<MobEffect> holder : instances.stream().map(MobEffectInstance::getEffect).toList()) {
                if (holder.value() == this)
                    living.removeEffect(holder);
            }
            living.removeEffectsCuredBy(cure);
        }
    }

    @Override
    public void fillEffectCures(Set<EffectCure> cures, MobEffectInstance instance) {
        cures.add(curedBy);
    }
}
