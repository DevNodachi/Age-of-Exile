package com.robertx22.age_of_exile.database.data.stats.types.loot;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class IncreasedItemQuantity extends Stat {

    private IncreasedItemQuantity() {
    }

    public static IncreasedItemQuantity getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public Elements getElement() {
        return null;
    }

    @Override
    public String locDescForLangFile() {
        return "Increases Loot Drop amount";
    }

    @Override
    public String GUID() {
        return "increased_quantity";
    }

    @Override
    public String locNameForLangFile() {
        return "Item Quantity";
    }

    private static class SingletonHolder {
        private static final IncreasedItemQuantity INSTANCE = new IncreasedItemQuantity();
    }
}
