package com.laugic.bysmuth.recipes;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

//From Create https://github.com/Creators-of-Create/Create
public interface IRecipeTypeInfo {

    ResourceLocation getId();

    <T extends RecipeSerializer<?>> T getSerializer();

    <T extends RecipeType<?>> T getType();

}