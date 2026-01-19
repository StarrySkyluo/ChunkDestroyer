package com.starryweb.chunkDestroyer.item;

import com.starryweb.chunkDestroyer.ChunkDestroyer;
import com.starryweb.chunkDestroyer.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ChunkDestroyer.MOD_ID);

    public static final RegistryObject<CreativeModeTab> Chunk_Destroyer_Tab =
            CREATIVE_MODE_TABS.register("chunk_destroyer_tab",() -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.CHUNK_DESTROYER.get()))
                    .title(Component.translatable("itemGroup.chunk_destroyer_tab"))
                    .displayItems((pParameters,pOutput) ->{

                        pOutput.accept(ModItems.CHUNK_DESTROYER.get());

                        pOutput.accept(ModBlocks.CHUNK_DESTROYER_BLOCK.get());

                    }).build());


    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }

}
