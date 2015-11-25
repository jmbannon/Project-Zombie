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
    NA   (0,  "None"),  
    SUP  (10, "Suppressor"),
    INC  (10, "Incendiary");
    
    private final int price;
    private final String displayName;
    private static final String title = "Attatchment: ";
    
    Attatchment(final int price,
                final String displayname)
    {
        this.price = price;
        this.displayName = displayname;
    }
    
    @Override
    public double getBulletSpreadBoost(final int baseBulletSpread)
    {
        return 0;
    }
    
    @Override
    public int getDamageBoost(final int baseDamage)
    {
        return 0;
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
    
}
