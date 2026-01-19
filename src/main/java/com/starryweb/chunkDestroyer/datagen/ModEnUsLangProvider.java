package com.starryweb.chunkDestroyer.datagen;

import com.starryweb.chunkDestroyer.ChunkDestroyer;
import com.starryweb.chunkDestroyer.block.ModBlocks;
import com.starryweb.chunkDestroyer.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraftforge.common.data.LanguageProvider;

public class ModEnUsLangProvider extends LanguageProvider {


    public ModEnUsLangProvider(PackOutput output) {
        super(output, ChunkDestroyer.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {

        add(ModItems.CHUNK_DESTROYER.get(),"Chunk Destroyer");
        add(ModBlocks.CHUNK_DESTROYER_BLOCK.get(),"Chunk Destroyer Block");



        add("itemGroup.chunk_destroyer_tab", "chunk destroyer tab");
    }
}
