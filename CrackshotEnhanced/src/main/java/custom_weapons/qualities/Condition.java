/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package custom_weapons.qualities;

import org.bukkit.ChatColor;

/**
 * @author Jesse Bannon (jmbannon@uw.edu)
 * 
 * Types of tiers for weapons. Contains display information for the weapon lore.
 */
public enum Condition
{
    NEW     (5, ChatColor.GREEN,      "Factory New"),
    MINT    (4, ChatColor.DARK_AQUA,  "Mint"),
    FINE    (3, ChatColor.YELLOW,     "Scarred"),
    WORN    (2, ChatColor.GOLD,       "Worn"),
    RUSTY   (1, ChatColor.RED,        "Rusty"),
    BROKEN  (0, ChatColor.DARK_RED,   "Broken");
    
    public static final String TITLE = "Condition: ";
    
    /** Total number of tiers */
    public static final int TIERS = 5;
    
    private final int enumValue;
    private final String condition;

    /**
     * Creates a weapon tier of the value (higher -> better) and the display
     * information for the weapon lore.
     * 
     * @param enumValue Value of the tier (higher is better).
     * @param color Color of the condition string in the lore.
     * @param condition Condition of the weapon to display in the lore.
     */
    private Condition(final int enumValue,
                      final ChatColor color,
                      final String condition)
    {
        this.enumValue = enumValue;
        this.condition = color + condition;
    }
    
    public static String getTitle()     { return TITLE;     }
    public int getEnumValue()           { return enumValue; }
    @Override public String toString()  { return condition; }
    
    /**
     * Returns the condition string based on the tier.  Returns null if tier 
     * does not match a condition.
     * 
     * @param tier Tier of the condition to get.
     * @return Returns the 
     */
    public static String getValue(final int tier)
    {
        for (Condition condition : Condition.values())
            if (tier == condition.enumValue)
                return condition.toString();
        
        return null;
    }
}
