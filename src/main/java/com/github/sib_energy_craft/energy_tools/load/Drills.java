package com.github.sib_energy_craft.energy_tools.load;

import com.github.sib_energy_craft.energy_api.utils.Identifiers;
import com.github.sib_energy_craft.energy_tools.item.*;
import com.github.sib_energy_craft.sec_utils.load.DefaultModInitializer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ToolMaterials;

import static com.github.sib_energy_craft.sec_utils.utils.ItemUtils.register;

/**
 * @since 0.0.8
 * @author sibmaks
 */
public final class Drills implements DefaultModInitializer {

    public static final Item MINING_DRILL;
    public static final Item DIAMOND_DRILL;
    public static final Item NETHERITE_DRILL;

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

    }
}
