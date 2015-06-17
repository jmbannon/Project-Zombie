/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.types;

import static net.projectzombie.crackshot_enhanced.custom_weapons.types.WeaponType.STAT_COLOR;
import static net.projectzombie.crackshot_enhanced.custom_weapons.types.WeaponType.VALUE_COLOR;

/**
 *
 * @author jbannon
 */
public class ScopeTypes
{
    private ScopeTypes() { /* Do nothing */ }
    
    /**
     * Returns the string to display based on the weapon lore's scope type.
     * 
     * @param scopeInt Integer set in Crackshot lore for scope type.
     * @return String to display for fire mode.
     */
    public static String getScopeDisplay(final int scopeInt)
    {
        switch(scopeInt)
        {
        case 0: return "n/a";
        case 1: return "ACOG";
        case 2: return "Tactical";
        case 3: return "Long-Range";
        default: return null;
        }
    }
}
