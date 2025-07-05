package com.laugic.bysmuth.recipes;

import javax.annotation.ParametersAreNonnullByDefault;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.google.gson.JsonSyntaxException;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

//From Create https://github.com/Creators-of-Create/Create
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ProcessingRecipeSerializer<T extends ProcessingRecipe<?>> extends ForgeRegistryEntry<RecipeSerializer<?>>
        implements RecipeSerializer<T> {

    private final ProcessingRecipeBuilder.ProcessingRecipeFactory<T> factory;

    public ProcessingRecipeSerializer(ProcessingRecipeBuilder.ProcessingRecipeFactory<T> factory) {
        this.factory = factory;
    }

    protected void writeToJson(JsonObject json, T recipe) {
        JsonArray jsonIngredients = new JsonArray();
        JsonArray jsonOutputs = new JsonArray();

        recipe.ingredients.forEach(i -> jsonIngredients.add(i.toJson()));

        recipe.results.forEach(o -> jsonOutputs.add(o.serialize()));

        json.add("ingredients", jsonIngredients);
        json.add("results", jsonOutputs);


        int processingDuration = recipe.getProcessingDuration();
        if (processingDuration > 0)
            json.addProperty("processingTime", processingDuration);

        recipe.writeAdditional(json);
    }

    protected T readFromJson(ResourceLocation recipeId, JsonObject json) {
        ProcessingRecipeBuilder<T> builder = new ProcessingRecipeBuilder<>(factory, recipeId);
        NonNullList<Ingredient> ingredients = NonNullList.create();
        NonNullList<ProcessingOutput> results = NonNullList.create();
        NonNullList<FluidStack> fluidResults = NonNullList.create();

        for (JsonElement je : GsonHelper.getAsJsonArray(json, "ingredients")) {
            ingredients.add(Ingredient.fromJson(je));
        }

        for (JsonElement je : GsonHelper.getAsJsonArray(json, "results")) {
            JsonObject jsonObject = je.getAsJsonObject();
            results.add(ProcessingOutput.deserialize(je));
        }

        if (json.has("enchantments")) {
            JsonArray enchantArray = json.getAsJsonArray("enchantments");
            for (JsonElement element : enchantArray) {
                JsonObject enchantObj = element.getAsJsonObject();
                String enchantId = GsonHelper.getAsString(enchantObj, "id");
                int level = GsonHelper.getAsInt(enchantObj, "level");

                ResourceLocation enchantRL = new ResourceLocation(enchantId);
                Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(enchantRL);
                if (enchantment != null) {
                    for (ProcessingOutput result: results) {
                        if(result.stack.getItem() instanceof EnchantedBookItem)
                            EnchantedBookItem.addEnchantment(result.stack, new EnchantmentInstance(enchantment, level));
                        else
                            result.stack.enchant(enchantment, level);
                    }
                } else {
                    throw new JsonSyntaxException("Unknown enchantment: " + enchantId);
                }
            }
        }

        builder.withItemIngredients(ingredients)
                .withItemOutputs(results)
                .withFluidOutputs(fluidResults);

        if (GsonHelper.isValidNode(json, "processingTime"))
            builder.duration(GsonHelper.getAsInt(json, "processingTime"));

        T recipe = builder.build();
        recipe.readAdditional(json);
        return recipe;
    }

    protected void writeToBuffer(FriendlyByteBuf buffer, T recipe) {
        NonNullList<Ingredient> ingredients = recipe.ingredients;
        NonNullList<ProcessingOutput> outputs = recipe.results;
        NonNullList<FluidStack> fluidOutputs = recipe.fluidResults;

        buffer.writeVarInt(ingredients.size());
        ingredients.forEach(i -> i.toNetwork(buffer));

        buffer.writeVarInt(outputs.size());
        outputs.forEach(o -> o.write(buffer));
        buffer.writeVarInt(fluidOutputs.size());
        fluidOutputs.forEach(o -> o.writeToPacket(buffer));

        buffer.writeVarInt(recipe.getProcessingDuration());

        recipe.writeAdditional(buffer);
    }

    protected T readFromBuffer(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        NonNullList<ProcessingOutput> results = NonNullList.create();
        NonNullList<FluidStack> fluidResults = NonNullList.create();

        int size = buffer.readVarInt();
        for (int i = 0; i < size; i++)
            ingredients.add(Ingredient.fromNetwork(buffer));

        size = buffer.readVarInt();
        for (int i = 0; i < size; i++)
            results.add(ProcessingOutput.read(buffer));

        size = buffer.readVarInt();
        for (int i = 0; i < size; i++)
            fluidResults.add(FluidStack.readFromPacket(buffer));

        T recipe = new ProcessingRecipeBuilder<>(factory, recipeId).withItemIngredients(ingredients)
                .withItemOutputs(results)
                .withFluidOutputs(fluidResults)
                .duration(buffer.readVarInt())
                .build();
        recipe.readAdditional(buffer);
        return recipe;
    }

    public final void write(JsonObject json, T recipe) {
        writeToJson(json, recipe);
    }

    @Override
    public final T fromJson(ResourceLocation id, JsonObject json) {
        return readFromJson(id, json);
    }

    @Override
    public final void toNetwork(FriendlyByteBuf buffer, T recipe) {
        writeToBuffer(buffer, recipe);
    }

    @Override
    public final T fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
        return readFromBuffer(id, buffer);
    }

    public ProcessingRecipeBuilder.ProcessingRecipeFactory<T> getFactory() {
        return factory;
    }

}
