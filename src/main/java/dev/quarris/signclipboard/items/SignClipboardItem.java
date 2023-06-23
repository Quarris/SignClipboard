package dev.quarris.signclipboard.items;

import dev.quarris.signclipboard.ModRef;
import dev.quarris.signclipboard.registry.ItemSetup;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SignText;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SignClipboardItem extends Item {

    private final boolean isFilled;

    public SignClipboardItem(boolean filled, Properties properties) {
        super(properties);
        this.isFilled = filled;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!player.isShiftKeyDown()) {
            return super.use(level, player, hand);
        }

        System.out.println(hand);
        return super.use(level, player, hand);
    }

    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        BlockEntity be = ctx.getLevel().getBlockEntity(ctx.getClickedPos());
        if (!(be instanceof SignBlockEntity sign)) {
            return super.useOn(ctx);
        }

        Player player = ctx.getPlayer();
        ItemStack held = ctx.getItemInHand();

        boolean facingFront = sign.isFacingFrontText(player);

        if (this.isFilled) {
            if ((sign.getPlayerWhoMayEdit() == null || player.getUUID().equals(sign.getPlayerWhoMayEdit())) && pasteSignText(ctx.getItemInHand(), sign, !facingFront)) {
                return InteractionResult.sidedSuccess(ctx.getLevel().isClientSide());
            }

            return InteractionResult.FAIL;
        }

        ItemStack clippy = copySignText(sign, facingFront);
        if (!clippy.isEmpty()) {
            if (!player.level().isClientSide()) {
                if (!player.getAbilities().instabuild) {
                    held.shrink(1);
                }
                player.addItem(clippy);
            }
            return InteractionResult.sidedSuccess(ctx.getLevel().isClientSide());
        }

        return InteractionResult.FAIL;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltips, TooltipFlag flag) {
        SignText.DIRECT_CODEC.parse(NbtOps.INSTANCE, stack.getTagElement("Front")).resultOrPartial(ModRef.LOGGER::error).ifPresent((text) -> {
            for (Component line : text.getMessages(true)) {
                tooltips.add(Component.literal("[").append(line).append("]").withStyle(ChatFormatting.GRAY));
            }
        });
        tooltips.add(Component.literal("-----").withStyle(ChatFormatting.GRAY));
        SignText.DIRECT_CODEC.parse(NbtOps.INSTANCE, stack.getTagElement("Back")).resultOrPartial(ModRef.LOGGER::error).ifPresent((text) -> {
            for (Component line : text.getMessages(true)) {
                tooltips.add(Component.literal("[").append(line).append("]").withStyle(ChatFormatting.GRAY));
            }
        });
    }

    public static ItemStack copySignText(SignBlockEntity sign, boolean reverse) {
        ItemStack clippy = new ItemStack(ItemSetup.FILLED_SIGN_CLIPBOARD.get());
        String frontTextTag = reverse ? "Back" : "Front";
        String backTextTag = reverse ? "Front" : "Back";
        SignText.DIRECT_CODEC.encodeStart(NbtOps.INSTANCE, sign.getFrontText()).resultOrPartial(ModRef.LOGGER::error).ifPresent((tag) -> {
            clippy.addTagElement(frontTextTag, tag);
        });
        SignText.DIRECT_CODEC.encodeStart(NbtOps.INSTANCE, sign.getBackText()).resultOrPartial(ModRef.LOGGER::error).ifPresent((tag) -> {
            clippy.addTagElement(backTextTag, tag);
        });
        return clippy;
    }

    public static boolean pasteSignText(ItemStack clipboard, SignBlockEntity sign, boolean facingFront) {
        SignText.DIRECT_CODEC.parse(NbtOps.INSTANCE, clipboard.getTagElement("Front")).resultOrPartial(ModRef.LOGGER::error).ifPresent((text) -> {
            sign.setText(text, facingFront);
        });

        SignText.DIRECT_CODEC.parse(NbtOps.INSTANCE, clipboard.getTagElement("Back")).resultOrPartial(ModRef.LOGGER::error).ifPresent((text) -> {
            sign.setText(text, !facingFront);
        });

        return true;
    }
}
