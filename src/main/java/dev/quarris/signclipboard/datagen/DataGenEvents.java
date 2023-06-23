package dev.quarris.signclipboard.datagen;

import dev.quarris.signclipboard.ModRef;
import dev.quarris.signclipboard.datagen.client.EnUsLangGen;
import dev.quarris.signclipboard.datagen.client.ItemModelGen;
import dev.quarris.signclipboard.datagen.server.RecipeGen;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ModRef.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenEvents {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        PackOutput output = gen.getPackOutput();

        // Client
        gen.addProvider(event.includeClient(), new EnUsLangGen(output, ModRef.ID));
        gen.addProvider(event.includeClient(), new ItemModelGen(output, ModRef.ID, fileHelper));

        // Server
        gen.addProvider(event.includeServer(), new RecipeGen(output));
    }
}
