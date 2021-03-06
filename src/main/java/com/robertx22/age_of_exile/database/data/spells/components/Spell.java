package com.robertx22.age_of_exile.database.data.spells.components;

import com.google.gson.Gson;
import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.data.IGUID;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect;
import com.robertx22.age_of_exile.database.data.spells.entities.EntitySavedSpellData;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellModEnum;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.CalculatedSpellData;
import com.robertx22.age_of_exile.saveclasses.unit.ResourcesData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.age_of_exile.vanilla_mc.packets.NoManaPacket;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Spell implements IGUID, IAutoGson<Spell>, ISerializedRegistryEntry<Spell>, IAutoLocName {
    public static Spell SERIALIZER = new Spell();

    public static String DEFAULT_EN_NAME = "default_entity_name";

    public static Gson GSON = new Gson();

    public String identifier = "";
    public AttachedSpell attached = new AttachedSpell();
    public SpellConfiguration config = new SpellConfiguration();

    public AttachedSpell getAttached() {
        return attached;
    }

    public SpellConfiguration getConfig() {
        return config;
    }

    public void validate() {
        for (ComponentPart x : this.attached.getAllComponents()) {
            x.validate();
        }
    }

    public final Identifier getIconLoc() {
        return getIconLoc(GUID());
    }

    public static final Identifier getIconLoc(String id) {
        return new Identifier(Ref.MODID, "textures/gui/spells/icons/" + id + ".png");
    }

    public final void onCastingTick(SpellCastContext ctx) {

        int timesToCast = (int) ctx.calcData.getSpell()
            .getConfig().times_to_cast;

        if (timesToCast > 1) {

            int castTimeTicks = (int) ctx.calcData.getSpell()
                .getConfig().cast_time_ticks;

            // if i didnt do this then cast time reduction would reduce amount of spell hits.
            int castEveryXTicks = castTimeTicks / timesToCast;

            if (ctx.isLastCastTick) {
                this.cast(ctx);
            } else {
                if (ctx.ticksInUse > 0 && ctx.ticksInUse % castEveryXTicks == 0) {
                    this.cast(ctx);
                }
            }

        } else if (timesToCast < 1) {
            System.out.println("Times to cast spell is: " + timesToCast + " . this seems like a bug.");
        }

    }

    public void cast(SpellCastContext ctx) {
        LivingEntity caster = ctx.caster;

        EntitySavedSpellData data = EntitySavedSpellData.create(caster, this, ctx.spellConfig);

        ctx.castedThisTick = true;

        if (this.config.swing_arm) {
            caster.swingHand(Hand.MAIN_HAND);
        }

        attached.onCast(SpellCtx.onCast(caster, data));
    }

    public final int getCooldownInSeconds(SpellCastContext ctx) {

        float multi = ctx.spellConfig.getMulti(SpellModEnum.COOLDOWN);

        float ticks = config.cooldown_ticks * multi;

        float sec = ticks / 20F;

        if (sec < 1) {
            return 1; // cant go lower than 1 second!!!
        }

        return (int) sec;
    }

    public final float getUseDurationInSeconds(SpellCastContext ctx) {
        float multi = ctx.spellConfig.getMulti(SpellModEnum.CAST_SPEED);
        return (config.cast_time_ticks * multi) / 20;
    }

    @Override
    public String GUID() {
        return identifier;
    }

    public void spendResources(SpellCastContext ctx) {
        ctx.data.getResources()
            .modify(getManaCostCtx(ctx));
    }

    public ResourcesData.Context getManaCostCtx(SpellCastContext ctx) {

        float cost = 0;

        cost += this.getCalculatedManaCost(ctx);

        return new ResourcesData.Context(
            ctx.data, ctx.caster, ResourcesData.Type.MANA, cost, ResourcesData.Use.SPEND);
    }

    public boolean canCast(SpellCastContext ctx) {

        LivingEntity caster = ctx.caster;

        if (caster instanceof PlayerEntity == false) {
            return true;
        }

        if (((PlayerEntity) caster).isCreative()) {
            return true;
        }
        if (this.config.passive_config.is_passive) {
            return false;
        }

        if (!caster.world.isClient) {

            EntityCap.UnitData data = Load.Unit(caster);

            if (data != null) {

                ResourcesData.Context rctx = getManaCostCtx(ctx);

                if (data.getResources()
                    .hasEnough(rctx)) {

                    if (!getConfig().castingWeapon.predicate.predicate.test(caster)) {
                        return false;
                    }

                    if (!ctx.spellsCap.getLearnedSpells(ctx.caster)
                        .contains(this)) {
                        return false;
                    }

                    return true;
                } else {
                    if (caster instanceof ServerPlayerEntity) {
                        Packets.sendToClient((PlayerEntity) caster, new NoManaPacket());
                    }
                }
            }
        }
        return false;

    }

    public final int getCalculatedManaCost(SpellCastContext ctx) {
        float manaCostMulti = ctx.spellConfig.getMulti(SpellModEnum.MANA_COST);
        return (int) Mana.getInstance()
            .scale(getConfig().mana_cost * manaCostMulti, ctx.calcData.level);
    }

    public boolean isPassive() {
        return getConfig().passive_config.is_passive;
    }

    public final List<Text> GetTooltipString(TooltipInfo info, CalculatedSpellData data) {

        SpellCastContext ctx = new SpellCastContext(info.player, 0, data);

        List<Text> list = new ArrayList<>();

        TooltipUtils.addEmpty(list);

        if (Screen.hasShiftDown()) {
            list.addAll(attached
                .getTooltip());
        }

        TooltipUtils.addEmpty(list);

        if (this.isPassive()) {
            list.add(Words.PassiveSkill.locName()
                .formatted(Formatting.GOLD));
            list.add(Words.PassiveDesc.locName()
                .formatted(Formatting.GOLD));
        }

        if (!isPassive()) {
            list.add(new LiteralText(Formatting.BLUE + "Mana Cost: " + getCalculatedManaCost(ctx)));
        }
        list.add(new LiteralText(Formatting.YELLOW + "Cooldown: " + getCooldownInSeconds(ctx) + "s"));
        if (!isPassive()) {
            list.add(new LiteralText(Formatting.GREEN + "Cast time: " + getUseDurationInSeconds(ctx) + "s"));
        }

        TooltipUtils.addEmpty(list);

        if (!isPassive()) {
            list.add(getConfig().castingWeapon.predicate.text);
        }

        TooltipUtils.addEmpty(list);

        if (this.config.times_to_cast > 1) {
            TooltipUtils.addEmpty(list);
            list.add(new LiteralText("Casted " + config.times_to_cast + " times during channel.").formatted(Formatting.RED));

        }

        if (Screen.hasShiftDown()) {

            Set<ExileEffect> effect = new HashSet<>();

            try {
                this.getAttached()
                    .getAllComponents()
                    .forEach(x -> {
                        x.acts.forEach(a -> {
                            if (a.has(MapField.EXILE_POTION_ID)) {
                                effect.add(a.getExileEffect());
                            }
                        });
                    });
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                effect.forEach(x -> list.addAll(x.GetTooltipString(info)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        TooltipUtils.removeDoubleBlankLines(list);

        return list;

    }

    @Override
    public Class<Spell> getClassForSerialization() {
        return Spell.class;
    }

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.SPELL;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Spells;
    }

    @Override
    public String locNameLangFileGUID() {
        return Ref.MODID + ".spell." + GUID();
    }

    public transient String locName;

    @Override
    public String locNameForLangFile() {
        return locName;
    }

}
