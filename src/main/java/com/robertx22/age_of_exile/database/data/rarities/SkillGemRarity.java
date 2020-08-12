package com.robertx22.age_of_exile.database.data.rarities;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.data.rarities.serialization.SerializedBaseRarity;
import com.robertx22.age_of_exile.database.data.rarities.serialization.SerializedSkillGemRarity;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;

public interface SkillGemRarity extends Rarity {

    MinMax statPercents();

    @Override
    default JsonObject toJson() {

        JsonObject json = this.getRarityJsonObject();

        json.add("stat_percents", statPercents().toJson());

        return json;
    }

    @Override
    default SkillGemRarity fromJson(JsonObject json) {

        SerializedBaseRarity baseRarity = this.baseSerializedRarityFromJson(json);

        SerializedSkillGemRarity rar = new SerializedSkillGemRarity(baseRarity);

        rar.statPerc = MinMax.getSerializer()
            .fromJson(json.get("stat_percents")
                .getAsJsonObject());

        return rar;
    }

}
