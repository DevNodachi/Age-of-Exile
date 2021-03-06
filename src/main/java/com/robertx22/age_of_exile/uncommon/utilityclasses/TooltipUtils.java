package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts.SocketData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.wrappers.SText;
import com.robertx22.library_of_exile.utils.CLOC;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class TooltipUtils {

    public static String CHECKMARK = Formatting.GREEN + "\u2714";
    public static String X = Formatting.RED + "\u2716";

    public static MutableText color(Formatting format, MutableText comp) {
        return new LiteralText(format + "").append(comp);
    }

    public static void addRequirements(List<Text> tip, GearItemData gear, EntityCap.UnitData data) {

        Formatting reqNumberColor = Formatting.YELLOW;

        if (gear.level > data.getLevel()) {
            reqNumberColor = Formatting.RED;
        }

        tip.add(new SText(""));
        tip.add(new LiteralText(reqNumberColor + "").append(Words.Level.locName())
            .append(": " + gear.level));
        tip.add(new SText(""));
    }

    public static void addSocketNamesLine(List<Text> tip, GearItemData gear) {
        Formatting BR = Formatting.GRAY;
        String runes = "";
        for (SocketData x : gear.sockets.sockets) {
            if (!runes.isEmpty()) {
                runes += " ";
            }

            if (x.isRune()) {
                runes += BR + "[" + Formatting.GOLD + x.rune_id.toUpperCase(Locale.ROOT) + BR + "]";

            }
            if (x.isGem()) {
                runes += BR + "[" + x.getGem()
                    .getFormat() + x.getGem()
                    .getIcon() + BR + "]";
            }

        }

        tip.add(new LiteralText(Formatting.GOLD + runes).formatted(Formatting.GOLD));
    }

    public static void addUniqueDesc(List<Text> tip, UniqueGear uniq, GearItemData gear) {
        String uniqdesc = CLOC.translate(uniq.locDesc());

        List<String> lores = TooltipUtils.cutIfTooLong(uniqdesc);
        tip.add(new LiteralText(""));

        int i = 0;
        for (String desc : lores) {

            MutableText comp = new LiteralText(gear.getRarity()
                .textFormatting() + "");

            if (i == 0) {
                comp.append("'");
            }
            comp.append(desc);

            if (i == lores.size() - 1) {
                comp.append("'");
            }
            i++;
            tip.add(comp);

        }
    }

    public static void addEmpty(List<Text> tooltip) {
        tooltip.add(CLOC.blank(""));
    }

    public static List<String> compsToStrings(List<Text> list) {
        return list.stream()
            .map(x -> x.asString())
            .collect(Collectors.toList());
    }

    public static MutableText level(int lvl) {
        return new LiteralText(Formatting.YELLOW + "").append(Words.Level.locName())
            .append((": " + lvl))
            .formatted(Formatting.YELLOW);

    }

    public static List<MutableText> cutIfTooLong(MutableText comp) {
        List<String> stringList = cutIfTooLong(CLOC.translate(comp));
        return stringList.stream()
            .map(x -> new SText(x))
            .collect(Collectors.toList());

    }

    public static List<String> cutIfTooLong(String str) {

        List<String> list = new ArrayList<>();

        int start = 0;
        int i = 0;
        for (Character c : str.toCharArray()) {

            if (i == str.length() - 1) {
                list.add(str.substring(start));
            } else if (i - start > 26 && c == ' ') {
                String cut = str.substring(start, i);
                if (start > 0) {
                    cut = cut.substring(1);
                }

                list.add(cut);

                start = i;
            }
            i++;
        }

        return list;
    }

    public static MutableText itemBrokenText(ItemStack stack, ICommonDataItem data) {

        if (data != null) {

            if (RepairUtils.isItemBroken(stack)) {
                MutableText comp = new LiteralText(X + " ").append(Words.Broken.locName());
                return comp;
            }

        }

        return null;
    }

    public static MutableText requirement(MutableText text, int stat, int req) {

        MutableText comp;

        if (stat >= req) {
            comp = new LiteralText(CHECKMARK);
        } else {
            comp = new LiteralText(X);
        }

        return comp.append(
            new LiteralText(Formatting.GRAY + " ").append(text)
                .append(": " + req));

    }

    public static List<Text> removeDoubleBlankLines(List<Text> list) {
        return removeDoubleBlankLines(list, 5000);
    }

    public static List<Text> removeDoubleBlankLines(List<Text> list, int minLinesCutAllBlanks) {

        List<Text> newt = new ArrayList();

        boolean lastIsEmpty = false;

        boolean alwaysRemoveEmpty = list.size() > minLinesCutAllBlanks;

        for (int i = 0; i < list.size(); i++) {

            if (list.get(i)
                .getString()
                .length() > 2) {
                lastIsEmpty = false;
                newt.add(list.get(i));
            } else {

                if ((lastIsEmpty || alwaysRemoveEmpty)) {

                } else {
                    newt.add(list.get(i));
                }

                lastIsEmpty = true;

            }
        }

        list.clear();

        list.addAll(newt);

        return newt;
    }

    public static MutableText rarity(Rarity rarity) {

        return (new LiteralText(rarity.textFormatting() + "")
            .append(rarity.locName()
                .append(" ")
                .append(Words.Item.locName())));
    }

    public static MutableText rarityShort(Rarity rarity) {
        return (new LiteralText(rarity.textFormatting() + "").append(rarity.locName()));
    }

    public static MutableText tier(int tier) {
        return
            Words.Tier.locName()
                .append(": " + tier);

    }

    public static void abilityLevel(List<MutableText> list, int current, int max) {
        list.add(
            new SText(Formatting.YELLOW + "").append("Ability ")
                .append(Words.Level.locName())
                .append(": " + current + "/" + max));
    }
}
