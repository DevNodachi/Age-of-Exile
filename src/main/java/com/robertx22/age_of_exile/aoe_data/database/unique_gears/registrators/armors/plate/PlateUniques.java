package com.robertx22.age_of_exile.aoe_data.database.unique_gears.registrators.armors.plate;

import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.adders.BasePlateArmors;
import com.robertx22.age_of_exile.aoe_data.database.spells.Spells;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.spell_related.GiveSpellStat;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Dexterity;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.EffectImmunity;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalPenetration;
import com.robertx22.age_of_exile.database.data.stats.types.generated.SpecificWeaponDamage;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuantity;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.RegeneratePercentStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Lifesteal;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShieldRegen;
import com.robertx22.age_of_exile.database.registrators.LevelRanges;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

import java.util.Arrays;

public class PlateUniques implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.MS_REG_ARMOR_CHEST,
            "ms_armor_chest",
            "The Unfading",
            "If only they knew that the inscription referred to the armor.",
            BasePlateArmors.CHESTS.get(LevelRanges.MIDDLE))
            .stats(Arrays.asList(
                new StatModifier(20, 100, Armor.getInstance(), ModType.LOCAL_INCREASE),
                new StatModifier(20, 60, MagicShieldRegen.getInstance(), ModType.LOCAL_INCREASE)
            ))
            .build();

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.BEAST_BLOOD,
            "beast_blood",
            "Blood of the Beast",
            "Accept the blood, suffer the Sacrifice. Be reborn anew.",
            BasePlateArmors.CHESTS.get(LevelRanges.ENDGAME))
            .stats(Arrays.asList(
                new StatModifier(5, 15, Health.getInstance(), ModType.FLAT),
                new StatModifier(1, 1, RegeneratePercentStat.HEALTH, ModType.FLAT),
                new StatModifier(1, 1, EffectImmunity.HUNGER, ModType.FLAT),
                new StatModifier(-15, -5, TreasureQuantity.getInstance(), ModType.FLAT)
            ))
            .build();

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.FIFTH_RIDER_HELMET,
            "fifth_rider",
            "The Fifth Rider",
            "The only thing that's missing is the horse.",
            BasePlateArmors.HELMETS.get(LevelRanges.HIGH))
            .stats(Arrays.asList(
                new StatModifier(20, 80, Armor.getInstance(), ModType.LOCAL_INCREASE),
                new StatModifier(15, 25, new SpecificWeaponDamage(WeaponTypes.Sword), ModType.FLAT),
                new StatModifier(10, 25, new ElementalPenetration(Elements.Fire), ModType.FLAT),
                new StatModifier(10, 25, new ElementalPenetration(Elements.Thunder), ModType.FLAT)
            ))
            .build();

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.GLUTTONY_HELMET,
            "gluttony_helmet",
            "Gluttony",
            "If only hunger could be sated, I would not be doomed.",
            BasePlateArmors.HELMETS.get(LevelRanges.LOW))
            .stats(Arrays.asList(
                new StatModifier(5, 15, Health.getInstance(), ModType.FLAT),
                new StatModifier(50, 100, Lifesteal.getInstance(), ModType.LOCAL_INCREASE)
            ))
            .build();

        UniqueGearBuilder.of(
            ModRegistry.UNIQUE_GEARS.KINGMAKER_CHEST,
            "kingmaker",
            "Kingmaker",
            "Alone we falter, together we rise.",
            BasePlateArmors.CHESTS.get(LevelRanges.HIGH))
            .stats(Arrays.asList(
                new StatModifier(2, 5, Health.getInstance(), ModType.FLAT),
                new StatModifier(25, 50, CriticalDamage.getInstance(), ModType.FLAT),
                new StatModifier(1, 1, new GiveSpellStat(Spells.BRAVERY_ID), ModType.FLAT),
                new StatModifier(-1, -1, Dexterity.INSTANCE, ModType.FLAT)
            ))
            .build();

    }
}
