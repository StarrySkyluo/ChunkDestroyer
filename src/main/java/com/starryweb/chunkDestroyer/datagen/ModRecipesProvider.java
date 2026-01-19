package com.starryweb.chunkDestroyer.datagen;

import com.starryweb.chunkDestroyer.ChunkDestroyer;
import com.starryweb.chunkDestroyer.block.ModBlocks;
import com.starryweb.chunkDestroyer.item.ModItems;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;

import net.minecraftforge.common.crafting.conditions.IConditionBuilder;


import java.util.function.Consumer;

public class ModRecipesProvider extends RecipeProvider implements IConditionBuilder {


    public ModRecipesProvider(PackOutput pOutput) {
        super(pOutput);
    }


    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {


        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHUNK_DESTROYER_BLOCK.get())
                .pattern("###")
                .pattern("#A#")
                .pattern("###")
                .define('#', Items.DIAMOND)
                .define('A', Items.DIAMOND_PICKAXE)
                .unlockedBy(getHasName(Items.DIAMOND), has(Items.DIAMOND))
                .unlockedBy(getHasName(Items.DIAMOND_PICKAXE), has(Items.DIAMOND_PICKAXE))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.CHUNK_DESTROYER.get())
                .pattern("###")
                .pattern("#A#")
                .pattern("###")
                .define('#', Items.DIAMOND)
                .define('A', Items.IRON_PICKAXE)
                .unlockedBy(getHasName(Items.DIAMOND), has(Items.DIAMOND))
                .unlockedBy(getHasName(Items.IRON_PICKAXE), has(Items.IRON_PICKAXE))
                .save(pWriter);


    }
}
