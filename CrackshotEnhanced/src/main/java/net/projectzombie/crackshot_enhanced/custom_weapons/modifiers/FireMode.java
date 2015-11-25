/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.Type;

/**
 *
 * @author jbannon
 */
public enum FireMode implements Type, GunModifier
{
    SEMI    ("Semi-Auto"),
    BURST   ("Burst"),
    AUTO    ("Automatic"),
    SING    ("Single Shot"),
    BOLT    ("Bolt-Action"),
    PUMP    ("Pump-Action"),
    OVER    ("Over/Under");
    
    private static final String TITLE = "Fire Mode: ";

    private final String displayName;
    
    private FireMode(final String displayName) 
    {
        this.displayName = displayName;
    }
    
    
    @Override public String toString()      { return displayName;    }
    @Override public int price()            { return 40;       }
    @Override public String title()         { return TITLE;    }
    

    @Override
    public String getDisplayName()
    {
        return displayName;
    }

}
