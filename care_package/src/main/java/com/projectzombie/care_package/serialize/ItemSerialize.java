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
    
    private final String serializedItem;
    
    /**
     * Serializes item in the form of "material,amount,#"
     * If it is air then "0\n"
     * @param item
     */
    public ItemSerialize(final ItemStack item) {
        final StringBuilder temp = new StringBuilder();
        if (item != null && item.getType() != Material.AIR) {
            temp.append(item.getType().toString());
            temp.append(",");
            temp.append(item.getAmount());
            temp.append(",");
            temp.append("\n");
            serializedItem = temp.toString();
        } else
            serializedItem = "0,0,\n";
    }
    
    public String getSerialized() {
        return serializedItem;
    }
}
