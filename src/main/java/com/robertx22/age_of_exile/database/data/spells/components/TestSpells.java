package com.robertx22.age_of_exile.database.data.spells.components;

import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.activated_on.Activation;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.spells.calc.ValueCalculationData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;

public class TestSpells {

    public static Spell TEST_SPELL = Spell.Builder.of("test")
        .addEffect(Activation.ON_TICK, ComponentPart.Builder.particleOnTick(3D, ParticleTypes.ITEM_SNOWBALL, 5D, 0.5D))
        .addEffect(Activation.ON_CAST, ComponentPart.Builder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.DIAMOND_SWORD, 5D, 1D, ModRegistry.ENTITIES.SIMPLE_PROJECTILE, 60D, false)))
        .addEffect(Activation.ON_HIT, ComponentPart.Builder.damage(ValueCalculationData.base(500), Elements.Nature))
        .build();

}
