package dev.quarris.signclipboard.datagen.client;

import dev.quarris.signclipboard.registry.ItemSetup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModelGen extends ItemModelProvider {

    public ItemModelGen(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        this.basicItem(ItemSetup.SIGN_CLIPBOARD.get());
        this.basicItem(ItemSetup.FILLED_SIGN_CLIPBOARD.get());
    }
}
