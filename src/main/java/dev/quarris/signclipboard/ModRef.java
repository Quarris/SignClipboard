package dev.quarris.signclipboard;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModRef {

    public static final String ID = "signclipboard";

    public static final Logger LOGGER = LoggerFactory.getLogger("Sign Clipboard");

    public static ResourceLocation res(String name) {
        return new ResourceLocation(ID, name);
    }
}
