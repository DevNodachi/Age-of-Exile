package com.robertx22.age_of_exile.database.data.spells.contexts;

import com.robertx22.age_of_exile.database.data.spells.entities.dataack_entities.EntitySavedSpellData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class SpellCtx {

    // the entity the effect came from, player summons fireball. fireball hits enemy, dmg comes from fireball
    public Entity sourceEntity;

    public World world;
    public LivingEntity caster;

    @Nullable
    public LivingEntity target;

    public BlockPos pos;
    public Vec3d vecPos;

    public EntitySavedSpellData calculatedSpellData;

    private SpellCtx(Entity sourceEntity, LivingEntity caster, LivingEntity target, BlockPos pos, EntitySavedSpellData calculatedSpellData) {
        this.sourceEntity = sourceEntity;
        this.caster = caster;
        this.target = target;
        this.pos = pos;
        this.calculatedSpellData = calculatedSpellData;
        this.world = caster.world;
        this.vecPos = new Vec3d(pos.getX(), pos.getY(), pos.getZ());

    }

    public static SpellCtx onCast(LivingEntity caster, EntitySavedSpellData data) {
        Objects.requireNonNull(caster);
        Objects.requireNonNull(data);
        return new SpellCtx(caster, caster, caster, caster.getBlockPos(), data);
    }

    public static SpellCtx onHit(LivingEntity caster, Entity sourceEntity, LivingEntity target, EntitySavedSpellData data) {
        Objects.requireNonNull(caster);
        Objects.requireNonNull(sourceEntity);
        Objects.requireNonNull(data);
        return new SpellCtx(sourceEntity, caster, target, target.getBlockPos(), data);
    }

    public static SpellCtx onTick(LivingEntity caster, Entity sourceEntity, EntitySavedSpellData data) {
        Objects.requireNonNull(caster);
        Objects.requireNonNull(sourceEntity);
        Objects.requireNonNull(data);
        return new SpellCtx(sourceEntity, caster, null, sourceEntity.getBlockPos(), data);
    }

}
