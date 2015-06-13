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
public enum WeaponConditionTiers
{
    NEW     (5, ChatColor.RED,        "Factory New"),
    MINT    (4, ChatColor.AQUA,       "Mint"),
    FINE    (3, ChatColor.DARK_BLUE,  "Scarred"),
    WORN    (2, ChatColor.GREEN,      "Worn"),
    RUSTY   (1, ChatColor.GOLD,       "Rusty"),
    BROKEN  (0, ChatColor.GRAY,       "Broken");
    
    /** Total number of tiers */
    public static final int TIERS = 5;
    
    private final int value;
    private final String condition;

    private WeaponConditionTiers(final int value,
                        final ChatColor color,
                        final String condition)
    {
        this.value = value;
        this.condition = color + condition;
    }
    
    public static String getCondition(final int tier)
    {
        switch(tier)
        {
        case 5: return NEW.getCondition();
        case 4: return MINT.getCondition();
        case 3: return FINE.getCondition();
        case 2: return WORN.getCondition();
        case 1: return RUSTY.getCondition();
        case 0: return BROKEN.getCondition();
        default: return null;
        }
    }
    
    public String getCondition()
    {
        return condition;
    }

}
