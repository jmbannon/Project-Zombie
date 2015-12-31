/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.BulletSpreadModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.Type;
import org.bukkit.ChatColor;

/**
 *
 * @author jbannon
 */
public enum Scope implements Type, GunModifier, BulletSpreadModifier
{
    
    BROKEN   ("Broken",             0),
    IRON     ("Iron Sight",         0),
    RED_IRON ("Red Dot Iron Sight", 0),
    ACOG     ("ACOG Scope",         4),
    TACT     ("Tactical Scope",     5),
    LONG     ("Sniper Scope",       10);
    
    private static final String TITLE = "Sight: ";

    private final int zoomAmount;
    private final String displayName;
    
    private Scope(final String value,
                  final int crackshotZoomAmount)
    {
        this.displayName = value;
        this.zoomAmount = crackshotZoomAmount;
    }
    
    public static String getTitle()         { return TITLE;       }
    public int getZoomAmount()              { return zoomAmount;  }
    @Override public String toString()      { return displayName; }
    @Override public String title()         {  return TITLE;      }

    @Override
    public int price()
    {
        return 20;
    }

    
    public double getZoomedBulletSpreadPercentModifier()
    {
        return -0.20;
    }
    
    /**
     * Calculates a double based on a Crackshot gun's bullet spread to be subtracted
     * from the modified bullet spread when zoomed in. Applicable for scopes.
     * @param baseBullsetSpread 
     * @return 
     */
    public double getZoomBullsetSpreadBoost(final int baseBullsetSpread)
    {
        return 0;
    }

    @Override
    public String getDisplayName()
    {
        return displayName;
    }

    @Override
    public double getBulletSpreadMultiplier()
    {
        return 1.0;
    }

    /**
     * A sight can never be null - every gun needs one!
     * @return False.
     */
    @Override
    public boolean isNull()
    {
        return false;
    }
    
    @Override
    public ChatColor getColor() {
        return ChatColor.GREEN;
    }
}
