package com.robertx22.age_of_exile.loot.blueprints;

import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.RarityRegistryContainer;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.bases.*;
import com.robertx22.age_of_exile.loot.generators.stack_changers.DamagedGear;
import com.robertx22.age_of_exile.loot.generators.util.GearCreationUtils;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import net.minecraft.item.ItemStack;

public class GearBlueprint extends ItemBlueprint {

    public GearBlueprint(int lvl) {
        super(lvl);
        actionsAfterGeneration.add(DamagedGear.INSTANCE);
    }

    public GearBlueprint(LootInfo info) {
        super(info);
        actionsAfterGeneration.add(DamagedGear.INSTANCE);

        this.rarity.setupChances(info);
        this.isUniquePart.setupChances(info);
    }

    public GearBlueprint(int lvl, int tier) {
        super(lvl, tier);
        actionsAfterGeneration.add(DamagedGear.INSTANCE);
    }

    public GearRarityPart rarity = new GearRarityPart(this);

    public GearItemSlotPart gearItemSlot = new GearItemSlotPart(this);
    public UnidentifiedPart unidentifiedPart = new UnidentifiedPart(this);
    public UniqueGearPart uniquePart = new UniqueGearPart(this);
    public IsUniquePart isUniquePart = new IsUniquePart(this);

    @Override
    public RarityRegistryContainer<GearRarity> getRarityContainer() {
        return SlashRegistry.GearRarities();
    }

    public GearItemData createData() {
        return GearCreationUtils.CreateData(this);
    }

    @Override
    ItemStack generate() {
        return GearCreationUtils.CreateStack(createData());
    }

}
