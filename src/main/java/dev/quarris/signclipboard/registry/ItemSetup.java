package dev.quarris.signclipboard.registry;

import dev.quarris.signclipboard.ModRef;
import dev.quarris.signclipboard.items.SignClipboardItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemSetup {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModRef.ID);

    public static final RegistryObject<Item> SIGN_CLIPBOARD = ITEMS.register("sign_clipboard", () -> new SignClipboardItem(false, new Item.Properties()));
    public static final RegistryObject<Item> FILLED_SIGN_CLIPBOARD = ITEMS.register("filled_sign_clipboard", () -> new SignClipboardItem(true, new Item.Properties()));

    public static void init(IEventBus bus) {
        ITEMS.register(bus);
    }
}
