/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import net.projectzombie.crackshot_enhanced.custom_weapons.types.Type;
import org.bukkit.Material;

/**
 *
 * @author jbannon
 */
public enum Scope implements Type, CraftableModifier
{
    
    IRON  (0, "Iron Sight", 0),
    ACOG  (1, "ACOG Scope", 4),
    TACT  (2, "Tactical Scope", 5),
    LONG  (3, "Sniper Scope", 10);
    
    private static final String TITLE = "Sight: ";
    
    private final int enumValue, zoomAmount;
    private final String value;
    
    private Scope(final int enumValue,
                  final String value,
                  final int crackshotZoomAmount)
    {
        this.enumValue = enumValue;
        this.value = value;
        this.zoomAmount = crackshotZoomAmount;
    }
    
    public static String getTitle()     { return TITLE;     }
    public int getZoomAmount()  { return zoomAmount; }
    @Override public String toString()  { return value;     }
    @Override public int getEnumValue() { return enumValue; }
    
    /**
     * Returns the string to display based on the weapon lore's scope type.
     * 
     * @param enumValue Integer set in Crackshot lore for scope type.
     * @return String to display for fire mode.
     */
    public static String getScopeDisplay(final int enumValue)
    {
        for (Scope type : Scope.values())
            if (type.enumValue == enumValue)
                return type.value;
        
        return IRON.value;
    }

    @Override
    public String title() {
        return TITLE;
    }

    @Override
    public int price() {
        return 20;
    }

    @Override
    public double getBulletSpreadBoost(int baseBulletSpread) {
        return 0;
    }

    @Override
    public int getDamageBoost(int baseDamage) {
        return 0;
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
    public Material getMaterial() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public byte getMaterialData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
