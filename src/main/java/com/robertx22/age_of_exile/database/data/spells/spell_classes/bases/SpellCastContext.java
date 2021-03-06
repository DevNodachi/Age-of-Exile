package com.robertx22.age_of_exile.database.data.spells.spell_classes.bases;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.capability.player.PlayerSpellCap;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.saveclasses.item_classes.CalculatedSpellData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellStatsCalcEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

;

public class SpellCastContext {

    public final LivingEntity caster;
    public final EntityCap.UnitData data;
    public final PlayerSpellCap.ISpellsCap spellsCap;
    public final int ticksInUse;
    public final Spell spell;
    public boolean isLastCastTick;
    public boolean castedThisTick = false;
    public CalculatedSpellData calcData;

    public SpellStatsCalcEffect.CalculatedSpellConfiguration spellConfig;

    private void calcSpellData() {
        this.calcData = CalculatedSpellData.create(caster, spell, spellConfig);
    }

    public SpellCastContext(LivingEntity caster, int ticksInUse, CalculatedSpellData spell) {
        this(caster, ticksInUse, spell.getSpell());
    }

    public SpellCastContext(LivingEntity caster, int ticksInUse, Spell spell) {
        this.caster = caster;
        this.ticksInUse = ticksInUse;

        this.spell = spell;

        this.data = Load.Unit(caster);

        SpellStatsCalcEffect effect = new SpellStatsCalcEffect(caster, spell.GUID());
        effect.Activate();
        this.spellConfig = effect.data;

        if (caster instanceof PlayerEntity) {
            this.spellsCap = Load.spells((PlayerEntity) caster);
        } else {
            this.spellsCap = new PlayerSpellCap.DefaultImpl();
        }

        calcSpellData();

        if (spell != null) {
            int castTicks = (int) this.calcData.getSpell()
                .getConfig().cast_time_ticks;
            this.isLastCastTick = castTicks == ticksInUse;
        }

    }
}
