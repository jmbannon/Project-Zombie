/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import net.projectzombie.crackshot_enhanced.custom_weapons.types.Type;
import net.projectzombie.crackshot_enhanced.custom_weapons.weps.GunSkeleton;
import org.bukkit.Material;

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

    private final String value;
    
    private FireMode(final String value) 
    {
        this.value = value;
    }
    
    
    @Override public String toString()      { return value;    }
    @Override public int price()            { return 40;       }
    @Override public String title()         { return TITLE;    }
    
    @Override public double getBulletSpreadBoost(int baseBulletSpread)
    {
        return 0;
    }

    @Override
    public int getDamageBoost(int baseDamage)
    {
        return 0;
    }

}
