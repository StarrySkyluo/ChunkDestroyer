package com.starryweb.chunkDestroyer.block;

import com.starryweb.chunkDestroyer.ChunkDestroyer;
import com.starryweb.chunkDestroyer.item.ModItems;
import com.starryweb.chunkDestroyer.item.custom.ChunkDestroyerBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ChunkDestroyer.MOD_ID);

    public static final RegistryObject<Block> CHUNK_DESTROYER_BLOCK =
            registerBlock("chunk_destroyer_block",
                    ()-> new ChunkDestroyerBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));


    //注册方块物品
    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name,() -> new BlockItem(block.get(),new Item.Properties()));
    }

    //注册方块
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> blocks = BLOCKS.register(name, block);
        registerBlockItem(name, blocks);
        return blocks;
    }

    public static void register(IEventBus eventbus){
        BLOCKS.register(eventbus);
    }

}
