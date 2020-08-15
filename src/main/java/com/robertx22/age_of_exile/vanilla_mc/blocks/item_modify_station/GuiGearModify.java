package com.robertx22.age_of_exile.vanilla_mc.blocks.item_modify_station;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.BaseLocRequirement;
import com.robertx22.age_of_exile.database.data.currency.loc_reqs.LocReqContext;
import com.robertx22.age_of_exile.gui.buttons.HelpButton;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.localization.CLOC;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.vanilla_mc.blocks.bases.TileGui;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class GuiGearModify extends TileGui<ContainerGearModify, TileGearModify> {

    // This is the resource location for the background image
    private static final Identifier texture = new Identifier(Ref.MODID, "textures/gui/modify_station.png");

    public GuiGearModify(ContainerGearModify cont, PlayerInventory invPlayer, MutableText comp) {
        super(cont, invPlayer, new LiteralText(""), TileGearModify.class);

        backgroundWidth = 256;
        backgroundHeight = 207;

    }

    // some [x,y] coordinates of graphical elements
    final int COOK_BAR_XPOS = 49;
    final int COOK_BAR_YPOS = 60;
    final int COOK_BAR_ICON_U = 0; // texture position of white arrow icon
    final int COOK_BAR_ICON_V = 207;
    final int COOK_BAR_WIDTH = 80;
    final int COOK_BAR_HEIGHT = 17;

    @Override
    protected void init() {
        super.init();

        List<Text> list = new ArrayList<>();

        list.add(new LiteralText("Here you can modify items, socket jewels etc."));
        list.add(new LiteralText(""));
        list.add(new LiteralText("Put the gear on one side, and the currency-"));
        list.add(new LiteralText("or jewel at the other."));

        this.addButton(new HelpButton(list, this.x + this.backgroundWidth, this.y));

    }

    @Override
    protected void drawBackground(MatrixStack matrix, float partialTicks, int x, int y) {

        // Bind the image texture
        MinecraftClient.getInstance()
            .getTextureManager()
            .bindTexture(texture);
        // Draw the image
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        drawTexture(matrix, this.x, this.y, 0, 0, backgroundWidth, backgroundHeight);

        // draw the cook progress bar
        drawTexture(matrix,
            this.x + 85, this.y + COOK_BAR_YPOS, COOK_BAR_ICON_U, COOK_BAR_ICON_V,
            (int) (this.tile.fractionOfCookTimeComplete() * COOK_BAR_WIDTH), COOK_BAR_HEIGHT
        );

        this.buttons.forEach(b -> b.renderToolTip(matrix, x, y));

    }

    @Override
    protected void drawForeground(MatrixStack matrix, int mouseX, int mouseY) {
        super.drawForeground(matrix, mouseX, mouseY);

        LocReqContext context = tile.getLocReqContext();
        if (context.effect != null && context.hasStack()) {
            int y = 80;

            boolean add = true;

            for (BaseLocRequirement req : context.effect.requirements()) {
                if (req.isNotAllowed(context)) {
                    String txt = CLOC.translate(req.getText());

                    if (add) {
                        String reqtext = CLOC.translate(Words.RequirementsNotMet.locName()) + ": ";

                        font.draw(matrix,
                            reqtext, this.backgroundWidth / 2 - font.getWidth(reqtext) / 2, y, Color.red.getRGB());
                        y += font.fontHeight + 1;
                        add = false;
                    }

                    font.draw(matrix, txt, this.backgroundWidth / 2 - font.getWidth(txt) / 2, y, Color.red.getRGB());
                    y += font.fontHeight;
                }
            }

        }

        final int LABEL_XPOS = 5;
        final int LABEL_YPOS = 5;
        font.draw(matrix, CLOC.translate(tile.getDisplayName()), LABEL_XPOS, LABEL_YPOS, Color.darkGray.getRGB());

    }

}