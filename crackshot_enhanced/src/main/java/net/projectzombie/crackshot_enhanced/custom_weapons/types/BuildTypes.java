/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.types;

import org.bukkit.ChatColor;

/**
 *
 * @author jbannon
 */
public enum BuildTypes {
    
    Stock    (0, ChatColor.YELLOW, "Stock"),
    Tempered (1, ChatColor.GREEN,  "Tempered"),
    Modified (2, ChatColor.GOLD,   "Modified"),
    Enhanced (3, ChatColor.RED,    "Enhanced");
    
    private final int value;
    private final String title;
    
    private BuildTypes(final int value,
                       final ChatColor color,
                       final String title)
    {
        this.value = value;
        this.title = color + title;
    }
    
    private String getTitle()
    {
        return title;
    }
    
    public static String getBuildType(final int value)
    {
        for (BuildTypes type : BuildTypes.values())
            if (type.value == value)
                return type.getTitle();
        return null;
    }
    
}
