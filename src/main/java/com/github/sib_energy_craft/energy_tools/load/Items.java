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

    public static final Item MINING_DRILL;
    public static final Item DIAMOND_DRILL;
    public static final Item NETHERITE_DRILL;

    public static final Item ENERGY_SAW;
    public static final Item ADVANCED_ENERGY_SAW;

    public static final Item ENERGY_HOE;
    public static final Item ENERGY_SHOVEL;

    public static final Item ENERGY_TREE_TAP;

    static {
        var energyToolSettings = new Item.Settings()
                .maxDamage(-1)
                .maxCount(1);
        var miningDrillItem = new MiningDrillItem(ToolMaterials.IRON, 2, -2.8f,
                energyToolSettings, 50, 30000);
        MINING_DRILL = register(ItemGroups.TOOLS, Identifiers.of("mining_drill"), miningDrillItem);

        var diamondDrill = new MiningDrillItem(ToolMaterials.DIAMOND, 3, -2.8f,
                energyToolSettings, 60, 30000);
        DIAMOND_DRILL = register(ItemGroups.TOOLS, Identifiers.of("diamond_drill"), diamondDrill);

        var netherDrill = new MiningDrillItem(ToolMaterials.NETHERITE, 4, -2.8f,
                energyToolSettings, 64, 30000);
        NETHERITE_DRILL = register(ItemGroups.TOOLS, Identifiers.of("netherite_drill"), netherDrill);

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

    }
}
