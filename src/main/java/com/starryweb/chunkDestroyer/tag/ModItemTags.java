package com.starryweb.chunkDestroyer.tag;

import com.starryweb.chunkDestroyer.ChunkDestroyer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {

    public static final TagKey<Item> SUGAR_TAG = bind("sugar_tag");

    private static TagKey<Item> bind(String pName) {
        return TagKey.create(Registries.ITEM,ResourceLocation.fromNamespaceAndPath(ChunkDestroyer.MOD_ID,pName));
    }

}
