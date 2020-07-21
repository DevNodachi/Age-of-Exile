package com.robertx22.mine_and_slash.loot.blueprints.bases;

import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.mine_and_slash.loot.blueprints.SkillGemBlueprint;

public class SpellPart extends BlueprintPart<BaseSpell> {

    public SpellPart(SkillGemBlueprint blueprint) {
        super(blueprint);
    }

    @Override
    protected BaseSpell generateIfNull() {
        return SlashRegistry.Spells()
            .random();
    }
}
