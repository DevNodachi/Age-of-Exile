package com.robertx22.age_of_exile.mobs.mages;

import com.robertx22.age_of_exile.aoe_data.database.spells.Spells;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.world.World;

public class WaterMage extends BaseMage {

    public WaterMage(EntityType<? extends SkeletonEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public Spell getSpell() {
        return SlashRegistry.Spells()
            .get(Spells.FROSTBALL_ID);
    }
}
