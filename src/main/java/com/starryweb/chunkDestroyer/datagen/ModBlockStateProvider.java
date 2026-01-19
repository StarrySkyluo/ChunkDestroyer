package com.starryweb.chunkDestroyer.datagen;


import com.starryweb.chunkDestroyer.ChunkDestroyer;
import com.starryweb.chunkDestroyer.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;


public class ModBlockStateProvider extends BlockStateProvider {


    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ChunkDestroyer.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        simpleBlock(ModBlocks.CHUNK_DESTROYER_BLOCK.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/chunk_destroyer_block")));

    }

    private <T extends Block> void blockItem(RegistryObject<T> block) {
        simpleBlockItem(block.get(),
                new ModelFile.UncheckedModelFile(ChunkDestroyer.MOD_ID + ":block/" + block.getId().getPath()));
    }

    private <T extends Block> void blockItem(RegistryObject<T> block,String append) {
        simpleBlockItem(block.get(),
                new ModelFile.UncheckedModelFile(ChunkDestroyer.MOD_ID + ":block/" + block.getId().getPath()+append));
    }


}
