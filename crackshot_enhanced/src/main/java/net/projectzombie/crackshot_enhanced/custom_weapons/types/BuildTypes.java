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
public enum BuildTypes implements Type
{
    
    Stock    (0, 1.0, ChatColor.YELLOW, "Stock"),
    Tempered (1, 0.9, ChatColor.GREEN,  "Tempered"),
    Modified (2, 0.8, ChatColor.GOLD,   "Modified"),
    Enhanced (3, 0.7, ChatColor.RED,    "Enhanced");
    
    private static final String title = "Build: ";
    
    private final int enumValue;
    private double scalar;
    private final String value;
    
    private BuildTypes(final int enumValue,
                       final double scalar,
                       final ChatColor color,
                       final String value)
    {
        this.enumValue = enumValue;
        this.scalar = scalar;
        this.value = color + value;
    }
    
    public static String getTitle()
    {
        return title;
    }
    
    @Override
    public String getValue()
    {
        return value;
    }
    
    public static String getValue(final int value)
    {
        for (BuildTypes type : BuildTypes.values())
            if (type.enumValue == value)
                return type.value;
        return null;
    }
    
    public static BuildTypes getBuildType(final int enumValue)
    {
        for (BuildTypes type : BuildTypes.values())
            if (type.enumValue == enumValue)
                return type;
        
        return Stock;
    }
    
    public double getScalar()
    {
        return scalar;
    }
}
