/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.types;

import org.bukkit.ChatColor;

/**
 * @author Jesse Bannon (jmbannon@uw.edu)
 * 
 * Types of tiers for weapons. Contains display information for the weapon lore.
 */
public enum Condition implements Type
{
    NEW     (5, ChatColor.RED,        "Factory New"),
    MINT    (4, ChatColor.AQUA,       "Mint"),
    FINE    (3, ChatColor.DARK_BLUE,  "Scarred"),
    WORN    (2, ChatColor.GREEN,      "Worn"),
    RUSTY   (1, ChatColor.GOLD,       "Rusty"),
    BROKEN  (0, ChatColor.GRAY,       "Broken");
    
    public static final String title = "Condition: ";
    
    /** Total number of tiers */
    public static final int TIERS = 5;
    
    private final int value;
    private final String condition;

    /**
     * Creates a weapon tier of the value (higher -> better) and the display
     * information for the weapon lore.
     * 
     * @param value Value of the tier (higher is better).
     * @param color Color of the condition string in the lore.
     * @param condition Condition of the weapon to display in the lore.
     */
    private Condition(final int value,
                           final ChatColor color,
                           final String condition)
    {
        this.value = value;
        this.condition = color + condition;
    }
    
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
            if (tier == condition.value)
                return condition.getValue();
        
        return null;
    }

    public static String getTitle() {
        return title;
    }
    
    @Override
    public String getValue()
    {
        return condition;
    }

}
