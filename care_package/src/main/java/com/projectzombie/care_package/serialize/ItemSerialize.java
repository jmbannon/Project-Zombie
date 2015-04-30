/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectzombie.care_package.serialize;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author jbannon
 */
public class ItemSerialize {
    
    /**
     * Serializes item in the form of "material,amount,#"
     * If it is air then "0\n"
     * @param item
     */
    private ItemSerialize() { /* Do nothing */ }
    
    /**
     * Serializes item in the form of itemType,amount,#
     * @param item
     * @return 
     */
    public static String serialize(final ItemStack item)
    {
        final StringBuilder temp = new StringBuilder();
        if (item != null && item.getType() != Material.AIR) {
            temp.append(item.getType().toString());
            temp.append(",");
            temp.append(item.getAmount());
            temp.append(",");
            temp.append("#");
            return temp.toString();
        } else
            return "0,0,#";
    }
    
    /**
     * Deserializes the string and sets the block in the specified world.
     * @param serializedString
     * @return 
     */
    public static ItemStack deserialize(final String serializedString)
    {
        final String[] parts = serializedString.split(",");
        final Material type;
        final int amount;
        
        if (parts[0].equalsIgnoreCase("0"))
            return new ItemStack(Material.AIR, 0);
        
        type = Material.valueOf(parts[0]);
        amount = Integer.valueOf(parts[1]);

        return new ItemStack(type, amount);
    }
}
