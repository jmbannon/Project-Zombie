/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.custom_interactions.custom_items;

import org.bukkit.inventory.ItemStack;

/**
 *
 * @author jbannon
 */
public enum CustomItemType {
    
    WireCutter(new WireCutter()),
    Chainsaw(new Chainsaw()),
    SledgeHammer(new SledgeHammer());
    
    private final CustomItem item;
    
    private CustomItemType(final CustomItem item)
    {
        this.item = item;
        
    }
    
    public CustomItem getCustomItem()
    {
        return this.item;
    }
    
    public static CustomItem getCustomItem(final ItemStack item)
    {
        if (item == null)
            return null;
        
        for (CustomItemType customItem : CustomItemType.values())
            if (item.getType().equals(customItem.getCustomItem().getMaterial()))
                return customItem.item;
        
        return null;
    }
}
