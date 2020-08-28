package com.robertx22.age_of_exile.vanilla_mc.blocks.slots;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

import java.util.HashMap;

public class EssentiaFuelSlot extends Slot {
    public EssentiaFuelSlot(Inventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return FUEL_VALUES.get(stack.getItem()) != null;
    }

    public static HashMap<Item, Integer> FUEL_VALUES = new HashMap<Item, Integer>() {
        {
            {
                put(ModRegistry.MISC_ITEMS.MAGIC_ESSENCE, 200);
                put(ModRegistry.MISC_ITEMS.RARE_MAGIC_ESSENCE, 1000);
            }
        }
    };

}