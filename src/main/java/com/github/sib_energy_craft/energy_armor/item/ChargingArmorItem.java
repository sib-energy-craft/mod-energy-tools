package com.github.sib_energy_craft.energy_armor.item;

import com.github.sib_energy_craft.energy_api.items.ChargeableItem;
import com.github.sib_energy_craft.energy_armor.load.Items;
import lombok.Getter;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.minecraft.entity.player.PlayerInventory.MAIN_SIZE;

/**
 * @author sibmaks
 * @since 0.0.8
 */
public class ChargingArmorItem extends ArmorItem implements ChargeableItem {
    @Getter
    private final int maxCharge;
    private final int maxChargePacket;

    public ChargingArmorItem(@NotNull ArmorMaterial material,
                             @NotNull Type type,
                             @NotNull Settings settings,
                             int maxCharge,
                             int maxChargePacket) {
        super(material, type, settings);
        this.maxCharge = maxCharge;
        this.maxChargePacket = maxChargePacket;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(!(entity instanceof PlayerEntity player)) {
            return;
        }
        boolean wearable = false;
        for (var armorItem : player.getArmorItems()) {
            if (armorItem == stack) {
                wearable = true;
                break;
            }
        }
        if(!wearable) {
            return;
        }
        int armorCharge = getCharge(stack);
        if(armorCharge == 0) {
            if(stack.isOf(Items.WORKER_CHSETPLATE)) {
                var playerInventory = player.getInventory();
                var unchargedStack = new ItemStack(Items.UNCHARGED_WORKER_CHESTPLATE, 1);
                unchargedStack.setNbt(stack.getNbt());
                playerInventory.setStack(MAIN_SIZE + slot, unchargedStack);
                return;
            }
        } else {
            if(stack.isOf(Items.UNCHARGED_WORKER_CHESTPLATE)) {
                var chargedChestPlate = new ItemStack(Items.WORKER_CHSETPLATE, 1);
                chargedChestPlate.setNbt(stack.getNbt());
                var playerInventory = player.getInventory();
                playerInventory.setStack(MAIN_SIZE + slot, chargedChestPlate);
                return;
            }
        }
        if(armorCharge <= 0) {
            return;
        }

        for (var handItemStack : entity.getHandItems()) {
            if(handItemStack.isEmpty()) {
                continue;
            }
            var handItem = handItemStack.getItem();
            if(!(handItem instanceof ChargeableItem chargeableItem)) {
                continue;
            }
            if(!chargeableItem.hasFreeSpace(handItemStack)) {
                continue;
            }
            int charge = Math.min(
                    maxChargePacket,
                    Math.min(
                            chargeableItem.getFreeSpace(handItemStack),
                            armorCharge
                    )
            );
            if(discharge(stack, charge)) {
                chargeableItem.charge(handItemStack, charge);
            }
            armorCharge = getCharge(stack);
            if(armorCharge == 0) {
                break;
            }
        }
    }

    @Override
    public void appendTooltip(ItemStack stack,
                              @Nullable World world,
                              List<Text> tooltip,
                              TooltipContext context) {
        ChargeableItem.super.appendTooltip(stack, tooltip);
    }

}
