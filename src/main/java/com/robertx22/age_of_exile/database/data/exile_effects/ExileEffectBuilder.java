package com.robertx22.age_of_exile.database.data.exile_effects;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public class ExileEffectBuilder {

    private ExileEffect effect = new ExileEffect();

    public static ExileEffectBuilder of(Integer num, String locname) {
        ExileEffectBuilder b = new ExileEffectBuilder();
        b.effect.id = num + "";
        b.effect.locName = locname;
        return b;
    }

    public ExileEffectBuilder stat(OptScaleExactStat stat) {
        this.effect.stats.add(stat);
        return this;
    }

    public ExileEffectBuilder vanillaStat(VanillaStatData stat) {
        this.effect.mc_stats.add(stat);
        return this;
    }

    public ExileEffectBuilder spell(Spell stat) {
        this.effect.spell = stat.getAttached();
        return this;
    }


    public ExileEffectBuilder stat(float first, Stat stat, ModType type) {
        OptScaleExactStat data = new OptScaleExactStat(first, stat, type);
        data.scaleToLevel = stat.getScaling() == StatScaling.LINEAR;
        this.effect.stats.add(data);
        return this;
    }

    public ExileEffect build() {
        effect.addToSerializables();
        return effect;
    }

}
