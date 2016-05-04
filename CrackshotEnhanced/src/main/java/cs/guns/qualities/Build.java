/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.guns.qualities;

import org.bukkit.ChatColor;

/**
 *
 * @author jbannon
 */
public enum Build
{
    PRESHOT  (0.0,  ChatColor.DARK_RED,     "ERROR"),
    STOCK    (1.0,  ChatColor.YELLOW,       "Stock"),
    TEMPERED (0.96, ChatColor.GREEN,        "Tempered"),
    MODIFIED (0.91, ChatColor.DARK_GREEN,   "Modified"),
    ENHANCED (0.85, ChatColor.AQUA,         "Enhanced"),
    SUPERIOR (0.78, ChatColor.DARK_AQUA,    "Superior"),
    RARE     (0.71, ChatColor.LIGHT_PURPLE, "Rare"),
    UNIQUE   (0.65, ChatColor.GOLD,         "Unique");
    
    private static final String TITLE = "Build: ";
    
    private final double scalar;
    private final String value;
    
    private Build(final double scalar,
                  final ChatColor color,
                  final String value)
    {
        this.scalar = scalar;
        this.value = color + value;
    }
    
    public static String getTitle()     { return TITLE;     }
    
    public String getDisplayName()      { return value;     }
    public double getScalar()           { return scalar;    }
    
    @Override
    public String toString() { return super.name(); }
}
