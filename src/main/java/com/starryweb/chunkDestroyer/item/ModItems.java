package com.starryweb.chunkDestroyer.item;

import com.starryweb.chunkDestroyer.item.custom.ChunkDestroyer;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(
            ForgeRegistries.ITEMS, com.starryweb.chunkDestroyer.ChunkDestroyer.MOD_ID
    );


    //durability设置耐久，从0到127，故为128耐久

    public static final RegistryObject<Item> CHUNK_DESTROYER = ITEMS.register("chunk_destroyer",
            ()->new ChunkDestroyer(new Item.Properties().durability(127)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }


}
