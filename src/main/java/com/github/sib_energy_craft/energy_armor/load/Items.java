package com.github.sib_energy_craft.energy_armor.load;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.energy_armor.ArmorMaterials;
import com.github.sib_energy_craft.energy_armor.item.ChargingArmorItem;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;

import static com.github.sib_energy_craft.sec_utils.utils.ItemUtils.register;

/**
 * @author sibmaks
 * @since 0.0.8
 */
public final class Items implements DefaultModInitializer {

    public static final Item WORKER_CHSETPLATE;
    public static final Item UNCHARGED_WORKER_CHESTPLATE;

    static {
        var commonSettings = new Item.Settings();

        var leatherBatBox = new ChargingArmorItem(
                ArmorMaterials.WORKER_SUIT,
                ArmorItem.Type.CHESTPLATE,
                commonSettings,
                30000,
                32
        );
        WORKER_CHSETPLATE = register(ItemGroups.COMBAT, Identifiers.of("worker_chestplate"), leatherBatBox);

        var unchargedLeatherBatBox = new ChargingArmorItem(
                ArmorMaterials.UNCHARGED_WORKER_SUIT,
                ArmorItem.Type.CHESTPLATE,
                commonSettings,
                30000,
                32
        );
        UNCHARGED_WORKER_CHESTPLATE = register(ItemGroups.COMBAT, Identifiers.of("uncharged_worker_chestplate"), unchargedLeatherBatBox);

    }
}
