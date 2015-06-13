/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons;

import org.bukkit.ChatColor;

/**
 *
 * @author jbannon
 */
public enum WeaponUpgradeTiers {
    
    Stock    (0, ChatColor.YELLOW, "Stock"),
    Tempered (1, ChatColor.GREEN,  "Tempered"),
    Modified (2, ChatColor.GOLD,   "Modified"),
    Enhanced (3, ChatColor.RED,    "Enhanced");
    
    private int value;
    private String title;
    
    private WeaponUpgradeTiers(final int value,
                               final ChatColor color,
                               final String title)
    {
        this.value = value;
        this.title = color + title;
    }
    
}
