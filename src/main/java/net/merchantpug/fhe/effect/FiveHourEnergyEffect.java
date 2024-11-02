package net.merchantpug.fhe.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.EffectCure;

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
        living.removeEffectsCuredBy(cure);
    }

    @Override
    public void fillEffectCures(Set<EffectCure> cures, MobEffectInstance instance) {
        cures.add(curedBy);
    }
}
