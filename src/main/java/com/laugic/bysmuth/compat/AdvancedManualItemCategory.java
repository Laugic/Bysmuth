package com.laugic.bysmuth.compat;

import com.laugic.bysmuth.Bysmuth;
import com.laugic.bysmuth.items.ModItems;
import com.laugic.bysmuth.recipes.AdvancedManualApplicationRecipe;
import com.laugic.bysmuth.recipes.ManualApplicationRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class AdvancedManualItemCategory implements IRecipeCategory<AdvancedManualApplicationRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(Bysmuth.ID, "advanced_item_application");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(Bysmuth.ID, "textures/gui/advanced_item_application.png");

    private final IDrawable background;
    private final IDrawable icon;

    public AdvancedManualItemCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 180, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(ModItems.FallenStar.get()));
    }

    @Override
    @Deprecated
    public Class<? extends AdvancedManualApplicationRecipe> getRecipeClass() {
        return AdvancedManualApplicationRecipe.class;
    }

    @Override
    public Component getTitle() {
        return new TextComponent("Advanced Item Application");
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
    public void setRecipe(IRecipeLayoutBuilder builder, AdvancedManualApplicationRecipe recipe, IFocusGroup focusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT,  11, 32).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT,  78, 32).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.INPUT,  78, 14).addIngredients(recipe.getIngredients().get(2));
        builder.addSlot(RecipeIngredientRole.INPUT,  78, 50).addIngredients(recipe.getIngredients().get(3));
        if(recipe.getIngredients().size() > 4)
        {
            builder.addSlot(RecipeIngredientRole.INPUT,  60, 32).addIngredients(recipe.getIngredients().get(4));
            builder.addSlot(RecipeIngredientRole.INPUT,  96, 32).addIngredients(recipe.getIngredients().get(4));
        }
        builder.addSlot(RecipeIngredientRole.OUTPUT, 154, 32).addItemStack(recipe.getResultItem());
    }
}