package com.github.sib_energy_craft.energy_tools.item;

import com.github.sib_energy_craft.energy_api.items.ChargeableItem;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @since 0.0.1
 * @author sibmaks
 */
@Slf4j
public class EnergyHoeItem extends HoeItem implements ChargeableItem {
    @Getter
    private final int maxCharge;
    private final int energyPerMine;

    public EnergyHoeItem(@NotNull ToolMaterial material,
                         int attackDamage,
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
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        ChargeableItem.super.discharge(stack, energyPerMine * 2);
        return true;
    }

    @Override
    public boolean postMine(@NotNull ItemStack stack,
                            @NotNull World world,
                            @NotNull BlockState state,
                            @NotNull BlockPos pos,
                            @NotNull LivingEntity miner) {
        if (!world.isClient && state.getHardness(world, pos) != 0.0f) {
            ChargeableItem.super.discharge(stack, energyPerMine);
        }
        return true;
    }

    @Nullable
    @Override
    public ActionResult useOnBlock(@NotNull ItemUsageContext context) {
        var world = context.getWorld();
        var blockPos = context.getBlockPos();
        var blockState = world.getBlockState(blockPos);
        var pair = TILLING_ACTIONS.get(blockState.getBlock());
        if (pair == null) {
            return ActionResult.PASS;
        }
        var predicate = pair.getFirst();
        var consumer = pair.getSecond();
        var stack = context.getStack();
        if (predicate.test(context) && hasAtLeast(stack, energyPerMine)) {
            var playerEntity = context.getPlayer();
            world.playSound(playerEntity, blockPos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
            if (!world.isClient) {
                consumer.accept(context);
                if (playerEntity != null) {
                    ChargeableItem.super.discharge(stack, energyPerMine);
                }
            }
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }

    @Override
    public void onCraft(@NotNull ItemStack stack, @NotNull World world, @NotNull PlayerEntity player) {
        ChargeableItem.super.onCraft(stack);
    }

    @Override
    public void appendTooltip(@NotNull ItemStack stack,
                              @Nullable World world,
                              @NotNull List<Text> tooltip,
                              @NotNull TooltipContext context) {
        ChargeableItem.super.appendTooltip(stack, tooltip);
    }

    protected boolean hasAtLeast(@NotNull ItemStack itemStack, int required) {
        int charge = getCharge(itemStack);
        return charge >= required;
    }
}
