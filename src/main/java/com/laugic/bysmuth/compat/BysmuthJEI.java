package com.laugic.bysmuth.compat;

import com.laugic.bysmuth.Bysmuth;
import com.laugic.bysmuth.items.ModItems;
import com.laugic.bysmuth.recipes.AdvancedManualApplicationRecipe;
import com.laugic.bysmuth.recipes.BlockRecipe;
import com.laugic.bysmuth.recipes.ManualApplicationRecipe;
import com.laugic.bysmuth.recipes.ModRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.Objects;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

@JeiPlugin
public class BysmuthJEI implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Bysmuth.ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new
                ManualItemCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new
                AdvancedManualItemCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new
                BlockRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        List<ManualApplicationRecipe> recipes = rm.getAllRecipesFor(ModRecipes.ITEM_APPLICATION.getType());
        registration.addRecipes(new RecipeType<>(ManualItemCategory.UID, ManualApplicationRecipe.class), recipes);
        List<AdvancedManualApplicationRecipe> advRecipes = rm.getAllRecipesFor(ModRecipes.ADVANCED_ITEM_APPLICATION.getType());
        registration.addRecipes(new RecipeType<>(AdvancedManualItemCategory.UID, AdvancedManualApplicationRecipe.class), advRecipes);
        List<BlockRecipe> blockRecipes = rm.getAllRecipesFor(ModRecipes.BLOCK_RECIPE.getType());
        registration.addRecipes(new RecipeType<>(BlockRecipeCategory.UID, BlockRecipe.class), blockRecipes);

        ItemStack item = new ItemStack(ModItems.BysmuthChunk.get());
        registration.addIngredientInfo(item, VanillaTypes.ITEM_STACK, new TranslatableComponent("jei.bysmuth.bysmuth_chunk.info"));
    }
}