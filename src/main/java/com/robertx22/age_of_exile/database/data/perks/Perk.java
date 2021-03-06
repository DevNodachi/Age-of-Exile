package com.robertx22.age_of_exile.database.data.perks;

import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.database.IByteBuf;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.ITooltipList;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AdvancementUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientTextureUtils;
import net.minecraft.advancement.Advancement;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class Perk implements ISerializedRegistryEntry<Perk>, IAutoGson<Perk>, ITooltipList, IByteBuf<Perk> {
    public static Perk SERIALIZER = new Perk();

    public PerkType type;
    public String identifier;
    public String spell = "";
    public String lock_under_adv = "";
    public String icon = "";
    public String one_of_a_kind = null;

    public boolean is_entry = false;
    public int lvl_req = 1;

    public List<OptScaleExactStat> stats = new ArrayList<>();

    @Override
    public Perk getFromBuf(PacketByteBuf buf) {
        Perk data = new Perk();

        data.type = PerkType.valueOf(buf.readString(50));
        data.identifier = buf.readString(100);
        data.spell = buf.readString(80);
        data.lock_under_adv = buf.readString(100);
        data.icon = buf.readString(150);
        data.one_of_a_kind = buf.readString(80);
        if (data.one_of_a_kind.isEmpty()) {
            data.one_of_a_kind = null;
        }

        data.is_entry = buf.readBoolean();
        data.lvl_req = buf.readInt();

        int s = buf.readInt();

        for (int i = 0; i < s; i++) {
            data.stats.add(OptScaleExactStat.SERIALIZER.getFromBuf(buf));
        }

        return data;
    }

    @Override
    public void toBuf(PacketByteBuf buf) {
        buf.writeString(type.name(), 100);
        buf.writeString(identifier, 100);
        buf.writeString(spell, 80);
        buf.writeString(lock_under_adv, 100);
        buf.writeString(icon, 150);

        buf.writeString(one_of_a_kind != null ? one_of_a_kind : "", 80);

        buf.writeBoolean(is_entry);
        buf.writeInt(lvl_req);

        buf.writeInt(stats.size());
        stats.forEach(x -> x.toBuf(buf));

    }

    transient Identifier cachedIcon = null;

    public Identifier getIcon() {
        if (cachedIcon == null) {
            Identifier id = new Identifier(icon);
            if (ClientTextureUtils.textureExists(id)) {
                cachedIcon = id;
            } else {
                cachedIcon = Stat.DEFAULT_ICON;
            }
        }
        return cachedIcon;
    }

    @Override
    public List<Text> GetTooltipString(TooltipInfo info) {
        List<Text> list = new ArrayList<>();
        Spell spell = getSpell();

        try {

            if (spell != null && !spell.GUID()
                .isEmpty()) {
                list.addAll(new SpellCastContext(info.player, 0, getSpell()).calcData.GetTooltipString(info));
            }

            stats.forEach(x -> list.addAll(x.GetTooltipString(info)));

            Advancement adv = AdvancementUtils.getAdvancement(info.player.world, new Identifier(lock_under_adv));

            if (adv != null) {
                list.add(new LiteralText("Needs advancement: ").append(adv.toHoverableText()));
            }

            if (this.one_of_a_kind != null) {
                list.add(new LiteralText("Can only have one Perk of this type: ").formatted(Formatting.GREEN));

                list.add(new TranslatableText(Ref.MODID + ".one_of_a_kind." + one_of_a_kind).formatted(Formatting.GREEN));
            }

            if (lvl_req > 1) {
                list.add(Words.RequiresLevel.locName()
                    .append(": " + lvl_req)
                    .formatted(Formatting.YELLOW));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public enum Connection {
        LINKED, BLOCKED, POSSIBLE
    }

    public enum PerkType {
        STAT(2, 24, 24, 39),
        SPECIAL(3, 24, 26, 77),
        MAJOR(1, 33, 33, 1),
        START(4, 23, 23, 115),
        SPELL_MOD(5, 26, 26, 153);

        int order;

        public int width;
        public int height;
        private int xoff;

        PerkType(int order, int width, int height, int xoff) {
            this.order = order;
            this.width = width;
            this.height = height;
            this.xoff = xoff;
        }

        public int getXOffset() {
            return xoff;
        }

    }

    public boolean isLockedUnderAdvancement() {
        return !lock_under_adv.isEmpty();
    }

    public PerkType getType() {
        return type;
    }

    public Spell getSpell() {
        return SlashRegistry.Spells()
            .get(spell);
    }

    public boolean isLockedToPlayer(PlayerEntity player) {

        if (Load.Unit(player)
            .getLevel() < lvl_req) {
            return true;
        }

        if (one_of_a_kind != null) {
            if (!one_of_a_kind.isEmpty()) {

                if (Load.perks(player)
                    .getAllAllocatedPerks()
                    .stream()
                    .filter(x -> {
                        return this.one_of_a_kind.equals(x.one_of_a_kind);
                    })
                    .count() > 0) {
                    return true;
                }
            }
        }
        if (isLockedUnderAdvancement()) {
            if (!didPlayerUnlockAdvancement(player)) {
                return true;
            }
        }

        return false;

    }

    public boolean didPlayerUnlockAdvancement(PlayerEntity player) {
        Identifier id = new Identifier(this.lock_under_adv);
        return AdvancementUtils.didPlayerFinish(player, id);
    }

    @Override
    public Class<Perk> getClassForSerialization() {
        return Perk.class;
    }

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.PERK;
    }

    @Override
    public String GUID() {
        return identifier;
    }
}
