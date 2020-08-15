package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.mob.MagmaCubeEntity;

public class MobAttributes {

    public static void register() {
        FabricDefaultAttributeRegistry.register(ModRegistry.ENTITIES.ARCANE_SLIME, MagmaCubeEntity.createMagmaCubeAttributes());

    }
}
