package com.robertx22.age_of_exile.database.data.spells.components.conditions;

import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.contexts.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;

import java.util.Arrays;

import static com.robertx22.age_of_exile.database.data.spells.map_fields.MapField.TICK_RATE;

public class TickRateCondition extends EffectCondition {

    public TickRateCondition() {
        super(Arrays.asList(MapField.TICK_RATE));
    }

    @Override
    public boolean canActivate(SpellCtx ctx, MapHolder data) {
        int ticks = data.get(MapField.TICK_RATE)
            .intValue();
        return ctx.sourceEntity != null && ctx.sourceEntity.age % ticks == 0;
    }

    public MapHolder create(Double ticks) {
        MapHolder d = new MapHolder();
        d.type = GUID();
        d.put(TICK_RATE, ticks);
        return d;
    }

    @Override
    public String GUID() {
        return "x_ticks_condition";
    }
}
