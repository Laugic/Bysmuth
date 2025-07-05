package com.laugic.bysmuth.compat;

import com.laugic.bysmuth.Bysmuth;
import com.laugic.bysmuth.items.ModItems;
import com.laugic.bysmuth.recipes.ItemApplicationRecipe;
import com.laugic.bysmuth.recipes.ManualApplicationRecipe;
import com.laugic.bysmuth.recipes.ModRecipes;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nonnull;

public class ManualItemCategory implements IRecipeCategory<ManualApplicationRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(Bysmuth.ID, "item_application");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(Bysmuth.ID, "textures/gui/item_application.png");

    private final IDrawable background;
    private final IDrawable icon;

    public ManualItemCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(ModItems.ChaosPowder.get()));
    }

    @Override
    @Deprecated
    public Class<? extends ManualApplicationRecipe> getRecipeClass() {
        return ManualApplicationRecipe.class;
    }

    @Override
    public Component getTitle() {
        return new TextComponent("Item Application");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    @Deprecated
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ManualApplicationRecipe recipe, IFocusGroup focusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT,  27, 32).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT,  76, 32).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 134, 32).addItemStack(recipe.getResultItem());
    }
}