/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.BulletSpreadModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.DamageModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.Type;

/**
 *
 * @author jbannon
 */
public enum Attatchment implements Type, GunModifier, BulletSpreadModifier, DamageModifier
{
    NULL_ATTATCHMENT(0, null),
    SUPPRESOR  (10, "Suppressor"),
    INCENDIARY  (10, "Incendiary Rounds"),
    CRIT_CHANCE(20, "Critical Chance Increase"),
    CRIT_STRIKE  (20, "Critical Strike Multiplier"),
    BLEEDOUT_INCREASE(20, "Bleedout Time Increase"),
    BLEEDOUT_DAMAGE_INCREASE(20, "Bleedout Damage Increase");
    
    private final int price;
    private final String displayName;
    private static final String title = "Attatchment: ";
    
    Attatchment(final int price,
                       final String displayname)
    {
        this.price = price;
        this.displayName = displayname;
    }
    
    
    static public String getTitle()     { return title;     }
    @Override public String toString()  { return displayName;     }
    @Override public int price()        { return price;     }
    @Override public String title()     { return title;     }

    @Override
    public String getDisplayName()
    {
        return displayName;
    }


    @Override
    public double getDamageBoost(double baseDamage)
    {
        return 0;
    }

    @Override
    public double getDamageModifyPercentage(double baseDamage)
    {
        return 1.0;
    }

    @Override
    public double getBulletSpreadModifyPercentage()
    {
        return 1.0;
    }
    
}
