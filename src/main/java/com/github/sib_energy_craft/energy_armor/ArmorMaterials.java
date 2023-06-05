package com.github.sib_energy_craft.energy_armor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.Util;

import java.util.EnumMap;

/**
 * @author sibmaks
 * @since 0.0.8
 */
@Getter
@AllArgsConstructor
public enum ArmorMaterials implements StringIdentifiable, ArmorMaterial {

    WORKER_SUIT("worker_suit", 15, Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
        map.put(ArmorItem.Type.BOOTS, 2);
        map.put(ArmorItem.Type.LEGGINGS, 5);
        map.put(ArmorItem.Type.CHESTPLATE, 6);
        map.put(ArmorItem.Type.HELMET, 2);
    }), 10, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F, 0.0F,
            Ingredient.ofItems(Items.IRON_INGOT)),

    UNCHARGED_WORKER_SUIT("worker_suit", 15, Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
        map.put(ArmorItem.Type.BOOTS, 0);
        map.put(ArmorItem.Type.LEGGINGS, 0);
        map.put(ArmorItem.Type.CHESTPLATE, 0);
        map.put(ArmorItem.Type.HELMET, 0);
    }), 0, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F, 0.0F,
            Ingredient.ofItems(Items.IRON_INGOT));

    private final String name;
    private final int durabilityMultiplier;
    private final EnumMap<ArmorItem.Type, Integer> protectionAmounts;
    private final int enchantability;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Ingredient repairIngredient;


    private static final EnumMap<ArmorItem.Type, Integer> BASE_DURABILITY = Util.make(new EnumMap<>(ArmorItem.Type.class), (map) -> {
        map.put(ArmorItem.Type.BOOTS, 13);
        map.put(ArmorItem.Type.LEGGINGS, 15);
        map.put(ArmorItem.Type.CHESTPLATE, 16);
        map.put(ArmorItem.Type.HELMET, 11);
    });

    public int getDurability(ArmorItem.Type type) {
        return BASE_DURABILITY.get(type) * this.durabilityMultiplier;
    }

    @Override
    public int getProtection(ArmorItem.Type type) {
        return this.protectionAmounts.get(type);
    }

    @Override
    public String asString() {
        return name;
    }
}
