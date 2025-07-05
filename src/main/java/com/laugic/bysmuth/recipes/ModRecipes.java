package com.laugic.bysmuth.recipes;


import com.laugic.bysmuth.Bysmuth;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.codec.language.bm.Lang;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.Optional;
import java.util.function.Supplier;

//From Create https://github.com/Creators-of-Create/Create
public enum ModRecipes implements IRecipeTypeInfo {
    ITEM_APPLICATION(ManualApplicationRecipe::new),
    ADVANCED_ITEM_APPLICATION(AdvancedManualApplicationRecipe::new),
    BLOCK_RECIPE(BlockRecipe::new),
    ;
    private final ResourceLocation id;
    private final RegistryObject<RecipeSerializer<?>> serializerObject;
    @Nullable
    private final RegistryObject<RecipeType<?>> typeObject;
    private final Supplier<RecipeType<?>> type;

//    CONVERSION(ConversionRecipe::new),
//    CRUSHING(CrushingRecipe::new),
//    CUTTING(CuttingRecipe::new),
//    MILLING(MillingRecipe::new),
//    BASIN(BasinRecipe::new),
//    MIXING(MixingRecipe::new),
//    COMPACTING(CompactingRecipe::new),
//    PRESSING(PressingRecipe::new),
//    SANDPAPER_POLISHING(SandPaperPolishingRecipe::new),
//    SPLASHING(SplashingRecipe::new),
//    HAUNTING(HauntingRecipe::new),
//    DEPLOYING(DeployerApplicationRecipe::new),
//    FILLING(FillingRecipe::new),
//    EMPTYING(EmptyingRecipe::new),
//
//    MECHANICAL_CRAFTING(MechanicalCraftingRecipe.Serializer::new),
//    SEQUENCED_ASSEMBLY(SequencedAssemblyRecipeSerializer::new),

    ModRecipes(Supplier<RecipeSerializer<?>> serializerSupplier, Supplier<RecipeType<?>> typeSupplier, boolean registerType) {
        String name = name().toLowerCase(Locale.ROOT);
        id = Bysmuth.asResource(name);
        serializerObject = Registers.SERIALIZER_REGISTER.register(name, serializerSupplier);
        if (registerType) {
            typeObject = Registers.TYPE_REGISTER.register(name, typeSupplier);
            type = typeObject;
        } else {
            typeObject = null;
            type = typeSupplier;
        }
    }

    ModRecipes(Supplier<RecipeSerializer<?>> serializerSupplier) {
        String name = name().toLowerCase(Locale.ROOT);
        id = Bysmuth.asResource(name);
        serializerObject = Registers.SERIALIZER_REGISTER.register(name, serializerSupplier);
        typeObject = Registers.TYPE_REGISTER.register(name, () -> simpleType(id));
        type = typeObject;
    }

    ModRecipes(ProcessingRecipeBuilder.ProcessingRecipeFactory<?> processingFactory) {
        this(() -> new ProcessingRecipeSerializer<>(processingFactory));
    }

    public static <T extends Recipe<?>> RecipeType<T> simpleType(ResourceLocation id) {
        String stringId = id.toString();
        return new RecipeType<T>() {
            @Override
            public String toString() {
                return stringId;
            }
        };
    }

    public static void register(IEventBus modEventBus) {
        ShapedRecipe.setCraftingSize(9, 9);
        Registers.SERIALIZER_REGISTER.register(modEventBus);
        Registers.TYPE_REGISTER.register(modEventBus);
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends RecipeSerializer<?>> T getSerializer() {
        return (T) serializerObject.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends RecipeType<?>> T getType() {
        return (T) type.get();
    }

    public <C extends Container, T extends Recipe<C>> Optional<T> find(C inv, Level world) {
        return world.getRecipeManager()
                .getRecipeFor(getType(), inv, world);
    }
/*
    public static boolean shouldIgnoreInAutomation(Recipe<?> recipe) {
        RecipeSerializer<?> serializer = recipe.getSerializer();
        if (serializer != null && AllTags.AllRecipeSerializerTags.AUTOMATION_IGNORE.matches(serializer))
            return true;
        return recipe.getId()
                .getPath()
                .endsWith("_manual_only");
    }*/

    private static class Registers {
        private static final DeferredRegister<RecipeSerializer<?>> SERIALIZER_REGISTER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Bysmuth.ID);
        private static final DeferredRegister<RecipeType<?>> TYPE_REGISTER = DeferredRegister.create(Registry.RECIPE_TYPE_REGISTRY, Bysmuth.ID);
    }

}