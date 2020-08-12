package com.robertx22.age_of_exile.database.data.stats.name_regex;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.types.bonus_dmg_to_status_affected.BonusDmgToStatusAffected;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public class StatusEffectBonusDmgRegex extends StatNameRegex {

    @Override
    public String getStatNameRegex(ModType type, Stat stat, float v1, float v2) {

        BonusDmgToStatusAffected s = (BonusDmgToStatusAffected) stat;

        if (v1 > 0) {
            return VALUE + " " + NAME;
        } else {
            return VALUE + " " + NAME;
        }

    }
}
