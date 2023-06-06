package com.github.sib_energy_craft.energy_tools.item;

import com.github.sib_energy_craft.energy_api.items.ChargeableItem;
import com.github.sib_energy_craft.tools.item.wrench.WrenchItem;
import lombok.Getter;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @since 0.0.8
 * @author sibmaks
 */
public class EnergyWrenchItem extends WrenchItem implements ChargeableItem {
    @Getter
    private final int maxCharge;
    private final int energyPerUse;

    public EnergyWrenchItem(@NotNull Settings settings,
                            int energyPerUse,
                            int maxCharge) {
        super(settings);
        this.maxCharge = maxCharge;
        this.energyPerUse = energyPerUse;
    }

    @Override
    public void onCraft(@NotNull ItemStack stack,
                        @NotNull World world,
                        @NotNull PlayerEntity player) {
        ChargeableItem.super.onCraft(stack);
    }

    @Override
    public void appendTooltip(@NotNull ItemStack stack,
                              @Nullable World world,
                              @NotNull List<Text> tooltip,
                              @NotNull TooltipContext context) {
        ChargeableItem.super.appendTooltip(stack, tooltip);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(!hasAtLeast(context.getStack(), energyPerUse)) {
            return ActionResult.PASS;
        }
        return super.useOnBlock(context);
    }

    @Override
    protected void onUse(ItemUsageContext context) {
        var stack = context.getStack();
        discharge(stack, energyPerUse);
    }

    protected boolean hasAtLeast(@NotNull ItemStack itemStack, int required) {
        int charge = getCharge(itemStack);
        return charge >= required;
    }
}
