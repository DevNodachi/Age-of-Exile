package com.robertx22.mine_and_slash.database.registrators;

import com.robertx22.exiled_lib.registry.ISlashRegistryInit;
import com.robertx22.mine_and_slash.database.data.StatModifier;
import com.robertx22.mine_and_slash.database.data.affixes.Affix;
import com.robertx22.mine_and_slash.database.data.mob_affixes.base.MobAffix;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.WeaponDamage;
import com.robertx22.mine_and_slash.database.data.stats.types.offense.CriticalHit;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.Health;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;

import java.util.ArrayList;
import java.util.List;

public class MobAffixes implements ISlashRegistryInit {

    public static MobAffix EMPTY = new MobAffix("empty", "empty", Affix.Type.prefix);

    public static MobAffix COLD = new MobAffix("cold", "Cold", Affix.Type.prefix).setMods(new StatModifier(4, 4, new WeaponDamage(Elements.Water), ModType.FLAT), new StatModifier(-100, -100, CriticalHit.getInstance(), ModType.FLAT));
    public static MobAffix FLAMING = new MobAffix("flaming", "Flaming", Affix.Type.prefix).setMods(new StatModifier(4, 4, new WeaponDamage(Elements.Fire), ModType.FLAT), new StatModifier(-100, -100, CriticalHit.getInstance(), ModType.FLAT));
    public static MobAffix LIGHTNING = new MobAffix("lightning", "Lightning", Affix.Type.prefix).setMods(new StatModifier(4, 4, new WeaponDamage(Elements.Thunder), ModType.FLAT), new StatModifier(-100, -100, CriticalHit.getInstance(), ModType.FLAT));
    public static MobAffix VENOMOUS = new MobAffix("venom", "Venomous", Affix.Type.prefix).setMods(new StatModifier(4, 4, new WeaponDamage(Elements.Nature), ModType.FLAT), new StatModifier(-100, -100, CriticalHit.getInstance(), ModType.FLAT));

    public static MobAffix OF_RUIN = new MobAffix("of_ruin", "Of Ruin", Affix.Type.suffix).setMods(new StatModifier(4, 4, new WeaponDamage(Elements.Physical), ModType.FLAT), new StatModifier(-100, -100, CriticalHit.getInstance(), ModType.FLAT));
    public static MobAffix OF_VITALITY = new MobAffix("of_vit", "Of Vitality", Affix.Type.suffix).setMods(new StatModifier(10, 10, Health.getInstance(), ModType.FLAT));

    @Override
    public void registerAll() {

        List<MobAffix> all = new ArrayList<>();

        all.add(COLD);
        all.add(FLAMING);
        all.add(LIGHTNING);
        all.add(VENOMOUS);

        all.add(OF_RUIN);
        all.add(OF_VITALITY);

        all.forEach(x -> x.addToSerializables());

    }
}
