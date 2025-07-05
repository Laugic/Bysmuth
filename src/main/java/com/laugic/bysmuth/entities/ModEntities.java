package com.laugic.bysmuth.entities;

import com.laugic.bysmuth.Bysmuth;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITIES, Bysmuth.MOD_ID);

    public static final RegistryObject<EntityType<PrimedNTN>> NTN =
            ENTITY_TYPES.register("ignited_ntn",
                    () -> EntityType.Builder.<PrimedNTN>of(PrimedNTN::new, MobCategory.MISC)
                            .fireImmune().sized(0.98F, 0.98F)
                            .build(new ResourceLocation(Bysmuth.MOD_ID, "ignited_ntn").toString()));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
