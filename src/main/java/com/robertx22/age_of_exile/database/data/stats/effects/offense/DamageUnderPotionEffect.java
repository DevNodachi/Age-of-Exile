package com.robertx22.age_of_exile.database.data.stats.effects.offense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import net.minecraft.entity.effect.StatusEffect;

public class DamageUnderPotionEffect extends BaseDamageEffect {
    StatusEffect status;

    public DamageUnderPotionEffect(StatusEffect status) {
        this.status = status;
    }

    @Override
    public int GetPriority() {
        return Priority.Second.priority;
    }

    @Override
    public EffectSides Side() {
        return EffectSides.Source;
    }

    @Override
    public DamageEffect activate(DamageEffect effect, StatData data, Stat stat) {
        effect.percentIncrease += data.getAverageValue();
        return effect;
    }

    @Override
    public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
        return effect.source.hasStatusEffect(status);
    }

}

