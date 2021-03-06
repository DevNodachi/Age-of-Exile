package com.robertx22.age_of_exile.vanilla_mc.items.misc;

import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.currency.base.IShapedRecipe;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class AddResetPerkPointsItem extends AutoItem implements IShapedRecipe {

    public AddResetPerkPointsItem() {
        super(new Settings().group(CreativeTabs.MyModTab)
            .maxCount(64));
    }

    @Override
    public String GUID() {
        return "potions/add_reset_perk_points";
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity player) {

        stack.decrement(1);

        if (player instanceof PlayerEntity) {
            PlayerEntity p = (PlayerEntity) player;
            Load.perks(p).data.reset_points += 5;
            p.giveItemStack(new ItemStack(Items.GLASS_BOTTLE));
        }

        return stack;
    }

    @Override
    public TypedActionResult<ItemStack> use(World worldIn, PlayerEntity player, Hand handIn) {
        ItemStack itemStack = player.getStackInHand(handIn);
        player.setCurrentHand(handIn);
        return TypedActionResult.success(itemStack);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 30;
    }

    @Override
    public ShapedRecipeJsonFactory getRecipe() {
        return shaped(ModRegistry.MISC_ITEMS.ADD_RESET_PERK_POINTS)
            .input('t', ModRegistry.MISC_ITEMS.T0_DUST)
            .input('v', Items.IRON_INGOT)
            .input('b', Items.GLASS_BOTTLE)
            .pattern(" v ")
            .pattern("vtv")
            .pattern(" b ")
            .criterion("player_level", trigger());
    }

    @Override
    public String locNameForLangFile() {
        return "Perk Reset Points Potion";
    }

}
