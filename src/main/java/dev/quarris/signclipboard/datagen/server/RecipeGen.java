package dev.quarris.signclipboard.datagen.server;

import dev.quarris.signclipboard.registry.ItemSetup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class RecipeGen extends RecipeProvider {

    public RecipeGen(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> builder) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, ItemSetup.SIGN_CLIPBOARD.get())
            .requires(ItemTags.SIGNS)
            .requires(Items.PAPER)
            .unlockedBy("has_sign", has(ItemTags.SIGNS))
            .save(builder);
    }
}
