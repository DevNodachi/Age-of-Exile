package com.robertx22.age_of_exile.database.data.stats.effects.offense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.age_of_exile.database.data.stats.types.generated.SpecificWeaponDamage;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;

public class SpecificWeaponDamageEffect extends BaseDamageEffect {

    private SpecificWeaponDamageEffect() {
    }

    public static SpecificWeaponDamageEffect getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public int GetPriority() {
        return Priority.First.priority;
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
        if (stat instanceof SpecificWeaponDamage) {
            SpecificWeaponDamage weapon = (SpecificWeaponDamage) stat;
            return weapon.weaponType()
                .equals(effect.weaponType);
        }
        return false;
    }

    private static class SingletonHolder {
        private static final SpecificWeaponDamageEffect INSTANCE = new SpecificWeaponDamageEffect();
    }
}