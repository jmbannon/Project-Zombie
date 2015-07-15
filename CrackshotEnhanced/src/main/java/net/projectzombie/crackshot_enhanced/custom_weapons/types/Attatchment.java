/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.types;

/**
 *
 * @author jbannon
 */
public enum Attatchment implements Type
{
    NA   (0, "None"),  
    SUP (1, "Suppressor"),
    SAW  (2, "Sawed-Off"),
    LUB  (3, "Lubed-Bolt"),
    EXT  (4, "Extended Mag"),
    REL  (5, "Fast-Mag");
    
    private final int enumValue;
    private final String value;
    private static final String title = "Attatchment: ";
    
    Attatchment(final int enumValue,
               final String value)
    {
        this.enumValue = enumValue;
        this.value = value;
    }
    
    static public String getTitle()     { return title;     }
    @Override public int getEnumValue() { return enumValue; }
    @Override public String toString()  { return value;     }
}
