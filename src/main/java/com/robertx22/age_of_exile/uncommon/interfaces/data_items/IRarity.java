package com.robertx22.age_of_exile.uncommon.interfaces.data_items;

import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;

public interface IRarity<R extends Rarity> {

    public int getRarityRank();

    public R getRarity();

    public default boolean isUnique() {
        return this.getRarityRank() == Unique;
    }

    int Common = 0;
    int Magical = 1;
    int Rare = 2;
    int Epic = 3;
    int Legendary = 4;
    int Mythic = 11;

    int Relic = 5;
    int Antique = 6;

    int Unique = 10;
    int Boss = 9;

    String COMMON_ID = "common";
    String MAGICAL_ID = "magical";
    String RARE_ID = "rare";
    String EPIC_ID = "epic";
    String LEGENDARY_ID = "legendary";
    String MYTHIC_ID = "mythic";
    String RELID_ID = "relic";

}
