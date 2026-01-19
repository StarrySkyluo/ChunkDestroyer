package com.starryweb.chunkDestroyer.datagen;

import com.starryweb.chunkDestroyer.ChunkDestroyer;
import com.starryweb.chunkDestroyer.block.ModBlocks;
import com.starryweb.chunkDestroyer.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelsProvider extends ItemModelProvider {
    public ModItemModelsProvider(PackOutput output,ExistingFileHelper existingFileHelper) {
        super(output, ChunkDestroyer.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        //区块破坏器（物品）
        basicItem(ModItems.CHUNK_DESTROYER.get());

        //区块破坏器（方块）
        withExistingParent(ModBlocks.CHUNK_DESTROYER_BLOCK.getId().getPath(),
                modLoc("block/chunk_destroyer_block"));

    }


    //工具的方法
    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.withDefaultNamespace("item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(ChunkDestroyer.MOD_ID, "item/"+item.getId().getPath()));
    }

}
