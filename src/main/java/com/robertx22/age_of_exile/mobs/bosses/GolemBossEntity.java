package com.robertx22.age_of_exile.mobs.bosses;

import com.robertx22.age_of_exile.mobs.bosses.bases.BossData;
import com.robertx22.age_of_exile.mobs.bosses.bases.IBossMob;
import com.robertx22.age_of_exile.mobs.bosses.channels.AngerChannel;
import com.robertx22.age_of_exile.mobs.bosses.channels.HealIfHitChannel;
import com.robertx22.library_of_exile.packets.defaults.EntityPacket;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.Packet;
import net.minecraft.predicate.block.BlockStatePredicate;
import net.minecraft.util.function.MaterialPredicate;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Arrays;

public class GolemBossEntity extends IronGolemEntity implements IBossMob {

    public static DefaultAttributeContainer.Builder createAttributes() {
        return HostileEntity.createHostileAttributes()
            .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 100)
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 25)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.17D)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4)
            .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 40.0D)
            .add(EntityAttributes.GENERIC_ARMOR, 0);
    }

    private static BlockPattern pattern;

    // copied from witherskullblock
    public static BlockPattern getBossPattern() {
        if (pattern == null) {
            pattern = BlockPatternBuilder.start()
                .aisle("^^^", "###", "~#~")
                .where('#', (cachedBlockPosition) -> {
                    return cachedBlockPosition.getBlockState()
                        .getBlock() == Blocks.IRON_BLOCK;
                })
                .where('^', CachedBlockPosition.matchesBlockState(BlockStatePredicate.forBlock(Blocks.BONE_BLOCK)))
                .where('~', CachedBlockPosition.matchesBlockState(MaterialPredicate.create(Material.AIR)))
                .build();
        }

        return pattern;
    }

    public BossData bossdata = new BossData(this, Arrays.asList(new AngerChannel(this), new HealIfHitChannel(this)));

    public GolemBossEntity(EntityType<? extends IronGolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean shouldAngerAt(LivingEntity entity) {
        return entity instanceof PlayerEntity;
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return EntityPacket.createPacket(this);
    }

    @Override
    public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
        return true;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.add(2, new WanderNearTargetGoal(this, 0.9D, 32.0F));
        this.goalSelector.add(2, new WanderAroundPointOfInterestGoal(this, 0.6D, false));
        this.goalSelector.add(4, new IronGolemWanderAroundGoal(this, 0.6D));
        this.goalSelector.add(5, new IronGolemLookGoal(this));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 20));
        this.goalSelector.add(8, new LookAroundGoal(this));

        this.targetSelector.add(2, new FollowTargetGoal<>(this, PlayerEntity.class, true));

    }

    @Override
    public void tick() {
        super.tick();

        if (!world.isClient) {
            this.bossdata.onTick();

            if (this.age % 20 == 0) {
                float max = getMaxHealth();
                this.heal(max * 0.002F);
            }
        }

    }

    @Override
    public boolean damage(DamageSource source, float amount) {

        if (bossdata.channelAction != null) {
            this.bossdata.channelAction.onHitBy(source, amount);
        }

        return super.damage(source, amount);
    }

    @Override
    public BossData getBossData() {
        return bossdata;

    }

    @Override
    protected boolean isDisallowedInPeaceful() {
        return true;
    }

    @Override
    public boolean canUsePortals() {
        return false;
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return true; // so peaceful removes it
    }

}


