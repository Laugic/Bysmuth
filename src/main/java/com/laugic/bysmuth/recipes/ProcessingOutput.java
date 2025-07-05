package com.laugic.bysmuth.recipes;


import java.util.Random;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.laugic.bysmuth.Bysmuth;
import com.laugic.bysmuth.util.Pair;
import com.laugic.bysmuth.util.RegisteredObjects;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.nbt.TagParser;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.ForgeRegistries;

//From Create https://github.com/Creators-of-Create/Create
public class ProcessingOutput {

    public static final ProcessingOutput EMPTY = new ProcessingOutput(ItemStack.EMPTY, 1);

    private static final Random r = new Random();
    public ItemStack stack = null;
    private Enchantment enchantment;
    private float chance = 0;

    private Pair<ResourceLocation, Integer> compatDatagenOutput;

    public ProcessingOutput(ItemStack stack, float chance) {
        this.stack = stack;
        this.chance = chance;
    }

    public ProcessingOutput(Pair<ResourceLocation, Integer> item, float chance) {
        this.stack = ItemStack.EMPTY;
        this.compatDatagenOutput = item;
        this.chance = chance;
    }

    public ProcessingOutput(Enchantment enchantment) {
        this.enchantment = enchantment;
    }

    public ItemStack getStack() {
        return stack;
    }

    public Enchantment getEnchantment() {
        return enchantment;
    }

    public float getChance() {
        return chance;
    }

    public ItemStack rollOutput() {
        int outputAmount = stack.getCount();
        for (int roll = 0; roll < stack.getCount(); roll++)
            if (r.nextFloat() > chance)
                outputAmount--;
        if (outputAmount == 0)
            return ItemStack.EMPTY;
        ItemStack out = stack.copy();
        out.setCount(outputAmount);
        return out;
    }

    public JsonElement serialize() {
        JsonObject json = new JsonObject();
        ResourceLocation resourceLocation = compatDatagenOutput == null ? RegisteredObjects.getKeyOrThrow(stack
                .getItem()) : compatDatagenOutput.getFirst();
        json.addProperty("item", resourceLocation.toString());
        int count = compatDatagenOutput == null ? stack.getCount() : compatDatagenOutput.getSecond();
        if (count != 1)
            json.addProperty("count", count);
        if (stack.hasTag())
            json.add("nbt", JsonParser.parseString(stack.getTag()
                    .toString()));
        if (chance != 1)
            json.addProperty("chance", chance);
        return json;
    }

    public static ProcessingOutput deserialize(JsonElement je) {
        if (!je.isJsonObject())
            throw new JsonSyntaxException("ProcessingOutput must be a json object");

        JsonObject json = je.getAsJsonObject();
        String itemId = GsonHelper.getAsString(json, "item");
        int count = GsonHelper.getAsInt(json, "count", 1);
        float chance = GsonHelper.isValidNode(json, "chance") ? GsonHelper.getAsFloat(json, "chance") : 1;
        ItemStack itemstack = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemId)), count);

        if (GsonHelper.isValidNode(json, "nbt")) {
            try {
                JsonElement element = json.get("nbt");
                itemstack.setTag(TagParser.parseTag(
                        element.isJsonObject() ? Bysmuth.GSON.toJson(element) : GsonHelper.convertToString(element, "nbt")));
            } catch (CommandSyntaxException e) {
                e.printStackTrace();
            }
        }

        return new ProcessingOutput(itemstack, chance);
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeItem(getStack());
        buf.writeFloat(getChance());
    }

    public static ProcessingOutput read(FriendlyByteBuf buf) {
        return new ProcessingOutput(buf.readItem(), buf.readFloat());
    }

}
