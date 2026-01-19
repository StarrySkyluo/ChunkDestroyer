package com.starryweb.chunkDestroyer.block.entity;

import com.starryweb.chunkDestroyer.ChunkDestroyer;
import com.starryweb.chunkDestroyer.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ChunkDestroyer.MOD_ID);

    public static final RegistryObject<BlockEntityType<ChunkDestroyerBlockEntity>> CHUNK_DESTROYER_BLOCK_BE =
            BLOCK_ENTITIES.register("chunk_destroyer_block_be",()->
                    BlockEntityType.Builder.of(ChunkDestroyerBlockEntity::new,
                            ModBlocks.CHUNK_DESTROYER_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }

}
