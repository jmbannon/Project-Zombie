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
        ItemStack inHand = player.getItemInHand();
        if (player.getInventory().contains(Material.NAME_TAG)
                && inHand != null
                && inHand.hasItemMeta())
        {
            ItemMeta meta = inHand.getItemMeta();
            if (meta.getDisplayName().startsWith(""+ChatColor.YELLOW))
                meta.setDisplayName(ChatColor.YELLOW + name.replace(""+ChatColor.RESET, ""));
            else
                meta.setDisplayName(name.replace(""+ChatColor.RESET, ""));
            
            inHand.setItemMeta(meta);
            player.getInventory().remove(Material.NAME_TAG);
            return true;
        } else
            return false;
    }
}
