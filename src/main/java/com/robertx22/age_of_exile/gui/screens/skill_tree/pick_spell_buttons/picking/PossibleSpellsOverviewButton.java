package com.robertx22.age_of_exile.gui.screens.skill_tree.pick_spell_buttons.picking;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.gui.screens.skill_tree.IMarkOnTop;
import com.robertx22.age_of_exile.gui.screens.skill_tree.IRemoveOnClickedOutside;
import com.robertx22.age_of_exile.gui.screens.skill_tree.SkillTreeScreen;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.stream.Collectors;

public class PossibleSpellsOverviewButton extends TexturedButtonWidget implements IMarkOnTop, IRemoveOnClickedOutside {

    static Identifier ID = new Identifier(Ref.MODID, "textures/gui/skill_tree/possible_spells.png");

    public static int XSIZE = 193;
    public static int YSIZE = 123;

    SkillTreeScreen screen;

    public PossibleSpellsOverviewButton(SkillTreeScreen screen, int pickSpellForHotbar, int x, int y) {
        super(x, y, XSIZE, YSIZE, 0, 0, 1, ID, (action) -> {
        });
        this.screen = screen;

        screen.removePerkButtons();

        List<Spell> spells = Load.spells(screen.mc.player)
            .getLearnedSpells(screen.mc.player)
            .stream()
            .filter(s -> !s.isPassive())
            .collect(Collectors.toList());

        int placeX = 0;
        int placeY = 0;

        for (Spell spell : spells) {

            int xp = x + 3 + (placeX * 19);
            int yp = y + 3 + (placeY * 19);

            screen.addButtonPublic(new PickSpellForHotBarButton(screen, pickSpellForHotbar, spell, xp, yp));

            placeX++;
            if (placeX == 9) {
                placeX = 0;
                placeY++;
            }
        }

    }

}
