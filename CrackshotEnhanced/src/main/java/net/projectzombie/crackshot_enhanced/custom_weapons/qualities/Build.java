/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.qualities;

import org.bukkit.ChatColor;

/**
 *
 * @author jbannon
 */
public enum Build
{
    
    STOCK    (0, 1.0,  ChatColor.YELLOW,       "Stock"),
    TEMPERED (1, 0.96, ChatColor.GREEN,        "Tempered"),
    MODIFIED (2, 0.91, ChatColor.DARK_GREEN,   "Modified"),
    ENHANCED (3, 0.85, ChatColor.AQUA,         "Enhanced"),
    SUPERIOR (4, 0.78, ChatColor.DARK_AQUA,    "Superior"),
    RARE     (5, 0.71, ChatColor.LIGHT_PURPLE, "Rare"),
    UNIQUE   (6, 0.65, ChatColor.GOLD,         "Unique");
    
    private static final String TITLE = "Build: ";
    
    private final int enumValue;
    private final double scalar;
    private final String value;
    
    private Build(final int enumValue,
                  final double scalar,
                  final ChatColor color,
                  final String value)
    {
        this.enumValue = enumValue;
        this.scalar = scalar;
        this.value = color + value;
    }
    
    public static String getTitle()     { return TITLE;     }
    public double getScalar()           { return scalar;    }
    
    @Override public String toString()  { return value;     }
    public int getEnumValue() { return enumValue; }
    
    public static String getValue(final int value)
    {
        for (Build type : Build.values())
            if (type.enumValue == value)
                return type.value;
        return null;
    }
    
    public static Build getBuildType(final int enumValue)
    {
        for (Build type : Build.values())
            if (type.enumValue == enumValue)
                return type;
        
        return STOCK;
    }

}
