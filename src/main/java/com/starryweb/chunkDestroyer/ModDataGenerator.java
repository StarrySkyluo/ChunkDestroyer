package com.starryweb.chunkDestroyer;

import com.starryweb.chunkDestroyer.datagen.*;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = ChunkDestroyer.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        //includeServer可以看出是服务端的东西（战利品列表之类）
        generator.addProvider(event.includeServer(),new ModRecipesProvider(packOutput));
        generator.addProvider(event.includeServer(),new LootTableProvider(packOutput, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(ModBlockLootTablesProvider::new, LootContextParamSets.BLOCK)
        )));
        //客户端的(模型之类，比如模型和材质包有关)
        BlockTagsProvider blockTagsProvider = generator.addProvider(event.includeServer(),
                new ModBlockTagsProvider(packOutput,lookupProvider,existingFileHelper));
        generator.addProvider(event.includeServer(), new ModItemTagsProvider(packOutput,lookupProvider,blockTagsProvider.contentsGetter(),existingFileHelper));

        generator.addProvider(event.includeClient(),new ModBlockStateProvider(packOutput,existingFileHelper));
        generator.addProvider(event.includeClient(),new ModItemModelsProvider(packOutput,existingFileHelper));
        generator.addProvider(event.includeClient(),new ModEnUsLangProvider(packOutput));
        generator.addProvider(event.includeClient(),new ModZhCnLangProvider(packOutput));

    }


}
