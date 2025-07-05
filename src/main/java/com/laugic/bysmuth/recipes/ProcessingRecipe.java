package com.laugic.bysmuth.recipes;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.annotation.ParametersAreNonnullByDefault;

import com.laugic.bysmuth.Bysmuth;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import org.slf4j.Logger;

import com.google.gson.JsonObject;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fluids.FluidStack;

//From Create https://github.com/Creators-of-Create/Create
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public abstract class ProcessingRecipe<T extends Container> implements Recipe<T> {

    protected ResourceLocation id;
    public NonNullList<Ingredient> ingredients;
    protected NonNullList<ProcessingOutput> results;
    protected NonNullList<FluidStack> fluidResults;
    protected int processingDuration;

    private RecipeType<?> type;
    private RecipeSerializer<?> serializer;
    private IRecipeTypeInfo typeInfo;
    private Supplier<ItemStack> forcedResult;

    public ProcessingRecipe(IRecipeTypeInfo typeInfo, ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        this.forcedResult = null;
        this.typeInfo = typeInfo;
        this.processingDuration = params.processingDuration;
        this.fluidResults = params.fluidResults;
        this.serializer = typeInfo.getSerializer();
        this.ingredients = params.ingredients;
        this.type = typeInfo.getType();
        this.results = params.results;
        this.id = params.id;

        validate(typeInfo.getId());
    }

    // Recipe type options:

    protected abstract int getMaxInputCount();

    protected abstract int getMaxOutputCount();

    protected boolean canRequireHeat() {
        return false;
    }

    protected boolean canSpecifyDuration() {
        return false;
    }

    protected int getMaxFluidInputCount() {
        return 0;
    }

    protected int getMaxFluidOutputCount() {
        return 0;
    }

    //

    private void validate(ResourceLocation recipeTypeId) {
        String messageHeader = "Your custom " + recipeTypeId + " recipe (" + id.toString() + ")";
        Logger logger = Bysmuth.LOGGER;
        int ingredientCount = ingredients.size();
        int outputCount = results.size();

        if (ingredientCount > getMaxInputCount())
            logger.warn(messageHeader + " has more item inputs (" + ingredientCount + ") than supported ("
                    + getMaxInputCount() + ").");

        if (outputCount > getMaxOutputCount())
            logger.warn(messageHeader + " has more item outputs (" + outputCount + ") than supported ("
                    + getMaxOutputCount() + ").");

        if (processingDuration > 0 && !canSpecifyDuration())
            logger.warn(messageHeader + " specified a duration. Durations have no impact on this type of recipe.");
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<ProcessingOutput> getRollableResults() {
        return results;
    }

    public NonNullList<FluidStack> getFluidResults() {
        return fluidResults;
    }

    public List<ItemStack> getRollableResultsAsItemStacks() {
        return getRollableResults().stream()
                .map(ProcessingOutput::getStack)
                .collect(Collectors.toList());
    }

    public void enforceNextResult(Supplier<ItemStack> stack) {
        forcedResult = stack;
    }

    public List<ItemStack> rollResults() {
        return rollResults(this.getRollableResults());
    }

    public List<ItemStack> rollResults(List<ProcessingOutput> rollableResults) {
        List<ItemStack> results = new ArrayList<>();
        for (int i = 0; i < rollableResults.size(); i++) {
            ProcessingOutput output = rollableResults.get(i);
            ItemStack stack = i == 0 && forcedResult != null ? forcedResult.get() : output.rollOutput();
            if (!stack.isEmpty())
                results.add(stack);
        }
        return results;
    }

    public int getProcessingDuration() {
        return processingDuration;
    }

    // IRecipe<> paperwork

    @Override
    public ItemStack assemble(T inv) {
        return getResultItem();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return getRollableResults().isEmpty() ? ItemStack.EMPTY
                : getRollableResults().get(0)
                .getStack();
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    // Processing recipes do not show up in the recipe book
    @Override
    public String getGroup() {
        return "processing";
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return serializer;
    }

    @Override
    public RecipeType<?> getType() {
        return type;
    }

    public IRecipeTypeInfo getTypeInfo() {
        return typeInfo;
    }

    // Additional Data added by subtypes

    public void readAdditional(JsonObject json) {}

    public void readAdditional(FriendlyByteBuf buffer) {}

    public void writeAdditional(JsonObject json) {}

    public void writeAdditional(FriendlyByteBuf buffer) {}

}
