/*
 * CarePackage
 *
 * Version:     0.5
 * MC Build:    1.8.3
 * Date:        05-03-2015
 *
 * Authur:      Jesse Bannon
 * Server:      Project Zombie
 * Website:     www.projectzombie.net
 * 
 * Initiates random care package drops by combining an alternate state of the
 * map with a base state on the actual player map. Stores the base state blocks
 * within a text buffer and pastes the alt state to the location of the base
 * state. Finds single chest within the pasted alt state and sets a randomly
 * define set of items made by the administrator.  Restores the state on a
 * timer.
 *
 */

package com.projectzombie.care_package.serialize;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author jbannon
 */
public class ItemSerialize {
    
    private ItemSerialize() { /* Do nothing */ }
    
    /**
     * Serializes item in the form of 
     * hasDisplayName,hasLore,itemType,amount,durability,displayname,loreSize,loreLine1,...,##
     * @param item
     * @return 
     */
    public static String serialize(final ItemStack item)
    {
        final StringBuilder temp = new StringBuilder();
        String hasDisplayName = "0&&";
        String hasLore = "0&&";
        
        if (item != null && item.getType() != Material.AIR) {
            temp.append(item.getType().toString());
            temp.append("&&");
            temp.append(item.getAmount());
            temp.append("&&");
            temp.append(item.getDurability());
            temp.append("&&");
            if (item.getItemMeta().hasDisplayName()) {
                hasDisplayName = "1&&";
                temp.append(item.getItemMeta().getDisplayName());
                temp.append("&&");
            }
            if (item.getItemMeta().hasLore()) {
                hasLore = "1&&";
                temp.append(item.getItemMeta().getLore().size());
                temp.append("&&");
                for (int i = 0; i < item.getItemMeta().getLore().size(); i++) {
                    temp.append(item.getItemMeta().getLore().get(i));
                    temp.append("&&");
                }
            }
            temp.append("##");
            return hasDisplayName + hasLore + temp.toString();
        } else
            return "!&&##";
    }
    
    /**
     * Deserializes the string and sets the block in the specified world.
     * @param serializedString
     * @return 
     */
    public static ItemStack deserialize(final String serializedString)
    {
        final String[] parts = serializedString.split("&&");
        final boolean hasDisplayName, hasLore;
        final ItemMeta meta;
        
        if (parts[0].equalsIgnoreCase("!"))
            return new ItemStack(Material.AIR, 0);
        
        hasDisplayName = (parts[0].equals("1"));
        hasLore = (parts[1].equals("1"));
        
        ItemStack item = new ItemStack(
                Material.valueOf(parts[2]), 
                Integer.valueOf(parts[3]), 
                Short.valueOf(parts[4]));
        
        meta = item.getItemMeta();
        
        if (hasDisplayName)
            meta.setDisplayName(parts[5]);
        
        if (hasLore) {
            final int loreSize = Integer.valueOf(parts[6]);
            List<String> loreList = new ArrayList<>();
            for (int i = 0; i < loreSize; i++)
                loreList.add(parts[7+i]);
        
            meta.setLore(loreList);
        }
        
        item.setItemMeta(meta);
        return item;
    }
}
