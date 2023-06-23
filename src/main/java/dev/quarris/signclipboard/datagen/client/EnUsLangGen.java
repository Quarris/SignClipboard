package dev.quarris.signclipboard.datagen.client;

import dev.quarris.signclipboard.registry.ItemSetup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class EnUsLangGen extends LanguageProvider {

    public EnUsLangGen(PackOutput output, String modid) {
        super(output, modid, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.add(ItemSetup.SIGN_CLIPBOARD.get(), "Sign Clipboard");
        this.add(ItemSetup.FILLED_SIGN_CLIPBOARD.get(), "Filled Sign Clipboard");

        this.add("tab.signprint", "Sign Print");
    }
}
