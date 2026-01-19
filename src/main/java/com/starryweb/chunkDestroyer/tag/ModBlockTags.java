package com.starryweb.chunkDestroyer.tag;

import com.starryweb.chunkDestroyer.ChunkDestroyer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModBlockTags {

    public static final TagKey<Block> ORE_TAGS = create("ore_tags");
    public static final TagKey<Block> PICKAXE_AXE_MINEABLE = create("pickaxe_axe_mineable");

    private static TagKey<Block> create(String pName) {
        return TagKey.create(Registries.BLOCK,ResourceLocation.fromNamespaceAndPath(ChunkDestroyer.MOD_ID,pName));
    }

    //forge命名空间找，一般用于与其他mod联动
    private static TagKey<Block> createForgeTag(String pName) {
        return TagKey.create(Registries.BLOCK,ResourceLocation.fromNamespaceAndPath("forge",pName));
    }

}
