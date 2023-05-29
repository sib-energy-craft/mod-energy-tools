package com.github.sib_energy_craft.energy_tools.load;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.energy_tools.item.*;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ToolMaterials;

import static com.github.sib_energy_craft.sec_utils.utils.ItemUtils.register;

/**
 * @since 0.0.1
 * @author sibmaks
 */
public final class Items implements DefaultModInitializer {

    public static final Item ENERGY_SAW;
    public static final Item ADVANCED_ENERGY_SAW;

    public static final Item ENERGY_HOE;

    public static final Item ENERGY_SHOVEL;
    public static final Item ADVANCED_ENERGY_SHOVEL;

    public static final Item ENERGY_TREE_TAP;

    public static final Item ENERGY_SWORD;

    static {
        var energyToolSettings = new Item.Settings()
                .maxDamage(-1)
                .maxCount(1);

        var energyTreeTap = new EnergyTreeTapItem(ToolMaterials.WOOD, 1, -2.8f,
                energyToolSettings, 50, 30000);
        ENERGY_TREE_TAP = register(ItemGroups.TOOLS, Identifiers.of("energy_tree_tap"), energyTreeTap);

        var energySaw = new EnergySawItem(ToolMaterials.IRON, 6, -3.1f,
                energyToolSettings, 50, 30000);
        ENERGY_SAW = register(ItemGroups.TOOLS, Identifiers.of("energy_saw"), energySaw);

        var advancedEnergySaw = new EnergySawItem(ToolMaterials.DIAMOND, 10, -3.0f,
                energyToolSettings, 100, 130000);
        ADVANCED_ENERGY_SAW = register(ItemGroups.TOOLS, Identifiers.of("advanced_energy_saw"), advancedEnergySaw);

        var energyHoe = new EnergyHoeItem(ToolMaterials.IRON, -2, -1f,
                energyToolSettings, 50, 30000);
        ENERGY_HOE = register(ItemGroups.TOOLS, Identifiers.of("energy_hoe"), energyHoe);

        var energyShovel = new EnergyShovelItem(ToolMaterials.IRON, 1.5f, -3f,
                energyToolSettings, 50, 30000);
        ENERGY_SHOVEL = register(ItemGroups.TOOLS, Identifiers.of("energy_shovel"), energyShovel);

        var advancedEnergyShovel = new EnergyShovelItem(ToolMaterials.DIAMOND, 1.5f, -3f,
                energyToolSettings, 100, 130000);
        ADVANCED_ENERGY_SHOVEL = register(ItemGroups.TOOLS, Identifiers.of("advanced_energy_shovel"), advancedEnergyShovel);

        var energySword = new EnergySwordItem(ToolMaterials.IRON, 4, -2.4f,
                energyToolSettings, 15, 10000);
        ENERGY_SWORD = register(ItemGroups.COMBAT, Identifiers.of("energy_sword"), energySword);

    }
}
