package com.laugic.bysmuth.compat;

import com.laugic.bysmuth.Bysmuth;
import com.laugic.bysmuth.items.ModItems;
import com.laugic.bysmuth.recipes.BlockRecipe;
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
import net.minecraft.world.level.block.Blocks;

public class BlockRecipeCategory implements IRecipeCategory<BlockRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(Bysmuth.ID, "block_recipe");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(Bysmuth.ID, "textures/gui/block_recipe.png");

    private final IDrawable background;
    private final IDrawable icon;

    public BlockRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 110, 68);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(Blocks.REPEATER));
    }

    @Override
    @Deprecated
    public Class<? extends BlockRecipe> getRecipeClass() {
        return BlockRecipe.class;
    }

    @Override
    public Component getTitle() {
        return new TextComponent("Block Recipe");
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
    public void setRecipe(IRecipeLayoutBuilder builder, BlockRecipe recipe, IFocusGroup focusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT,  19, 19).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT,  19, 37).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 78, 27).addItemStack(recipe.getResultItem());
    }
}