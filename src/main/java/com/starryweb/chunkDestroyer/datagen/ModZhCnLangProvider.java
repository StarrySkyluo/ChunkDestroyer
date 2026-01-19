package com.starryweb.chunkDestroyer.datagen;

import com.starryweb.chunkDestroyer.ChunkDestroyer;
import com.starryweb.chunkDestroyer.block.ModBlocks;
import com.starryweb.chunkDestroyer.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ModZhCnLangProvider extends LanguageProvider {


    public ModZhCnLangProvider(PackOutput output) {
        super(output, ChunkDestroyer.MOD_ID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        add(ModItems.CHUNK_DESTROYER.get(),"区块破坏手机");
        add(ModBlocks.CHUNK_DESTROYER_BLOCK.get(),"区块破坏器");



        add("itemGroup.chunk_destroyer_tab", "区块破坏器");
    }
}
