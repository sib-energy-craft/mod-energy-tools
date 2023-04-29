package com.github.sib_energy_craft.energy_tools.item;

import com.github.sib_energy_craft.energy_api.items.ChargeableItem;
import com.github.sib_energy_craft.tools.item.tree_tap.TreeTapItem;
import lombok.Getter;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public class EnergyTreeTapItem extends TreeTapItem implements ChargeableItem {
    @Getter
    private final int maxCharge;
    private final int energyPerMine;

    public EnergyTreeTapItem(@NotNull ToolMaterial material,
                             float attackDamage,
                             float attackSpeed,
                             @NotNull Settings settings,
                             int energyPerMine,
                             int maxCharge) {
        super(material, attackDamage, attackSpeed, settings);
        this.maxCharge = maxCharge;
        this.energyPerMine = energyPerMine;
    }

    @Override
    public float getMiningSpeedMultiplier(@NotNull ItemStack stack,
                                          @NotNull BlockState state) {
        return hasAtLeast(stack, energyPerMine) ? super.getMiningSpeedMultiplier(stack, state) : 0.1f;
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
    public boolean onUse(@NotNull PlayerEntity player,
                         @NotNull Hand hand,
                         @NotNull ItemStack treeTap) {
        if(hasAtLeast(treeTap, energyPerMine)) {
            ChargeableItem.super.discharge(treeTap, energyPerMine);
            return true;
        }
        return false;
    }

    protected boolean hasAtLeast(@NotNull ItemStack itemStack, int required) {
        int charge = getCharge(itemStack);
        return charge >= required;
    }
}
