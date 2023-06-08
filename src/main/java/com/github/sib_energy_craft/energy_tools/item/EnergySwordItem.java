package com.github.sib_energy_craft.energy_tools.item;

import com.github.sib_energy_craft.energy_api.items.ChargeableItem;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @since 0.0.8
 * @author sibmaks
 */
@Slf4j
public class EnergySwordItem extends SwordItem implements ChargeableItem {
    @Getter
    private final int maxCharge;
    private final float attackSpeed;
    private final int energyPerHit;

    public EnergySwordItem(@NotNull ToolMaterial material,
                           int attackDamage,
                           float attackSpeed,
                           @NotNull Settings settings,
                           int energyPerHit,
                           int maxCharge) {
        super(material, attackDamage, attackSpeed, settings);
        this.maxCharge = maxCharge;
        this.attackSpeed = attackSpeed;
        this.energyPerHit = energyPerHit;
    }

    @Override
    public float getMiningSpeedMultiplier(@NotNull ItemStack stack,
                                          @NotNull BlockState state) {
        return hasAtLeast(stack, energyPerHit) ? super.getMiningSpeedMultiplier(stack, state) : 0.1f;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return ChargeableItem.super.discharge(stack, energyPerHit);
    }

    @Override
    public boolean postMine(@NotNull ItemStack stack,
                            @NotNull World world,
                            @NotNull BlockState state,
                            @NotNull BlockPos pos,
                            @NotNull LivingEntity miner) {
        if (!world.isClient && state.getHardness(world, pos) != 0.0f) {
            return ChargeableItem.super.discharge(stack, energyPerHit * 2);
        }
        return true;
    }

    @Override
    public void onCraft(@NotNull ItemStack stack, @NotNull World world, @NotNull PlayerEntity player) {
        ChargeableItem.super.onCraft(stack);

        setNbtModifiers(stack);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        var nbt = stack.getNbt();

        if(nbt == null) {
            setNbtModifiers(stack);
        } else {
            boolean charged = nbt.getBoolean("Charged");
            if(charged != hasEnergy(stack)) {
                setNbtModifiers(stack);
            }
        }
    }

    private void setNbtModifiers(@NotNull ItemStack stack) {
        double attackDamage = getAttackDamage(stack);
        var damageModifier = new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID,
                "Weapon modifier", attackDamage, EntityAttributeModifier.Operation.ADDITION);
        var speedModifier = new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID,
                "Weapon modifier", attackSpeed, EntityAttributeModifier.Operation.ADDITION);

        var attributeModifiers = new NbtList();

        // net.minecraft.entity.attribute.EntityAttributes
        var damageNbt = damageModifier.toNbt();
        damageNbt.putString("AttributeName", "generic.attack_damage");
        damageNbt.putString("Slot", "mainhand");
        attributeModifiers.add(damageNbt);

        var speedNbt = speedModifier.toNbt();
        speedNbt.putString("AttributeName", "generic.attack_speed");
        speedNbt.putString("Slot", "mainhand");
        attributeModifiers.add(speedNbt);

        var nbt = stack.getOrCreateNbt();
        nbt.put("AttributeModifiers", attributeModifiers);
        nbt.putBoolean("Charged", attackDamage != 0);
    }

    private double getAttackDamage(ItemStack stack) {
        return hasAtLeast(stack, energyPerHit) ? super.getAttackDamage() : 0;
    }

    @Override
    public void appendTooltip(@NotNull ItemStack stack,
                              @Nullable World world,
                              @NotNull List<Text> tooltip,
                              @NotNull TooltipContext context) {
        ChargeableItem.super.appendTooltip(stack, tooltip);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    protected boolean hasAtLeast(@NotNull ItemStack itemStack, int required) {
        int charge = getCharge(itemStack);
        return charge >= required;
    }
}
