/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.custom_items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author jbannon
 */
public class Engraver extends CustomItem
{
    public Engraver() {
        super(Material.NAME_TAG,
              null,
              null,
              0,
              0,
              0,
              0.5);
    }
    
    public static boolean engraveItem(final Player player,
                                      final String name)
    {
        PlayerInventory inv = player.getInventory();
        ItemStack inHand = player.getItemInHand();
        int engraverInvSlot = inv.first(Material.NAME_TAG);
        
        if (engraverInvSlot >= 0
                && inHand != null
                && inHand.hasItemMeta())
        {
            ItemStack engraver = inv.getItem(engraverInvSlot);
            ItemMeta meta = inHand.getItemMeta();
            if (meta.getDisplayName().startsWith(String.valueOf(ChatColor.YELLOW)))
            {
                String[] split = meta.getDisplayName().split(String.valueOf(ChatColor.YELLOW));
                StringBuilder stb = new StringBuilder();
                stb.append(ChatColor.YELLOW);
                stb.append(ChatColor.translateAlternateColorCodes('&', name));
                stb.append(ChatColor.YELLOW);
                for (int i = 2; i < split.length; i++)
                    stb.append(split[i]);
                
                meta.setDisplayName(stb.toString());
            }
            else
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
            
            inHand.setItemMeta(meta);
            if (engraver.getAmount() == 1)
                inv.remove(engraver);
            else
                engraver.setAmount(engraver.getAmount()-1);
            
            player.updateInventory();
            player.sendMessage("Item has been engraved.");
            return true;
        } 
        else if (engraverInvSlot == -1)
            player.sendMessage("You must have an engraver to engrave an item!");
        else if (inHand == null)
            player.sendMessage("You must have an item in your hand to engrave!");
        else if (!inHand.hasItemMeta())
            player.sendMessage("You cannot engrave this item.");
        
        return false;
    }
}
