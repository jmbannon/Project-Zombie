/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import net.projectzombie.crackshot_enhanced.custom_weapons.types.Type;

/**
 *
 * @author jbannon
 */
public enum Attatchment implements Type, GunModifier
{
    NA   (0, 0,  "None"),  
    SUP  (1, 10, "Suppressor"),
    SO   (2, 10, "Sawed-Off"),
    LUB  (3, 10, "Lubed-Bolt"),
    EXT  (4, 10, "Extended Mag"),
    REL  (5, 10, "Fast-Mag"),
    INC  (6, 10, "Incendiary"),
    GRA  (7, 10, "Grave-Spawn");
    
    private final int enumValue, price;
    private final String value;
    private static final String title = "Attatchment: ";
    
    Attatchment(final int enumValue,
                final int price,
                final String value)
    {
        this.enumValue = enumValue;
        this.price = price;
        this.value = value;
    }
    
    static public String getTitle()     { return title;     }
    @Override public int getEnumValue() { return enumValue; }
    @Override public String toString()  { return value;     }
    @Override public int price()        { return price;     }
    @Override public String title()     { return title;     }
}
