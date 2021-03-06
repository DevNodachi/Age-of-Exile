package com.robertx22.age_of_exile.gui.screens.skill_tree.pick_spell_buttons.picking;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.gui.screens.skill_tree.IMarkOnTop;
import com.robertx22.age_of_exile.gui.screens.skill_tree.IRemoveOnClickedOutside;
import com.robertx22.age_of_exile.gui.screens.skill_tree.SkillTreeScreen;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RenderUtils;
import com.robertx22.age_of_exile.vanilla_mc.packets.spells.HotbarSetupPacket;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class PickSpellForHotBarButton extends TexturedButtonWidget implements IMarkOnTop, IRemoveOnClickedOutside {

    static Identifier ID = new Identifier(Ref.MODID, "textures/gui/skill_tree/hotbar.png");

    public static int XSIZE = 16;
    public static int YSIZE = 16;
    SkillTreeScreen screen;
    Spell spell;

    public PickSpellForHotBarButton(SkillTreeScreen screen, int hotbarPosition, Spell spell, int x, int y) {
        super(x, y, XSIZE, YSIZE, 0, 0, 1, ID, (action) -> {
            Packets.sendToServer(new HotbarSetupPacket(spell, hotbarPosition));
            screen.removeRemovableButtons();

        });
        this.spell = spell;
        this.screen = screen;

    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (!spell.GUID()
            .isEmpty()) {
            MinecraftClient mc = MinecraftClient.getInstance();
            mc.getTextureManager()
                .bindTexture(this.spell.getIconLoc());
            RenderUtils.render16Icon(matrices, spell.getIconLoc(), this.x + 4, this.y + 4);
        }
    }

}
